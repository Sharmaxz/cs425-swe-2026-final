package edu.miu.cs425.studentrecords.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String courseCode;

    @NotBlank
    @Column(nullable = false)
    private String courseName;

    @NotNull
    @Column(nullable = false)
    private Integer creditScore;

    @ManyToMany(mappedBy = "courses")
    @JsonIgnore // To prevent infinite recursion in JSON serialization
    private List<Student> students = new ArrayList<>();

    public Course() {
    }

    public Course(String courseCode, String courseName, Integer creditScore) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.creditScore = creditScore;
    }

    // region Getters and Setters

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    // endregion Getters and Setters
}
