package com.management.student.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.management.student.dto.StudentDTO;
import com.management.student.model.Student;
import com.management.student.repository.StudentRepository;

@Service
public class StudentService{
    @Autowired
    private StudentRepository studentRepository;

    public StudentDTO convertToDTO(Student student){
        StudentDTO dto = new StudentDTO();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setEmail(student.getEmail());
        dto.setAttendingRoom(student.getAttendingRoom());
        dto.setRollNumber(student.getRollNumber());
        return dto;
    }

    private final String UPLOAD_DIR = "uploads/profile_images/";
    
    public String uploadImage(Long id,MultipartFile file) throws IOException{
        Student student = studentRepository.findById(id).orElseThrow(()->new RuntimeException("Student not found"));

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if(!Files.exists(uploadPath))
            Files.createDirectories(uploadPath);

        String fileName = id + "_" + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);

        Files.copy(file.getInputStream(),filePath,StandardCopyOption.REPLACE_EXISTING);

        student.setProfileImagePath(fileName);
        studentRepository.save(student);

        return "Image uploaded successfully:" + fileName;
    }

    public List<StudentDTO> getAllStudentDTOs(){
        return studentRepository.findAll().stream().map(this::convertToDTO).toList();
    }

    public Page<Student> getAllStudentsPaginated(int page,int size,String sortBy,String sortDir){
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        return studentRepository.findAll(pageable);
    }

    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id).orElse(null);
    }

    public Student updateStudent(Long id,Student student){
        Student existingStudent = studentRepository.findById(student.getId()).orElse(null);
        if(existingStudent != null){
            existingStudent.setName(student.getName());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setAttendingRoom(student.getAttendingRoom());
            existingStudent.setRollNumber(student.getRollNumber());
            return studentRepository.save(existingStudent);
        }
        return null;
    }

    public void deleteStudent(Long id){
         studentRepository.deleteById(id);
    }

    public List<Student> searchStudentsByName(String name){
        return studentRepository.findByNameContainingIgnoreCase(name);
    }
}