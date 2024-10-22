package lt.ca.javau10.dancestudio.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lt.ca.javau10.dancestudio.entities.Student;
import lt.ca.javau10.dancestudio.entities.Teacher;
import lt.ca.javau10.dancestudio.services.StudioService;

@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudioService studioService;

    @InjectMocks
    private ApiController apiController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDummyTeacher() throws Exception {
        mockMvc.perform(get("/api/jsonTeacher"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName").value("teachersFirstName"));
    }

    @Test
    void testGetDummyStudent() throws Exception {
        mockMvc.perform(get("/api/jsonStudent"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName").value("studentsFirstName"));
    }

    @Test
    void testGetStudents() throws Exception {
        Student student1 = new Student("John", "Doe", "john@example.com");
        Student student2 = new Student("Jane", "Doe", "jane@example.com");
        List<Student> students = Arrays.asList(student1, student2);

        when(studioService.findAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[1].firstName").value("Jane"));
    }

    @Test
    void testGetTeachers() throws Exception {
        Teacher teacher1 = new Teacher("Alice", "Smith", "alice@example.com", "Description", "Style", null, null);
        Teacher teacher2 = new Teacher("Bob", "Brown", "bob@example.com", "Description", "Style", null, null);
        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);

        when(studioService.findAllTeachers()).thenReturn(teachers);

        mockMvc.perform(get("/api/teachers"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].firstName").value("Alice"))
            .andExpect(jsonPath("$[1].firstName").value("Bob"));
    }

    @Test
    void testGetStudentByFirstName() throws Exception {
        Student student = new Student("John", "Doe", "john@example.com");
        when(studioService.getStudentByFirstName("John")).thenReturn(student);

        mockMvc.perform(get("/api/student?firstName=John"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testGetTeacherByFirstName() throws Exception {
        Teacher teacher = new Teacher("Alice", "Smith", "alice@example.com", "Description", "Style", null, null);
        when(studioService.getTeacherByFirstName("Alice")).thenReturn(teacher);

        mockMvc.perform(get("/api/teacher?firstName=Alice"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    void testAddTeacher() throws Exception {
        Teacher teacher = new Teacher("Alice", "Smith", "alice@example.com", "Description", "Style", null, null);
        when(studioService.addTeacher(any(Teacher.class))).thenReturn(teacher);

        mockMvc.perform(post("/api/addTeacher")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Alice\", \"lastName\":\"Smith\", \"email\":\"alice@example.com\", \"description\":\"Description\", \"style\":\"Style\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("Alice"));
    }

    @Test
    void testAddStudent() throws Exception {
        Student student = new Student("John", "Doe", "john@example.com");
        when(studioService.addStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/addStudent")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"John\", \"lastName\":\"Doe\", \"email\":\"john@example.com\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName").value("John"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studioService).deleteStudentById(1L);

        mockMvc.perform(get("/api/delete/student/1"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void testDeleteTeacher() throws Exception {
        doNothing().when(studioService).deleteTeacherById(1L);

        mockMvc.perform(get("/api/delete/teacher/1"))
            .andExpect(status().is3xxRedirection());
    }
}
