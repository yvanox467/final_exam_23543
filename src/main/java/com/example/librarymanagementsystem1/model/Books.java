package com.example.librarymanagementsystem1.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;
@Entity
@Data
public class Books {
@Id
@GeneratedValue
    private UUID id;
    private String bookName;
    private String description;
    private String author;
    private Integer quantity;
    private Integer borrowed;
    @ManyToOne
    private Type type;

    @PrePersist
    public void setNNoOfBooks() {
        if (borrowed == null) {
            borrowed = 0;
        }
    }

}
