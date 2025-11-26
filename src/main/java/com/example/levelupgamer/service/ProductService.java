package com.example.levelupgamer.service;

import com.example.levelupgamer.model.Product;
import com.example.levelupgamer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repo;

    public List<Product> findAll() {
        return repo.findAll();
    }

    public List<Product> findByCategory(Long catId) {
        return repo.findByCategoryId(catId);
    }

    public Product save(Product p) {
        return repo.save(p);
    }

    public Product update(Long id, Product p) {
        Product prod = repo.findById(id).orElseThrow();
        prod.setName(p.getName());
        prod.setDescription(p.getDescription());
        prod.setPrice(p.getPrice());
        prod.setStock(p.getStock());
        prod.setCategory(p.getCategory());
        return repo.save(prod);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
