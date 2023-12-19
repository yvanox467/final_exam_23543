package com.example.librarymanagementsystem1.service.interfaces;

import com.example.librarymanagementsystem1.model.Student;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    void saveStudent(Student book);
    Student getStudentById(String id);
    void deleteStudentById(String id);
    Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);

    Page<Student>search(String name, int pageNo, int pageSize, String sortField, String sortDirection);
}


