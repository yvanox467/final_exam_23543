package com.example.librarymanagementsystem1.repository;

import com.example.librarymanagementsystem1.model.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository

public interface TypeRepository extends JpaRepository<Type, UUID> {
    Page<Type> findAllByTypeNameContaining (String name, Pageable pageable);
}
