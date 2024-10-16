package lt.ca.javau10.dancestudio.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import lt.ca.javau10.dancestudio.entities.ERole;
import lt.ca.javau10.dancestudio.entities.UserDto;
import lt.ca.javau10.dancestudio.entities.UserEntity;
import lt.ca.javau10.dancestudio.repositories.UserRepository;
import lt.ca.javau10.dancestudio.utils.EntityMapper;

@Service
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;
	private final EntityMapper entityMapper;
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	
	public UserService(UserRepository userRepository, EntityMapper entityMapper) {
		this.userRepository = userRepository;
		this.entityMapper = entityMapper;
	}
	
	//CRUD - Create, Read, Update, Delete
	
	public UserDto createUser(UserDto userDto) {
		UserEntity userEntityBeforeSave = entityMapper.toUserEntity(userDto);

		UserEntity userEntityAfterSave = userRepository.save(userEntityBeforeSave);		
		
		return entityMapper.toUserDto(userEntityAfterSave);		
	}
	
	public List<UserDto> getAllUsers(){
		List<UserEntity> users = userRepository.findAll();
		
		return users.stream()
				.map(entityMapper::toUserDto)
				.toList();		
	}
	
	public Optional<UserDto> getUserById(Long id) {
		Optional<UserEntity> user = userRepository.findById(id);
		
		return user.map(entityMapper::toUserDto);
	}
	
	public Optional<UserDto> updateUser(Long id, UserDto userDto ){
		
		if( userRepository.existsById(id) ) {
			UserEntity userEntityBeforeSave = entityMapper.toUserEntity(userDto);
			userEntityBeforeSave.setId(id);
			
			UserEntity userEntityAfterSave = userRepository.save(userEntityBeforeSave);
			return Optional.of( entityMapper.toUserDto(userEntityAfterSave));
			
		} else {
			return Optional.empty();
		}
		
	}
	
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepository
							.findByUsername(username)
							.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		
		logger.info("Loaded :"+user.toString());
		return entityMapper.toUserDto(user);
	}

	public List<UserDto> getUsersByRole(ERole roleName) {
	    List<UserEntity> users = userRepository.findByRoles_Name(roleName);
	    return users.stream()
	                .map(entityMapper::toUserDto)
	                .collect(Collectors.toList());
	}
	
	public void assignStudentToTeacher(Long studentId, Long teacherId) {
	    UserEntity student = userRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
	    student.setAssignedTeacherId(teacherId);
	    userRepository.save(student);
	}
	
	public List<UserEntity> getAssignedStudents(Long teacherId) {
	    return userRepository.findByAssignedTeacherId(teacherId);
	}

}
