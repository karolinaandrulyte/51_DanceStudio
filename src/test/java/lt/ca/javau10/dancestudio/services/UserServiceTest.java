package lt.ca.javau10.dancestudio.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lt.ca.javau10.dancestudio.entities.UserDto;
import lt.ca.javau10.dancestudio.entities.UserEntity;
import lt.ca.javau10.dancestudio.repositories.UserRepository;
import lt.ca.javau10.dancestudio.utils.EntityMapper;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EntityMapper entityMapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        userService = new UserService(userRepository, entityMapper);
    }
    
    private UserEntity mockUser() {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setAssignedTeacherId(2L); 
        return user;
    }
    
    private UserDto mockUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testuser");
        userDto.setEmail("testuser@example.com");
        return userDto;
    }

    @Test
    public void testLoadUserByUsername_Success() {
        String username = "testuser";
        UserEntity mockUser = mockUser();
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        when(entityMapper.toUserDto(mockUser)).thenReturn(mockUserDto());

        UserDto userDetails = (UserDto) userService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void testLoadUserByUsername_NotFound() {
        String username = "nonexistent";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }

    @Test
    public void testGetAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(mockUser()));

        List<UserDto> users = userService.getAllUsers();

        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testAssignStudentToTeacher() {
        Long studentId = 1L;
        Long teacherId = 2L;
        UserEntity mockStudent = mockUser();

        when(userRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));

        userService.assignStudentToTeacher(studentId, teacherId);

        verify(userRepository).save(mockStudent);
        assertEquals(teacherId, mockStudent.getAssignedTeacherId());
    }

}
