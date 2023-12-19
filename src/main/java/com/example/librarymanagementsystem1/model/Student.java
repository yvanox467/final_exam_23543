package com.example.librarymanagementsystem1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;

@Data
@Entity

public class Student {
    @Id
    private String regNo;
    private String studentName;
    private String email;
    private Integer borrowed;

    @PrePersist
    public void setDefaultValues() {
        if (borrowed == null) {
            borrowed = 0;
        }
    }

}
