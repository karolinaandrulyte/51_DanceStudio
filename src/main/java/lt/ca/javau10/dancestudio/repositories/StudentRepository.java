package lt.ca.javau10.dancestudio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.ca.javau10.dancestudio.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>{

	List<Student> findAll();
	
	Student findByFirstName(String firstName);


}
