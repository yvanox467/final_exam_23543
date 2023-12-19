package com.example.librarymanagementsystem1.controller;

import com.example.librarymanagementsystem1.model.Student;
import com.example.librarymanagementsystem1.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/studentPage")
    public  String homePage(@ModelAttribute("student") Student student, Model model){


        return findPageited(1,"regNo","asc", model);
    }
    @PostMapping("/studentSave")
    public String saveStudent(@ModelAttribute("student") Student student, Model model){
        studentService.saveStudent(student);
        return "redirect:/studentPage";

    }
    @GetMapping("/studentUpdate/{id}")

    public String updateStudent(@PathVariable(value = "id") String  id, Model model) {
        List<Student> studentList = studentService.getAllStudents();
        model.addAttribute("studentList", studentList);
        Student student = studentService.getStudentById(id);
        model.addAttribute("student", student);

        return "student";
    }
    @GetMapping("/studentDelete/{id}")
    public String deleteStudent(@PathVariable(value = "id")String id, Model model){
        this.studentService.deleteStudentById(id);
        return "redirect:/studentPage";
    }
    @GetMapping("/pageStudent/{pageNo}")
    public String findPageited(@PathVariable(value = "pageNo")int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        int pageSize = 5;
        Page<Student> page = studentService.findPaginated(pageNo, pageSize,sortField, sortDir);
        List<Student> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);

        Student student= new Student();
        model.addAttribute("student", student);


        return"student";

    }  @PostMapping("/pageSearchStudent/{pageNo}")
    public String search(@PathVariable(value = "pageNo")int pageNo,
                         @RequestParam("name") String name,
                         @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        int pageSize = 5;
        Page<Student> page = studentService.search(name,pageNo, pageSize,sortField, sortDir);
        List<Student> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);

        Student student= new Student();
        model.addAttribute("student", student);


        return"student";

    }

}
