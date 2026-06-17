package edu.miu.cs425.studentrecords.service;

import edu.miu.cs425.studentrecords.dto.StudentResponse;
import edu.miu.cs425.studentrecords.model.Course;
import edu.miu.cs425.studentrecords.model.Student;
import edu.miu.cs425.studentrecords.repository.CourseRepository;
import edu.miu.cs425.studentrecords.repository.StudentRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll(Sort.by(Sort.Direction.ASC, "lastName"))
                .stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public List<StudentResponse> getHonorRollStudents() {
        return studentRepository.findHonorRollStudents()
                .stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
    }

    public StudentResponse registerStudent(Student student, Integer courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with ID: " + courseId));
        
        student.addCourse(course);
        Student savedStudent = studentRepository.save(student);
        return new StudentResponse(savedStudent);
    }
}
