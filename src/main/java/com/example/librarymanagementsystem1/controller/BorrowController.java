package com.example.librarymanagementsystem1.controller;

import com.example.librarymanagementsystem1.model.Books;
import com.example.librarymanagementsystem1.model.Borrowed;
import com.example.librarymanagementsystem1.model.Student;
import com.example.librarymanagementsystem1.service.interfaces.BooksService;
import com.example.librarymanagementsystem1.service.interfaces.BorrowService;
import com.example.librarymanagementsystem1.service.interfaces.EmailService;
import com.example.librarymanagementsystem1.service.interfaces.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class BorrowController {

    @Autowired
    private BooksService booksService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private BorrowService borrowService;
    @Autowired
    private EmailService emailService;


    @GetMapping("/borrowedBook")
    public String borrowdBook(Model model){

        Books books = new Books();
        List<Books> booksList= booksService.getAllBooks();
        List<Student>studentList= studentService.getAllStudents();
        Student student = new Student();
        Borrowed borrowed = new Borrowed();
        String outOfStock ="";
        List<Borrowed> borrowedList =borrowService.borrowedBooks();
        model.addAttribute("bookList",booksList);
        model.addAttribute("studentList",studentList);
        model.addAttribute("student",student);
        model.addAttribute("books",books);
        model.addAttribute("borrowed",borrowed);
        model.addAttribute("borrowedList",borrowedList);
        model.addAttribute("outOfStock",outOfStock);
        return findPageited(1,"books", "asc", model);
    }
    @PostMapping("/saveBorrowed")
    public String saveBorrowed(@ModelAttribute("borrowed") Borrowed borrowed,
                               @RequestParam(value = "student") String regNUmber,
                               @RequestParam(value = "books") UUID book,
                               Model model){

        Books books = new Books();
        if (booksService.getBookById(book).getQuantity()>=booksService.getBookById(book).getBorrowed() || studentService.getStudentById(regNUmber).getBorrowed()>=5) {
            books.setId(book);
            books.setBorrowed(booksService.getBookById(book).getBorrowed() + 1);
            books.setBookName(booksService.getBookById(book).getBookName());
            books.setDescription(booksService.getBookById(book).getDescription());
            books.setQuantity(booksService.getBookById(book).getQuantity());
            books.setType(booksService.getBookById(book).getType());
            books.setAuthor(booksService.getBookById(book).getAuthor());
            borrowService.saveBorrowed(borrowed);

            Student student= new Student();
            student.setRegNo(regNUmber);
            student.setBorrowed(studentService.getStudentById(regNUmber).getBorrowed()-1);
            student.setStudentName(studentService.getStudentById(regNUmber).getStudentName());
            student.setEmail(studentService.getStudentById(regNUmber).getEmail());
            studentService.saveStudent(student);

            booksService.saveBook(books);

            String to= studentService.getStudentById(regNUmber).getEmail();
            String subject="Book borrowed successful";
            String body ="You have borrowed a book called "+ booksService.getBookById(book).getBookName()
                    +" on "+ LocalDate.now()+" and you are expected to return it on "
                    +LocalDate.now().plusDays(7)+" .Thank you";
            emailService.sendEmail(to,subject,body);
        }
        else{
            String outOfStock = "Borrow not allowed because of either the quantity";
            model.addAttribute("outOfStock",outOfStock);
        }
        return "redirect:/borrowedBook";
    } @GetMapping("/pageBorrow/{pageNo}")
    public String findPageited(@PathVariable(value = "pageNo")int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,
                               Model model) {
        int pageSize = 5;
        Page<Borrowed> page = borrowService.findPaginated(pageNo, pageSize,sortField, sortDir);
        List<Borrowed> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);

        Books books = new Books();
        List<Books> booksList= booksService.getAllBooks();
        List<Student>studentList= studentService.getAllStudents();
        Student student = new Student();
        Borrowed borrowed = new Borrowed();
        String outOfStock ="";
        List<Borrowed> borrowedList =borrowService.borrowedBooks();
        model.addAttribute("bookList",booksList);
        model.addAttribute("studentList",studentList);
        model.addAttribute("student",student);
        model.addAttribute("books",books);
        model.addAttribute("borrowed",borrowed);
        model.addAttribute("borrowedList",borrowedList);
        model.addAttribute("outOfStock",outOfStock);


        return"borrowBook";

    }

    @PostMapping("/pageSearchBorrow/{pageNo}")
public String search(@PathVariable(value = "pageNo")int pageNo,
                     @RequestParam("name") String name,
                     @RequestParam("sortField") String sortField,
                           @RequestParam("sortDir") String sortDir,
                           Model model) {
    int pageSize = 5;
    Page<Borrowed> page = borrowService.search(name,pageNo, pageSize,sortField, sortDir);
    List<Borrowed> listUserInfos = page.getContent();
    model.addAttribute("currentPage", pageNo);
    model.addAttribute("totalPages", page.getTotalPages());
    model.addAttribute("totalItems", page.getTotalElements());

    model.addAttribute("sortField", sortField);
    model.addAttribute("sortDir", sortDir);
    model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

    model.addAttribute("listUserInfos", listUserInfos);

    Books books = new Books();
    List<Books> booksList= booksService.getAllBooks();
    List<Student>studentList= studentService.getAllStudents();
    Student student = new Student();
    Borrowed borrowed = new Borrowed();
    String outOfStock ="";
    List<Borrowed> borrowedList =borrowService.borrowedBooks();
    model.addAttribute("bookList",booksList);
    model.addAttribute("studentList",studentList);
    model.addAttribute("student",student);
    model.addAttribute("books",books);
    model.addAttribute("borrowed",borrowed);
    model.addAttribute("borrowedList",borrowedList);
    model.addAttribute("outOfStock",outOfStock);


    return"borrowBook";

}
}
