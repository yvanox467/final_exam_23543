package com.example.librarymanagementsystem1.repository;


import com.example.librarymanagementsystem1.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StudentRepository extends JpaRepository<Student, String> {
    Page<Student> findAllByStudentNameContaining (String name, Pageable pageable);
}
