package com.example.librarymanagementsystem1.service.implementation;

import com.example.librarymanagementsystem1.model.UserInfo;
import com.example.librarymanagementsystem1.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoRepository repository;


    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User saved successfully";
    }
}
