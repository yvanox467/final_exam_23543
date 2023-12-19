package com.example.librarymanagementsystem1.controller;

import com.example.librarymanagementsystem1.model.Books;
import com.example.librarymanagementsystem1.model.Type;
import com.example.librarymanagementsystem1.service.interfaces.BooksService;
import com.example.librarymanagementsystem1.service.interfaces.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class BookController {
    @Autowired
    private BooksService bookService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/bookPage")
    public  String homePage(@ModelAttribute("book") Books book, Model model){
        List<Books> bookList = bookService.getAllBooks();
        model.addAttribute("bookList", bookList);
        List<Type> typeList = typeService.getAllType();
        model.addAttribute("typeList", typeList);

        return findPageited(1,"bookName","asc",book, model);
    }
    @PostMapping("/bookSave")
    public String saveBooks(@ModelAttribute("book") Books book, Model model){
        bookService.saveBook(book);
        return "redirect:/";

    }
    @GetMapping("/bookUpdate/{id}")

    public String updateBooks(@PathVariable(value = "id") UUID id, Model model) {
        List<Books> bookList = bookService.getAllBooks();
        model.addAttribute("bookList", bookList);
        Books book = bookService.getBookById(id);
        model.addAttribute("book", book);
        List<Type> typeList = typeService.getAllType();
        model.addAttribute("typeList", typeList);

        return "book";
    }
    @GetMapping("/bookDelete/{id}")
    public String deleteBooks(@PathVariable(value = "id")UUID id, Model model){
        this.bookService.deleteBookById(id);
        return "redirect:/";
    }
    @GetMapping("/pageBook/{pageNo}")
    public String findPageited(@PathVariable(value = "pageNo")int pageNo,
                               @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,@ModelAttribute("book") Books book,
                               Model model) {
        int pageSize = 5;

        Page<Books> page = bookService.findPaginated(pageNo, pageSize,sortField, sortDir);
        List<Books> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);

        List<Books> typeList = bookService.getAllBooks();
        model.addAttribute("bookList", typeList);
        model.addAttribute("book", book);


        return"book";

    }
    @PostMapping("/pageSearchBook/{pageNo}")
    public String search(@PathVariable(value = "pageNo")int pageNo,
                         @RequestParam("name") String name,
                         @RequestParam("sortField") String sortField,
                               @RequestParam("sortDir") String sortDir,@ModelAttribute("book") Books book,
                               Model model) {
        int pageSize = 5;
        Page<Books> page = bookService.search(name,pageNo, pageSize,sortField, sortDir);
        List<Books> listUserInfos = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listUserInfos", listUserInfos);

        List<Books> typeList = bookService.getAllBooks();
        model.addAttribute("bookList", typeList);
        model.addAttribute("book", book);


        return"book";

    }
}
