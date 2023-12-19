package com.example.librarymanagementsystem1.controller;

import com.example.librarymanagementsystem1.model.Books;
import com.example.librarymanagementsystem1.model.Borrowed;
import com.example.librarymanagementsystem1.model.Student;
import com.example.librarymanagementsystem1.service.interfaces.BooksService;
import com.example.librarymanagementsystem1.service.interfaces.BorrowService;
import com.example.librarymanagementsystem1.service.interfaces.EmailService;
import com.example.librarymanagementsystem1.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class ReturnController {

    @Autowired
    private BooksService booksService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private EmailService emailService;


    @GetMapping("/returnBook")
    public String borrowdBook(Model model){

        Books books = new Books();
        List<Books> booksList= booksService.getAllBooks();
        List<Student>studentList= studentService.getAllStudents();
        Student student = new Student();
        Borrowed borrowed = new Borrowed();
        String outOfStock ="";
        List<Borrowed> borrowedList =borrowService.showReturnedBooks();
        model.addAttribute("bookList",booksList);
        model.addAttribute("studentList",studentList);
        model.addAttribute("student",student);
        model.addAttribute("books",books);
        model.addAttribute("borrowed",borrowed);
        model.addAttribute("borrowedList",borrowedList);
        model.addAttribute("outOfStock",outOfStock);
        return"returnBook";
    }
    @PostMapping("/saveReturn")
    public String saveBorrowed(@ModelAttribute("borrowed") Borrowed borrowed,
                               @RequestParam(value = "student") String regNUmber,
                               @RequestParam(value = "books") UUID book,
                               @RequestParam(value = "id") UUID id,
                               Model model){

        Books books = new Books();

            if (borrowService.getBorrowedById(id).getDateToBeReturned() == null) {
            books.setId(book);
            books.setBorrowed(booksService.getBookById(book).getBorrowed() + 1);
            books.setBookName(booksService.getBookById(book).getBookName());
            books.setDescription(booksService.getBookById(book).getDescription());
            books.setQuantity(booksService.getBookById(book).getQuantity());
            books.setType(booksService.getBookById(book).getType());
            books.setAuthor(booksService.getBookById(book).getAuthor());

            borrowed.setDateBorrowed(borrowService.getBorrowedById(id).getDateBorrowed());
            borrowed.setDateToBeReturned(LocalDate.now());
            borrowService.saveBorrowed(borrowed);

            Student student= new Student();
            student.setRegNo(regNUmber);
            student.setBorrowed(studentService.getStudentById(regNUmber).getBorrowed()-1);
            student.setStudentName(studentService.getStudentById(regNUmber).getStudentName());
            student.setEmail(studentService.getStudentById(regNUmber).getEmail());
            studentService.saveStudent(student);

            booksService.saveBook(books);

            String to= studentService.getStudentById(regNUmber).getEmail();
            String subject="Book Returned successfully";
            String body ="You have return a book called "+ booksService.getBookById(book).getBookName()
                    +" on "+ LocalDate.now() +" .Thank you";
            emailService.sendEmail(to,subject,body);

        return "redirect:/returnBook";}
            else {
                String message = "Book was already returned";
                System.out.println(message);
                model.addAttribute("message",message);
                return "messageDiplay";
            }
    }
    @GetMapping("/returnBook/{id}")

    public String updateStudent(@PathVariable(value = "id") UUID  id, Model model) {
        Books books = new Books();
        List<Books> booksList= booksService.getAllBooks();
        List<Student>studentList= studentService.getAllStudents();
        Student student = new Student();
        Borrowed borrowed = borrowService.getBorrowedById(id);
        String outOfStock ="";
        List<Borrowed> borrowedList =borrowService.getAllBorroweds();
        model.addAttribute("bookList",booksList);
        model.addAttribute("studentList",studentList);
        model.addAttribute("student",student);
        model.addAttribute("books",books);
        model.addAttribute("borrowed",borrowed);
        model.addAttribute("borrowedList",borrowedList);
        model.addAttribute("outOfStock",outOfStock);
        return "returnBook";
    }

}
