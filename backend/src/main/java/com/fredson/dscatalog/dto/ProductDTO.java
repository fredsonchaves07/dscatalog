package com.fredson.dscatalog.dto;

import com.fredson.dscatalog.entities.Category;
import com.fredson.dscatalog.entities.Product;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Set;

public class ProductDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Double price;
    private String imgUrl;
    private String description;
    private Instant date;
    private List<CategoryDTO> categories;

    public ProductDTO() {}

    public ProductDTO(String name, Double price, String imgUrl, String description, Instant date) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
        this.description = description;
        this.date = date;
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrl = product.getImgUrl();
        this.date = product.getDate();
    }

    public ProductDTO(Product product, Set<Category> categories) {
        this(product);
        categories.forEach(category -> this.categories.add(new CategoryDTO(category)));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
