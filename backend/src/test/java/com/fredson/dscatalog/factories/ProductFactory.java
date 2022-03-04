package com.fredson.dscatalog.factories;

import com.fredson.dscatalog.dto.ProductDTO;
import com.fredson.dscatalog.entities.Category;
import com.fredson.dscatalog.entities.Product;

import java.time.Instant;

public class ProductFactory {

    public static Product createProduct() {
        Product product = new Product("Phone", "Good Phone", 800.0, "https://img.com/img.png", Instant.parse("2020-07-14T10:00:00Z"));
        product.getCategories().add(new Category(2L, "Electronics"));
        return product;
    }

    public static ProductDTO createProductDTO() {
        Product product = createProduct();
        return new ProductDTO(product, product.getCategories());
    }
}
