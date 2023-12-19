package com.example.librarymanagementsystem1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;
@Entity
@Data
public class Type {
    @Id
    @GeneratedValue
    private UUID id;
    private String typeName;
}
