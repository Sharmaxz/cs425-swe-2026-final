package edu.miu.cs425.studentrecords.controller;

import edu.miu.cs425.studentrecords.dto.StudentResponse;
import edu.miu.cs425.studentrecords.model.Student;
import edu.miu.cs425.studentrecords.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/honor-roll")
    public ResponseEntity<List<StudentResponse>> getHonorRollStudents() {
        return ResponseEntity.ok(studentService.getHonorRollStudents());
    }

    @PostMapping("/register")
    public ResponseEntity<StudentResponse> registerStudent(
            @Valid @RequestBody Student student,
            @RequestParam Integer courseId) {
        StudentResponse response = studentService.registerStudent(student, courseId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
