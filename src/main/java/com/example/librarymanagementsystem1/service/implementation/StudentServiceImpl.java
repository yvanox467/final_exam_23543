package com.example.librarymanagementsystem1.service.implementation;

import com.example.librarymanagementsystem1.model.Student;
import com.example.librarymanagementsystem1.repository.StudentRepository;
import com.example.librarymanagementsystem1.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentsRepository;

    @Override
    public List<Student> getAllStudents() {
        return studentsRepository.findAll();
    }

    @Override
    public void saveStudent(Student students) {
        this.studentsRepository.save(students);

    }

    @Override
    public Student getStudentById(String id) {
        Optional<Student> optional = studentsRepository.findById(id);
        Student students = null;
        if (optional.isPresent()) {
            students = optional.get();
        } else {
            throw new RuntimeException("Students not found for id" + id);
        }
        return students;
    }

    @Override
    public void deleteStudentById(String id) {
        this.studentsRepository.deleteById(id);

    }
    @Override
    public Page<Student> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.studentsRepository.findAll(pageable);
    }

    @Override
    public Page<Student> search(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.studentsRepository.findAllByStudentNameContaining(name,pageable);
    }

}
