package lt.ca.javau10.dancestudio.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lt.ca.javau10.dancestudio.helpers.WorkTimeHelper;

@Entity
public class Teacher {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String firstName;
	private String lastName;
	private String email;
	private String description;
	private String danceStyle;
	
	// List to hold WorkTimeHelper instances //
    @Transient // because WorkTimeHelper is not an entity. It will not be persisted in the database, but can be used in the business logic layer.
    private List<WorkTimeHelper> workTimes = new ArrayList<>();
	
//	One-to-many relationship with Student
    @OneToMany
    @JsonManagedReference // ryšio „owner side“ (valdančioje pusėje). Tai nurodo, kad šis objektas yra tas, kuris bus serializuojamas į JSON.
    @JoinColumn(name = "teacher_id")  // This column will be added to the student table to reference the teacher, Student should not exist without Teacher
    private List<Student> students = new ArrayList<>();  // A teacher can have multiple students
	
	public Teacher() {}
	
	public Teacher(String firstName, String lastName, String email, String description, String danceStyle,
			List<WorkTimeHelper> workTimes, List<Student> students) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.description = description;
		this.danceStyle = danceStyle;
		this.workTimes = workTimes;
		this.students = students;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDanceStyle() {
		return danceStyle;
	}

	public void setDanceStyle(String danceStyle) {
		this.danceStyle = danceStyle;
	}

	 // Get and set work times
    public List<WorkTimeHelper> getWorkTimes() {
        return workTimes;
    }
    
    public void setWorkTimes(List<WorkTimeHelper> workTimes) {
        this.workTimes = workTimes;
    }

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public void addStudent(Student student) {
		students.add(student);
	}
	
}
