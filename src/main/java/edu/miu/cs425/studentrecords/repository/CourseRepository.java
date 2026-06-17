package edu.miu.cs425.studentrecords.repository;

import edu.miu.cs425.studentrecords.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
}
