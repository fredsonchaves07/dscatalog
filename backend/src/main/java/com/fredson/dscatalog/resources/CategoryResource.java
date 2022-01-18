package com.fredson.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import com.fredson.dscatalog.dto.CategoryDTO;
import com.fredson.dscatalog.entities.Category;

import com.fredson.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;
    
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<CategoryDTO> categoryList = categoryService.findAll();
        return ResponseEntity.ok().body(categoryList);
    }
}
