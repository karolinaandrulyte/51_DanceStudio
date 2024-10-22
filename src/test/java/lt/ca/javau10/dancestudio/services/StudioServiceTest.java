package lt.ca.javau10.dancestudio.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import lt.ca.javau10.dancestudio.entities.Student;
import lt.ca.javau10.dancestudio.entities.Teacher;
import lt.ca.javau10.dancestudio.repositories.StudentRepository;
import lt.ca.javau10.dancestudio.repositories.TeacherRepository;

import java.util.List;

class StudioServiceTest {

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private TeacherRepository teacherRepo;

    @InjectMocks
    private StudioService studioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllStudents() {
        Student student1 = new Student();
        Student student2 = new Student();
        when(studentRepo.findAll()).thenReturn(Arrays.asList(student1, student2));

        List<Student> students = studioService.findAllStudents();

        assertEquals(2, students.size());
        verify(studentRepo, times(1)).findAll();
    }

    @Test
    void testFindAllTeachers() {
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        when(teacherRepo.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        List<Teacher> teachers = studioService.findAllTeachers();

        assertEquals(2, teachers.size());
        verify(teacherRepo, times(1)).findAll();
    }

    @Test
    void testGetStudentByFirstName() {
        Student student = new Student();
        student.setFirstName("John");
        when(studentRepo.findByFirstName("John")).thenReturn(student);

        Student foundStudent = studioService.getStudentByFirstName("John");

        assertNotNull(foundStudent);
        assertEquals("John", foundStudent.getFirstName());
        verify(studentRepo, times(1)).findByFirstName("John");
    }

    @Test
    void testGetTeacherByFirstName() {
        Teacher teacher = new Teacher();
        teacher.setFirstName("Jane");
        when(teacherRepo.findByFirstName("Jane")).thenReturn(teacher);

        Teacher foundTeacher = studioService.getTeacherByFirstName("Jane");

        assertNotNull(foundTeacher);
        assertEquals("Jane", foundTeacher.getFirstName());
        verify(teacherRepo, times(1)).findByFirstName("Jane");
    }

    @Test
    void testGetStudentById() {
        Student student = new Student();
        student.setId(1L);
        when(studentRepo.findById(1L)).thenReturn(Optional.of(student));

        Student foundStudent = studioService.getStudentById(1L);

        assertNotNull(foundStudent);
        assertEquals(1L, foundStudent.getId());
        verify(studentRepo, times(1)).findById(1L);
    }

    @Test
    void testGetTeacherById() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        when(teacherRepo.findById(1L)).thenReturn(Optional.of(teacher));

        Teacher foundTeacher = studioService.getTeacherById(1L);

        // Assert
        assertNotNull(foundTeacher);
        assertEquals(1L, foundTeacher.getId());
        verify(teacherRepo, times(1)).findById(1L);
    }

    @Test
    void testSaveStudent() {
        Student student = new Student();

        studioService.saveStudent(student);

        verify(studentRepo, times(1)).save(student);
    }

    @Test
    void testSaveTeacher() {
        Teacher teacher = new Teacher();

        studioService.saveTeacher(teacher);

        verify(teacherRepo, times(1)).save(teacher);
    }

    @Test
    void testAddStudent() {
        Student student = new Student();
        when(studentRepo.save(student)).thenReturn(student);

        Student addedStudent = studioService.addStudent(student);

        assertNotNull(addedStudent);
        verify(studentRepo, times(1)).save(student);
    }

    @Test
    void testAddTeacher() {
        Teacher teacher = new Teacher();
        when(teacherRepo.save(teacher)).thenReturn(teacher);

        Teacher addedTeacher = studioService.addTeacher(teacher);

        assertNotNull(addedTeacher);
        verify(teacherRepo, times(1)).save(teacher);
    }

    @Test
    void testUpdateStudent() {
        Student oldStudent = new Student();
        oldStudent.setId(1L);
        oldStudent.setFirstName("John");

        Student newStudent = new Student();
        newStudent.setFirstName("John Updated");

        when(studentRepo.findById(1L)).thenReturn(Optional.of(oldStudent));

        studioService.updateStudent(1L, newStudent);

        assertEquals("John Updated", oldStudent.getFirstName());
        verify(studentRepo, times(1)).save(oldStudent);
    }

    @Test
    void testUpdateTeacher() {
        Teacher oldTeacher = new Teacher();
        oldTeacher.setId(1L);
        oldTeacher.setFirstName("Jane");

        Teacher newTeacher = new Teacher();
        newTeacher.setFirstName("Jane Updated");

        when(teacherRepo.findById(1L)).thenReturn(Optional.of(oldTeacher));

        studioService.updateTeacher(1L, newTeacher);

        assertEquals("Jane Updated", oldTeacher.getFirstName());
        verify(teacherRepo, times(1)).save(oldTeacher);
    }

    @Test
    void testDeleteStudentById() {
        studioService.deleteStudentById(1L);

        verify(studentRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTeacherById() {
        studioService.deleteTeacherById(1L);

        verify(teacherRepo, times(1)).deleteById(1L);
    }
}
