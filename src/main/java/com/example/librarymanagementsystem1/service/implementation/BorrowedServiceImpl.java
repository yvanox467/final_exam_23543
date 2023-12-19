package com.example.librarymanagementsystem1.service.implementation;

import com.example.librarymanagementsystem1.model.Borrowed;
import com.example.librarymanagementsystem1.repository.BorrowedRepository;
import com.example.librarymanagementsystem1.service.interfaces.BorrowService;
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
public class BorrowedServiceImpl implements BorrowService {
    @Autowired
    private BorrowedRepository borrowedsRepository;

    @Override
    public List<Borrowed> getAllBorroweds() {
        return borrowedsRepository.findAll();
    }

    @Override
    public void saveBorrowed(Borrowed borroweds) {
        this.borrowedsRepository.save(borroweds);

    }

    @Override
    public Borrowed getBorrowedById(UUID id) {
        Optional<Borrowed> optional = borrowedsRepository.findById(id);
        Borrowed borroweds = null;
        if (optional.isPresent()) {
            borroweds = optional.get();
        } else {
            throw new RuntimeException("Borroweds not found for id" + id);
        }
        return borroweds;
    }

    @Override
    public void deleteBorrowedById(UUID id) {
        this.borrowedsRepository.deleteById(id);

    }

    @Override
    public List<Borrowed> borrowedBooks() {
        return borrowedsRepository.findBorrowedByDateToBeReturnedIsNull();
    }

    @Override
    public List<Borrowed> showReturnedBooks() {
        return borrowedsRepository.findBorrowedByDateToBeReturnedIsNotNull();
    }
    @Override
    public Page<Borrowed> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.borrowedsRepository.findAll(pageable);
    }

    @Override
    public Page<Borrowed> search(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.borrowedsRepository.findBorrowedByBooksContaining(name,pageable);
    }
}
