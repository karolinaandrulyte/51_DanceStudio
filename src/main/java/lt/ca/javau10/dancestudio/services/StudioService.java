package lt.ca.javau10.dancestudio.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lt.ca.javau10.dancestudio.entities.Student;
import lt.ca.javau10.dancestudio.entities.Teacher;
import lt.ca.javau10.dancestudio.repositories.StudentRepository;
import lt.ca.javau10.dancestudio.repositories.TeacherRepository;

@Service
public class StudioService {

	private final StudentRepository studentRepo;
	private final TeacherRepository teacherRepo;
	
	public StudioService(StudentRepository studentRepo, TeacherRepository teacherRepo) {
		this.studentRepo = studentRepo;
		this.teacherRepo = teacherRepo;
	}
	
	public List<Student> findAllStudents() {
		return studentRepo.findAll();
	}
	
	public List<Teacher> findAllTeachers() {
		return teacherRepo.findAll();
	}
	
	public Student getStudentByFirstName(String firstName) {
		return studentRepo.findByFirstName(firstName);
	}
	
	public Teacher getTeacherByFirstName(String firstName) {
		return teacherRepo.findByFirstName(firstName);
	}
	
	public Student getStudentById(Long id) {
		return studentRepo.findById(id)
				.orElse(new Student());
	}
	
	public Teacher getTeacherById(Long id) {
		return teacherRepo.findById(id)
				.orElse(new Teacher());
	}	
	
	public void saveStudent(Student student) {
		studentRepo.save(student);
	}
	
	public void saveTeacher(Teacher teacher) {
		teacherRepo.save(teacher);
	}
	
	public Student addStudent(Student student) {
		return studentRepo.save(student);
	}
	
	public Teacher addTeacher(Teacher teacher) {
		return teacherRepo.save(teacher);
	}
	
	public void updateStudent(Long id, Student newStudent) {
		Student oldStudent = getStudentById(id);
		oldStudent.setFirstName( newStudent.getFirstName());
		oldStudent.setLastName( newStudent.getLastName());
		oldStudent.setEmail( newStudent.getEmail());
		oldStudent.setTeacher( newStudent.getTeacher());
		studentRepo.save(oldStudent);
	}
	
	public void updateTeacher(Long id, Teacher newTeacher) {
		Teacher oldTeacher = getTeacherById(id);
		oldTeacher.setFirstName( newTeacher.getFirstName());
		oldTeacher.setLastName( newTeacher.getLastName());
		oldTeacher.setEmail( newTeacher.getEmail());
		oldTeacher.setDescription( newTeacher.getDescription());
		oldTeacher.setDanceStyle( newTeacher.getDanceStyle());
		teacherRepo.save(oldTeacher);
	}
	
	public void deleteStudentById(Long id) {
		studentRepo.deleteById(id);
	}
	
	public void deleteTeacherById(Long id) {
		teacherRepo.deleteById(id);
	}
	
}
