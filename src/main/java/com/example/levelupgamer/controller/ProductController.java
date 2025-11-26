package com.example.levelupgamer.controller;

import com.example.levelupgamer.model.Product;
import com.example.levelupgamer.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @GetMapping
    public List<Product> all() {
        return service.findAll();
    }

    @GetMapping("/category/{id}")
    public List<Product> byCategory(@PathVariable Long id) {
        return service.findByCategory(id);
    }

    @PostMapping
    public Product create(@RequestBody Product p) {
        return service.save(p);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product p) {
        return service.update(id, p);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}