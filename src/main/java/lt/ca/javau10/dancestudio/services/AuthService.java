package lt.ca.javau10.dancestudio.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lt.ca.javau10.dancestudio.entities.ERole;
import lt.ca.javau10.dancestudio.entities.Role;
import lt.ca.javau10.dancestudio.entities.UserDto;
import lt.ca.javau10.dancestudio.entities.UserEntity;
import lt.ca.javau10.dancestudio.payload.requests.LoginRequest;
import lt.ca.javau10.dancestudio.payload.requests.SignupRequest;
import lt.ca.javau10.dancestudio.payload.responses.JwtResponse;
import lt.ca.javau10.dancestudio.payload.responses.MessageResponse;
import lt.ca.javau10.dancestudio.repositories.RoleRepository;
import lt.ca.javau10.dancestudio.repositories.UserRepository;
import lt.ca.javau10.dancestudio.security.JwtUtils;

@Service
public class AuthService {

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	
	  AuthenticationManager authenticationManager;

	  UserRepository userRepository;

	  RoleRepository roleRepository;

	  PasswordEncoder encoder;

	  JwtUtils jwtUtils;
	  
	  public AuthService (AuthenticationManager authenticationManager,
			  UserRepository userRepository,
			  RoleRepository roleRepository,
			  PasswordEncoder encoder,
			  JwtUtils jwtUtils) {
		  this.authenticationManager = authenticationManager;
		  this.userRepository = userRepository;
		  this.roleRepository = roleRepository;
		  this.encoder = encoder;
		  this.jwtUtils = jwtUtils;
	  }
	
	public JwtResponse authenticateUser(LoginRequest loginRequest) {
       
		Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
		
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDto userDetails = (UserDto) authentication.getPrincipal();
        logger.info("Before: " + userDetails.toString());
        List<String> roles = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), userDetails.getFirstName(), userDetails.getLastName(), roles);
    }
	
	public MessageResponse registerUser(SignupRequest signUpRequest) {
		
        checkUserExists(signUpRequest);
        
        UserEntity user = createNewUser(signUpRequest);
        Set<Role> roles = getInitialRoles(signUpRequest);

        user.setRoles(roles);
       
        logger.info("Before: " + user.toString());
        user = userRepository.save(user);
        logger.info("After: " + user.toString());
        
        return new MessageResponse("User registered successfully!");
    }

	private UserEntity createNewUser(SignupRequest signUpRequest) {
		UserEntity user = new UserEntity(
				signUpRequest.getUsername(), 
				encoder.encode(signUpRequest.getPassword()),
				               signUpRequest.getEmail(),
				               signUpRequest.getFirstName(),
				               signUpRequest.getLastName());
		logger.info(user.toString());
		return user;
	}

	private Set<Role> getInitialRoles(SignupRequest signUpRequest) {
//		Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        boolean isTeacher = signUpRequest.isTeacher(); 
        logger.info("isTeacher value during registration: " + isTeacher);
        
     // Assign either ROLE_TEACHER or ROLE_STUDENT based on the sign-up request
        Optional<Role> userRole = isTeacher ? 
            roleRepository.findByName(ERole.ROLE_TEACHER) : 
            roleRepository.findByName(ERole.ROLE_STUDENT);
        
        // Ensure the role is found, otherwise throw an error
        userRole.ifPresentOrElse(
            roles::add, 
            () -> { throw new RuntimeException("Error: Role is not found."); }
        );
		return roles;
	}

	private void checkUserExists(SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Email is already in use!");
        }
	}
}
