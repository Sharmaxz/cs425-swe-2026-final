package edu.miu.cs425.studentrecords.controller;

import edu.miu.cs425.studentrecords.dto.StudentResponse;
import edu.miu.cs425.studentrecords.model.Student;
import edu.miu.cs425.studentrecords.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testGetAllStudents() throws Exception {
        Student student = new Student("E019", "Jennifer", "White", LocalDate.of(2026, 1, 15));
        student.setStudentId(1L);
        StudentResponse response = new StudentResponse(student);

        Mockito.when(studentService.getAllStudents()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Jennifer"));
    }

    @Test
    public void testRegisterStudent() throws Exception {
        Student student = new Student("E776", "Jane", "Dougherty", LocalDate.of(2026, 6, 15));
        student.setStudentId(5L);
        StudentResponse response = new StudentResponse(student);

        Mockito.when(studentService.registerStudent(any(Student.class), eq(3))).thenReturn(response);

        String jsonRequest = """
                {
                    "matricNumber": "E776",
                    "firstName": "Jane",
                    "lastName": "Dougherty",
                    "dateOfAdmission": "2026-06-15"
                }
                """;

        mockMvc.perform(post("/api/v1/students/register?courseId=3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }

    @Test
    public void testRegisterStudent_CourseNotFound_Returns404() throws Exception {
        Mockito.when(studentService.registerStudent(any(Student.class), eq(99)))
                .thenThrow(new IllegalArgumentException("Course not found with ID: 99"));

        String jsonRequest = """
                {
                    "matricNumber": "E776",
                    "firstName": "Jane",
                    "lastName": "Dougherty"
                }
                """;

        mockMvc.perform(post("/api/v1/students/register?courseId=99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Course not found with ID: 99"));
    }

    @Test
    public void testRegisterStudent_ValidationFailed_Returns400() throws Exception {
        String jsonRequest = """
                {
                    "dateOfAdmission": "2026-06-15"
                }
                """;

        mockMvc.perform(post("/api/v1/students/register?courseId=3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.message.firstName").exists())
                .andExpect(jsonPath("$.message.lastName").exists())
                .andExpect(jsonPath("$.message.matricNumber").exists());
    }
}
