package com.fredson.dscatalog.services;

import com.fredson.dscatalog.dto.CategoryDTO;
import com.fredson.dscatalog.entities.Category;
import com.fredson.dscatalog.repositories.CategoryRepository;
import com.fredson.dscatalog.services.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(category -> new CategoryDTO(category)).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return new CategoryDTO(category.orElseThrow(() -> new EntityNotFoundException("Category not found!")));
    }
}
