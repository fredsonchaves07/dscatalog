package com.fredson.dscatalog.resources;

import com.fredson.dscatalog.dto.CategoryDTO;
import com.fredson.dscatalog.dto.ProductDTO;
import com.fredson.dscatalog.services.CategoryService;
import com.fredson.dscatalog.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

    @Autowired
    private ProductService service;
    
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPage, Sort.Direction.valueOf(direction), orderBy);
        Page<ProductDTO> productList = service.findAllPaged(pageRequest);
        return ResponseEntity.ok().body(productList);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO product = service.findById(id);
        return ResponseEntity.ok().body(product);
    }
//
//    @PostMapping()
//    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO product) {
//        product = service.insert(product);
//        URI uri = ServletUriComponentsBuilder
//                .fromCurrentRequestUri()
//                .path("/{id}")
//                .buildAndExpand(product.getId())
//                .toUri();
//        return ResponseEntity.created(uri).body(product);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO category) {
//        category = service.update(id, category);
//        return ResponseEntity.ok().body(category);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
