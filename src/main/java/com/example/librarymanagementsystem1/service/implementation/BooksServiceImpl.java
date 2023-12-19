package com.example.librarymanagementsystem1.service.implementation;


import com.example.librarymanagementsystem1.model.Books;
import com.example.librarymanagementsystem1.repository.BooksRepository;
import com.example.librarymanagementsystem1.service.interfaces.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BooksServiceImpl implements BooksService {
    @Autowired
    private BooksRepository booksRepository;

    @Override
    public List<Books> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    public void saveBook(Books books) {
        this.booksRepository.save(books);

    }

    @Override
    public Books getBookById(UUID id) {
        Optional<Books> optional = booksRepository.findById(id);
        Books books = null;
        if (optional.isPresent()) {
            books = optional.get();
        } else {
            throw new RuntimeException("Books not found for id" + id);
        }
        return books;
    }

    @Override
    public void deleteBookById(UUID id) {
        this.booksRepository.deleteById(id);

    }
    @Override
    public Page<Books> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.booksRepository.findAll(pageable);
    }

    @Override
    public Page<Books> search(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.booksRepository.findAllByBookNameContaining(name,pageable);
    }


}