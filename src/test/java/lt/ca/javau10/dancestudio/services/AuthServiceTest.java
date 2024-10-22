package lt.ca.javau10.dancestudio.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import lt.ca.javau10.dancestudio.entities.UserEntity;
import lt.ca.javau10.dancestudio.payload.requests.LoginRequest;
import lt.ca.javau10.dancestudio.payload.requests.SignupRequest;
import lt.ca.javau10.dancestudio.payload.responses.JwtResponse;
import lt.ca.javau10.dancestudio.payload.responses.MessageResponse;
import lt.ca.javau10.dancestudio.repositories.RoleRepository;
import lt.ca.javau10.dancestudio.repositories.UserRepository;
import lt.ca.javau10.dancestudio.security.JwtUtils;

public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private AuthService authService;
    
    private UserEntity mockUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setAssignedTeacherId(2L); // Example field if you're using it
        return user;
    }
    
    private Authentication mockAuthentication() {
        return new UsernamePasswordAuthenticationToken("testuser", "password", new ArrayList<>());
    }

    @BeforeEach
    public void setup() {
        // Initialize mocks before each test
        authService = new AuthService(authenticationManager, userRepository, roleRepository, encoder, jwtUtils);
    }

    @Test
    public void testAuthenticateUser_Success() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("testuser");
        loginRequest.setPassword("testpassword");

        when(authenticationManager.authenticate(any())).thenReturn(mockAuthentication());
        when(jwtUtils.generateJwtToken(any())).thenReturn("mockJwtToken");

        JwtResponse response = authService.authenticateUser(loginRequest);

        assertNotNull(response);
        assertEquals("mockJwtToken", response.getAccessToken());
    }

    @Test
    public void testRegisterUser_Success() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("newuser");
        signupRequest.setPassword("newpassword");

        // Mock user existence check
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // Mock save
        when(userRepository.save(any())).thenReturn(mockUser());

        MessageResponse response = authService.registerUser(signupRequest);

        assertNotNull(response);
        assertEquals("User registered successfully!", response.getMessage());
    }

}
