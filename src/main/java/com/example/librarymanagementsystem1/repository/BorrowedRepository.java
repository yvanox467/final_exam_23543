package com.example.librarymanagementsystem1.repository;

import com.example.librarymanagementsystem1.model.Borrowed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository

public interface BorrowedRepository extends JpaRepository<Borrowed, UUID> {
    List<Borrowed> findBorrowedByDateToBeReturnedIsNull();
    List<Borrowed>findBorrowedByDateToBeReturnedIsNotNull();
    Page<Borrowed> findBorrowedByBooksContaining (String name, Pageable pageable);
}
