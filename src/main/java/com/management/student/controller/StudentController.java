package com.management.student.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.management.student.dto.StudentDTO;
import com.management.student.model.Student;
import com.management.student.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management",description = "APIs for managing students")
public class StudentController{
    @Autowired
    private StudentService studentService;

    @Operation(summary = "Create a new student",description = "Create a new student with the provided details")
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student){
        Student saveStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(saveStudent);
    }

    @GetMapping
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        Student student = studentService.getStudentById(id);
        return student != null ? ResponseEntity.ok(student) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        return "Student deleted successfully with id:" + id;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,@Valid @RequestBody Student studentDetails){
        Student updateStudent = studentService.updateStudent(id,studentDetails);
        return updateStudent != null ? ResponseEntity.ok(updateStudent) : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public List<Student> searchStudents(@RequestParam String name){
        return studentService.searchStudentsByName(name);
    }

    @Operation(summary = "Get paginated list of students",description = "Retrieve a paginated list of students with sorting options")
    @GetMapping("/paged")
    public ResponseEntity<Page<Student>> getStudentsPage(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDir){

        Page<Student> students = studentService.getAllStudentsPaginated(page,size,sortBy,sortDir);
        return ResponseEntity.ok(students);
    }

    @GetMapping("/dtos")
        public List<StudentDTO> getAllStudentDTOs(){
            return studentService.getAllStudentDTOs();
        }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadProfileImage(@PathVariable Long id,@RequestParam("file") MultipartFile file){
        try{
            String response = studentService.uploadImage(id,file);
            return ResponseEntity.ok(response);
        }catch(IOException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image:" + e.getMessage());
        }
    }    
}
