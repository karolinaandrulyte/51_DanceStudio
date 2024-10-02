package lt.ca.javau10.dancestudio.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lt.ca.javau10.dancestudio.entities.Student;
import lt.ca.javau10.dancestudio.entities.Teacher;
import lt.ca.javau10.dancestudio.services.StudioService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ApiController {
	
	@GetMapping("/jsonTeacher") // gaunam tiesiog student
	public Teacher getDummyTeacher() {
		return new Teacher("teachersFirstName", "teachersLastName", "teachers.email@example.com", "Teacher description", "Teacher style", null, null);
	}
	
	@GetMapping("/jsonStudent") // gaunam tiesiog student
	public Student getDummyStudent() {
		return new Student("studentsFirstName", "studentsLastName", "students.email@example.com");
	}

	private final StudioService studioService;
	
	public ApiController(StudioService studioService) {
		this.studioService = studioService;
	}
	
	@GetMapping("/students")
	public List<Student> getStudents() {
		return studioService.findAllStudents();
	}
	
	@GetMapping("/teachers")
	public List<Teacher> getTeachers() {
		return studioService.findAllTeachers();
	}
	 
	@GetMapping("/student")
	public Student getStudentByFirstName(@RequestParam String firstName) {
		return studioService.getStudentByFirstName(firstName);
	}
		
	@GetMapping("/teacher")
	public Teacher getTeacherByFirstName(@RequestParam String firstName) {
		return studioService.getTeacherByFirstName(firstName);		
	}

	@GetMapping("/students/{id}")
	public Student getStudentById(@PathVariable Long id) {
		return studioService.getStudentById(id);
	}
	
	@GetMapping("/teachers/{id}")
	public Teacher getOrgById(@PathVariable Long id) {
		return studioService.getTeacherById(id);
	}	
	
//	4) Su postman turi veikti post užklausa, pridedanti vieną objektą ir įrašyti į DB
	@PostMapping("/addTeacher")
	public Teacher addTeacher(@RequestBody Teacher teacher) {
		return studioService.addTeacher(teacher);
	}
	
	@PostMapping("/addStudent") 
	public Student addStudent(@RequestBody Student student) {
		return studioService.addStudent(student);
	}
//	DeleteMapping pažymėtas endpoint turi ištrinti vieną objektą pagal id. (po to patikrinti DB ir susijusių objektų lenteles ir įrašus)
	@GetMapping("/delete/student/{id}")
	public String deleteStudent(@PathVariable Long id) {
		studioService.deleteStudentById(id);
		return "redirect:/students";
	}
	
	@GetMapping("/delete/teacher/{id}")
	public String deleteTeacher(@PathVariable Long id) {
		studioService.deleteTeacherById(id);
		return "redirect:/teachers";
	}
	
}
