package com.example.librarymanagementsystem1.service.interfaces;


import com.example.librarymanagementsystem1.model.Type;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

;

public interface TypeService {
    List <Type>  getAllType();
    void saveType(Type type);
    Type getTypeById(UUID id);
    void deleteTypeById(UUID id);

    Page<Type> findPaginated(int pageNo, int pageSize, String sortField, String sortDir);

    Page<Type>search(String name, int pageNo, int pageSize, String sortField, String sortDirection);
}


