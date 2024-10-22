package lt.ca.javau10.dancestudio.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import lt.ca.javau10.dancestudio.entities.ERole;
import lt.ca.javau10.dancestudio.entities.Role;
import lt.ca.javau10.dancestudio.entities.UserDto;
import lt.ca.javau10.dancestudio.services.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        
        Role studentRole = new Role(ERole.ROLE_STUDENT);
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("user@example.com");
        userDto.setRoles(Set.of(studentRole)); 
    }

    @Test
    void testCreateUser() throws Exception {
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(post("/api/users/")
                .contentType("application/json")
                .content("{\"username\":\"user@example.com\", \"email\":\"user@example.com\", \"firstName\":\"John\", \"lastName\":\"Doe\", \"password\":\"password\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("user@example.com"));

        verify(userService, times(1)).createUser(any(UserDto.class));
    }

    @Test
    void testGetAllUsers() throws Exception {
        when(userService.getAllUsers()).thenReturn(List.of(userDto));

        mockMvc.perform(get("/api/users/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("user@example.com"));

        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.of(userDto));

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user@example.com"));

        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testUpdateUser() throws Exception {
        when(userService.updateUser(any(Long.class), any(UserDto.class))).thenReturn(userDto);

        mockMvc.perform(put("/api/users/1")
                .contentType("application/json")
                .content("{\"username\":\"user@example.com\", \"email\":\"user@example.com\", \"firstName\":\"John\", \"lastName\":\"Doe\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("user@example.com"));

        verify(userService, times(1)).updateUser(any(Long.class), any(UserDto.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());

        verify(userService, times(1)).deleteUser(1L);
    }
}
