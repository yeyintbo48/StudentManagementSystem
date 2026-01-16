package com.management.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.management.student.model.Student;

public interface ServiceRepository extends JpaRepository<Student,Long> {
    
}
