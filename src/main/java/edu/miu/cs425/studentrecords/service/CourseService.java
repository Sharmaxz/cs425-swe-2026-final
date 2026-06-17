package edu.miu.cs425.studentrecords.service;

import edu.miu.cs425.studentrecords.model.Course;
import edu.miu.cs425.studentrecords.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
