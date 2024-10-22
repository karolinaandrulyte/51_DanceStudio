package lt.ca.javau10.dancestudio.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import lt.ca.javau10.dancestudio.payload.requests.LoginRequest;
import lt.ca.javau10.dancestudio.payload.requests.SignupRequest;
import lt.ca.javau10.dancestudio.payload.responses.JwtResponse;
import lt.ca.javau10.dancestudio.payload.responses.MessageResponse;
import lt.ca.javau10.dancestudio.services.AuthService;

@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("user@example.com"); 
        loginRequest.setPassword("password");  

        JwtResponse jwtResponse = new JwtResponse("token", null, "user@example.com", "user@example.com", null, null, null);

        when(authService.authenticateUser(loginRequest)).thenReturn(jwtResponse);

        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user@example.com\", \"password\":\"password\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.token").value("token"));
    }

    @Test
    void testRegisterUser() throws Exception {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("user@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setEmail("user@example.com");
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");

        MessageResponse response = new MessageResponse("User registered successfully");

        when(authService.registerUser(signUpRequest)).thenReturn(response);

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user@example.com\", \"password\":\"password\", \"email\":\"user@example.com\", \"firstName\":\"John\", \"lastName\":\"Doe\"}"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("User registered successfully"));
    }

    @Test
    void testRegisterUser_ThrowsException() throws Exception {
        SignupRequest signUpRequest = new SignupRequest();
        signUpRequest.setUsername("user@example.com");
        signUpRequest.setPassword("password");
        signUpRequest.setEmail("user@example.com");
        signUpRequest.setFirstName("John");
        signUpRequest.setLastName("Doe");

        ResponseStatusException exception = new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists");

        doThrow(exception).when(authService).registerUser(signUpRequest);

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"user@example.com\", \"password\":\"password\", \"email\":\"user@example.com\", \"firstName\":\"John\", \"lastName\":\"Doe\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.message").value("User already exists"));
    }
}
