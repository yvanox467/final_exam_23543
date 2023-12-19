package com.example.librarymanagementsystem1.service.implementation;

import com.example.librarymanagementsystem1.model.UserInfo;
import com.example.librarymanagementsystem1.repository.UserInfoRepository;
import com.example.librarymanagementsystem1.service.interfaces.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserInfoImpl implements UserInfoService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserInfoRepository repository;


    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User saved successfully";
    }
    @Autowired
    private UserInfoRepository userInfosRepository;

    @Override
    public List<UserInfo> getAllUserInfo() {
        return userInfosRepository.findAll();
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        this.userInfosRepository.save(userInfo);

    }

    @Override
    public UserInfo getUserInfoById(UUID id) {
        Optional<UserInfo> optional = userInfosRepository.findById(id);
        UserInfo userInfos = null;
        if (optional.isPresent()) {
            userInfos = optional.get();
        } else {
            throw new RuntimeException("UserInfos not found for id" + id);
        }
        return userInfos;
    }

    @Override
    public void deleteUserInfoById(UUID id) {
        this.userInfosRepository.deleteById(id);

    }

    @Override
    public Optional<UserInfo> getEmail(String email) {
        return userInfosRepository.findByEmail(email);
    }

    @Override
    public Page<UserInfo> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.repository.findAll(pageable);
    }

    @Override
    public Page<UserInfo> search(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.repository.findAllByEmailContaining(name,pageable);
    }

}
