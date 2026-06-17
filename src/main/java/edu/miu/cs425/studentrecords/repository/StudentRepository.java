package edu.miu.cs425.studentrecords.repository;

import edu.miu.cs425.studentrecords.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    // Custom query to find Honor Roll students (registered in a course with creditScore >= 5)
    @Query("SELECT DISTINCT s FROM Student s JOIN s.courses c WHERE c.creditScore >= 5")
    List<Student> findHonorRollStudents();
}
