package com.example.librarymanagementsystem1.repository;

import com.example.librarymanagementsystem1.model.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Optional<UserInfo> findByEmail(String email);
    Page<UserInfo> findAllByEmailContaining (String name, Pageable pageable);
}
