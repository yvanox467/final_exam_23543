package com.example.librarymanagementsystem1.service.interfaces;

import com.example.librarymanagementsystem1.model.Books;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

;

public interface BooksService {
    List <Books>  getAllBooks();
    void saveBook(Books book);
    Books getBookById(UUID id);
    void deleteBookById(UUID id);
    Page<Books> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
    Page<Books>search(String name, int pageNo, int pageSize, String sortField, String sortDirection);

}
