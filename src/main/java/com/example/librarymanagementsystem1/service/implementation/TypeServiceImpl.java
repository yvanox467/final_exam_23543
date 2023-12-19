package com.example.librarymanagementsystem1.service.implementation;

import com.example.librarymanagementsystem1.model.Type;
import com.example.librarymanagementsystem1.repository.TypeRepository;
import com.example.librarymanagementsystem1.service.interfaces.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;

    @Override
    public List<Type> getAllType() {
        return typeRepository.findAll();
    }

    @Override
    public void saveType(Type type) {
        this.typeRepository.save(type);

    }

    @Override
    public Type getTypeById(UUID id) {
        Optional<Type> optional = typeRepository.findById(id);
        Type type = null;
        if (optional.isPresent()) {
            type = optional.get();
        } else {
            throw new RuntimeException("Type not found for id" + id);
        }
        return type;
    }

    @Override
    public void deleteTypeById(UUID id) {
        this.typeRepository.deleteById(id);

    }
    @Override
    public Page<Type> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.typeRepository.findAll(pageable);
    }

    @Override
    public Page<Type> search(String name, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable= PageRequest.of(pageNo -1,pageSize,sort);
        return this.typeRepository.findAllByTypeNameContaining(name,pageable);
    }


}