package lt.ca.javau10.dancestudio.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
public class SimpleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private SimpleController simpleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testGetAdminPage() throws Exception {
        mockMvc.perform(get("/api/test/admin"))
            .andExpect(status().isOk())
            .andExpect(content().string("Admin page is accessible"));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void testGetTeacherPage() throws Exception {
        mockMvc.perform(get("/api/test/teacher"))
            .andExpect(status().isOk())
            .andExpect(content().string("Teacher page is accessible"));
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    void testGetStudentPage() throws Exception {
        mockMvc.perform(get("/api/test/student"))
            .andExpect(status().isOk())
            .andExpect(content().string("Student page is accessible"));
    }

    @Test
    @WithMockUser(roles = "TEACHER")
    void testGetUserPage() throws Exception {
        mockMvc.perform(get("/api/test/user"))
            .andExpect(status().isOk())
            .andExpect(content().string("User page is accessible"));
    }

    @Test
    void testGetHomePage() throws Exception {
        mockMvc.perform(get("/api/test/all"))
            .andExpect(status().isOk())
            .andExpect(content().string("Home page is accessible"));
    }
}
