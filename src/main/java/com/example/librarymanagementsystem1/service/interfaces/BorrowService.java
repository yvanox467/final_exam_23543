package com.example.librarymanagementsystem1.service.interfaces;

import com.example.librarymanagementsystem1.model.Borrowed;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface BorrowService {
    List<Borrowed> getAllBorroweds();
    void saveBorrowed(Borrowed book);
    Borrowed getBorrowedById(UUID id);
    void deleteBorrowedById(UUID id);
    List<Borrowed> borrowedBooks();
    List<Borrowed> showReturnedBooks();

    Page<Borrowed> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);

    Page<Borrowed>search(String name, int pageNo, int pageSize, String sortField, String sortDirection);
}
