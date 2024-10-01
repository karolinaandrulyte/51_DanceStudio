package lt.ca.javau10.dancestudio.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import lt.ca.javau10.dancestudio.entities.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

	List<Teacher> findAll();

	Teacher findByFirstName(String firstName);

}
