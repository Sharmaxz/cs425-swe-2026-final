package edu.miu.cs425.studentrecords.dto;

import edu.miu.cs425.studentrecords.model.Course;
import edu.miu.cs425.studentrecords.model.Student;
import java.time.LocalDate;
import java.util.List;

public class StudentResponse {
    private Long studentId;
    private String matricNumber;
    private String firstName;
    private String lastName;
    private LocalDate dateOfAdmission;
    private List<Course> courses;
    private Integer totalCreditScore;

    public StudentResponse(Student student) {
        this.studentId = student.getStudentId();
        this.matricNumber = student.getMatricNumber();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.dateOfAdmission = student.getDateOfAdmission();
        this.courses = student.getCourses();
        this.totalCreditScore = student.getCourses().stream()
                .mapToInt(Course::getCreditScore)
                .sum();
    }

    public Long getStudentId() { return studentId; }
    public String getMatricNumber() { return matricNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getDateOfAdmission() { return dateOfAdmission; }
    public List<Course> getCourses() { return courses; }
    public Integer getTotalCreditScore() { return totalCreditScore; }
}
