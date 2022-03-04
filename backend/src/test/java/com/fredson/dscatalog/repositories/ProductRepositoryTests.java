package com.fredson.dscatalog.repositories;

import com.fredson.dscatalog.entities.Product;
import com.fredson.dscatalog.factories.ProductFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    private long existingId;
    private long notExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        notExistingId = 1000L;
        countTotalProducts = 25;
    }

    @Test
    public void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = ProductFactory.createProduct();
        product.setId(null);
        product = productRepository.save(product);
        Assertions.assertNotNull(product.getId());
        Assertions.assertEquals(countTotalProducts + 1, product.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExists() {
        productRepository.deleteById(existingId);
        Optional<Product> result = productRepository.findById(existingId);
        Assertions.assertFalse(result.isPresent());
    }

    @Test
    public void deleteShouldThrowEmptyDataAccessExceptionWhenIdDoesNotExist() {
        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {
            productRepository.deleteById(notExistingId);
        });
    }

    @Test
    public void findByIdShouldGetObjectWhenIdExist() {
        Optional<Product> result = productRepository.findById(1L);
        Assertions.assertTrue(result.isPresent());
    }

    @Test
    public void findByIdShouldGetObjectWhenIdNotExist() {
        Optional<Product> result = productRepository.findById(100L);
        Assertions.assertTrue(result.isEmpty());
    }
}
