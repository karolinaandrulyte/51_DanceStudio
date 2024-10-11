package lt.ca.javau10.dancestudio;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lt.ca.javau10.dancestudio.entities.ERole;
import lt.ca.javau10.dancestudio.entities.Role;
import lt.ca.javau10.dancestudio.entities.Student;
import lt.ca.javau10.dancestudio.entities.Teacher;
import lt.ca.javau10.dancestudio.helpers.TimeSlot;
import lt.ca.javau10.dancestudio.helpers.WorkTimeHelper;
import lt.ca.javau10.dancestudio.repositories.RoleRepository;
import lt.ca.javau10.dancestudio.repositories.StudentRepository;
import lt.ca.javau10.dancestudio.repositories.TeacherRepository;

@Component
public class DataSeeder implements CommandLineRunner{

	 private static final Logger logger = LoggerFactory.getLogger(DataSeeder.class);
	 
	 @Autowired
	 StudentRepository studentRepo;
	 
	 @Autowired
	 TeacherRepository teacherRepo;
	 
	 @Autowired
	 RoleRepository roleRepository;
	    
	@Override
	public void run(String... args) throws Exception {
			
		seedStudentAndTeachers();		
		seedRoles();
	}
	
	void seedStudentAndTeachers() {
		if (studentRepo.count() > 0)
			return;
		
		Teacher teacher1 = new Teacher("Alice", "Johnson", "alice.johnson@example.com", "Experienced Dance Instructor", "Bachata", null, null); 
		Teacher teacher2 = new Teacher("Bob", "Smith", "bob.smith@example.com", "Salsa Dance Expert", "Salsa", null, null);
		Teacher teacher3 = new Teacher("Clara", "Williams", "clara.williams@example.com", "Reggaeton Dance Specialist", "Reggaeton", null, null);
        Teacher teacher4 = new Teacher("David", "Bowie", "davidbowie@example.com", "World-renowned son instructor", "Son", null, null);
		teacherRepo.saveAll(Arrays.asList(teacher1, teacher2, teacher3, teacher4));
        
        Student student1 = new Student("John", "Doe", "john.doe@example.com");
        Student student2 = new Student("Jane", "Roe", "jane.roe@example.com");
        Student student3 = new Student("Emily", "Smith", "emily.smith@example.com");
        Student student4 = new Student("Frances", "Jolly", "fjolly@example.com");
        studentRepo.saveAll(Arrays.asList(student1, student2, student3, student4));
        
        teacher1.addStudent(student4);
        teacher2.addStudent(student3);
        teacher3.addStudent(student2);
        teacher4.addStudent(student1);
        
     // Assign WorkTimeHelper slots to teachers
        assignWorkTimeToTeacher(teacher1, teacher2, TimeSlot.MONDAY_18, TimeSlot.WEDNESDAY_18);
        assignWorkTimeToTeacher(teacher3, teacher4, TimeSlot.TUESDAY_19, TimeSlot.THURSDAY_19);

        // Save teachers with their work time slots and assigned students
        teacherRepo.saveAll(Arrays.asList(teacher1, teacher2, teacher3, teacher4));
	}
	
	void seedRoles() {
	
		if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
            logger.info("ROLE_ADMIN added.");
        }
        
        if (roleRepository.findByName(ERole.ROLE_STUDENT).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_STUDENT));
            logger.info("ROLE_STUDENT added.");
        }
        
        if (roleRepository.findByName(ERole.ROLE_TEACHER).isEmpty()) {
            roleRepository.save(new Role(ERole.ROLE_TEACHER));
            logger.info("ROLE_TEACHER added.");
        }		
	}
	
	// Method to assign WorkTimeHelper slots to teachers
    private void assignWorkTimeToTeacher(Teacher teacher1, Teacher teacher2, TimeSlot slot1, TimeSlot slot2) {
        WorkTimeHelper workTime1 = new WorkTimeHelper(slot1);
        WorkTimeHelper workTime2 = new WorkTimeHelper(slot2);

        teacher1.setWorkTimes(Arrays.asList(workTime1));
        teacher2.setWorkTimes(Arrays.asList(workTime2));
    }
}
