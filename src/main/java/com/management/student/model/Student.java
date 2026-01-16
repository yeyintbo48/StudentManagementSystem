package com.management.student.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "students")
public class Student{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    @Size(min = 2,message = "name should have at least 2 characters")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email should be valid")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "attendingRoom is required")
    private String attendingRoom;

    @NotBlank(message = "rollNumber is required")
    @Pattern(regexp = "^[0-9]*$", message = "rollNumber should be digits only")
    private String rollNumber;

    private String profileImagePath;
}