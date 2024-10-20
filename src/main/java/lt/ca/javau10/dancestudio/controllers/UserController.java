package lt.ca.javau10.dancestudio.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import lt.ca.javau10.dancestudio.entities.ERole;
import lt.ca.javau10.dancestudio.entities.UserDto;
import lt.ca.javau10.dancestudio.entities.UserEntity;
import lt.ca.javau10.dancestudio.services.UserService;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

	private final UserService userService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		UserDto createdUser = userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);		
	}
	
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);	
	}
	
	@GetMapping("/teachers")
	public ResponseEntity<List<UserDto>> getTeachers() {
	    List<UserDto> teachers = userService.getUsersByRole(ERole.ROLE_TEACHER);
	    return new ResponseEntity<>(teachers, HttpStatus.OK);
	}
	
	@GetMapping("/teachers/{teacherId}/students")
	public ResponseEntity<List<UserEntity>> getAssignedStudents(@PathVariable Long teacherId) {
	    List<UserEntity> assignedStudents = userService.getAssignedStudents(teacherId);
	    return ResponseEntity.ok(assignedStudents);
	}

	@GetMapping("/students")
	public ResponseEntity<List<UserDto>> getStudents() {
	    List<UserDto> students = userService.getUsersByRole(ERole.ROLE_STUDENT);
	    return new ResponseEntity<>(students, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id){	
		Optional<UserDto> userInBox = userService.getUserById(id);
		return userInBox
				.map( ResponseEntity::ok )
				.orElseGet( () -> ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
		logger.info("Got request " + userDto);
	    
	    return ResponseEntity.ok(userService.updateUser(id, userDto));  // Return UserDto as the response
	}
	
	@PutMapping("/students/{studentId}/assign/{teacherId}")
	public ResponseEntity<Void> assignStudentToTeacher(
	    @PathVariable Long studentId,
	    @PathVariable Long teacherId) {
	    
	    userService.assignStudentToTeacher(studentId, teacherId);
	    return ResponseEntity.ok().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id){
		userService.deleteUser(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("hasRole('TEACHER')")
	@PutMapping("/students/{studentId}/unassign")
	public ResponseEntity<?> unassignStudentFromTeacher(@PathVariable Long studentId) {
	    userService.unassignStudentFromTeacher(studentId);
	    return ResponseEntity.ok("Student unassigned successfully.");
	}
	
	@PutMapping("/profile/update")
	public ResponseEntity<UserDto> updateTeacherProfile(@RequestBody UserDto userDto, Authentication authentication) {
	    String currentUsername = authentication.getName();
	    UserEntity updatedUser = userService.updateTeacherProfile(currentUsername, userDto);
	    return ResponseEntity.ok(new UserDto(updatedUser));
	}
}
