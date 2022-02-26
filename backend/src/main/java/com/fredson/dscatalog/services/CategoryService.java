package com.fredson.dscatalog.services;

import com.fredson.dscatalog.dto.CategoryDTO;
import com.fredson.dscatalog.entities.Category;
import com.fredson.dscatalog.repositories.CategoryRepository;
import com.fredson.dscatalog.services.exceptions.DatabaseException;
import com.fredson.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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
    public Page<CategoryDTO> findAllPaged(Pageable pageRequest) {
        Page<Category> list = categoryRepository.findAll(pageRequest);
        return list.map(category -> new CategoryDTO(category));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        return new CategoryDTO(category.orElseThrow(() -> new ResourceNotFoundException("Category not found!")));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO category) {
        return new CategoryDTO(categoryRepository.save(new Category(category.getName())));
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryUpdate) {
        try {
            Category category = categoryRepository.getById(id);
            category.setName(categoryUpdate.getName());
            return new CategoryDTO(categoryRepository.save(category));
        } catch (EntityNotFoundException exception) {
            throw new ResourceNotFoundException("Category not found!");
        }
    }

    public void delete(Long id) {
        try {
            categoryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("Category not found!");
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
