package com.fredson.dscatalog.services;

import com.fredson.dscatalog.dto.ProductDTO;
import com.fredson.dscatalog.entities.Category;
import com.fredson.dscatalog.entities.Product;
import com.fredson.dscatalog.repositories.ProductRepository;
import com.fredson.dscatalog.services.exceptions.DatabaseException;
import com.fredson.dscatalog.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> productList = repository.findAll();
        return productList.stream().map(product -> new ProductDTO(product)).toList();
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest) {
        Page<Product> list = repository.findAll(pageRequest);
        return list.map(product -> new ProductDTO(product));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> product = repository.findById(id);
        return new ProductDTO(product.orElseThrow(() -> new ResourceNotFoundException("Product not found!")), product.get().getCategories());
    }

//    @Transactional
//    public ProductDTO insert(ProductDTO product) {
//        return new ProductDTO(repository.save(new Product(product.getName())));
//    }
//
//    @Transactional
//    public ProductDTO update(Long id, ProductDTO productUpdate) {
//        try {
//            Product product = repository.getById(id);
//            product.setName(productUpdate.getName());
//            return new ProductDTO(repository.save(product));
//        } catch (EntityNotFoundException exception) {
//            throw new ResourceNotFoundException("Product not found!");
//        }
//    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException exception) {
            throw new ResourceNotFoundException("Product not found!");
        } catch (DataIntegrityViolationException exception) {
            throw new DatabaseException("Integrity violation");
        }
    }
}
