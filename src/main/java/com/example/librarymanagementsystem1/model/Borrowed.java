package com.example.librarymanagementsystem1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.UUID;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Borrowed {
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private Books books;
    @ManyToOne
    private Student student;
    @CreationTimestamp
    private LocalDate dateBorrowed;
    private LocalDate dateToBeReturned;

}
