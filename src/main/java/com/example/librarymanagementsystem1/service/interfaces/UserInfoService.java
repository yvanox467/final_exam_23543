package com.example.librarymanagementsystem1.service.interfaces;

import com.example.librarymanagementsystem1.model.UserInfo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserInfoService {
    List<UserInfo> getAllUserInfo();
    void saveUserInfo(UserInfo userInfo);
    UserInfo getUserInfoById(UUID id);
    void deleteUserInfoById(UUID id);

    Optional<UserInfo> getEmail(String email);

    Page<UserInfo> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);
    Page<UserInfo>search(String name, int pageNo, int pageSize, String sortField, String sortDirection);
}
