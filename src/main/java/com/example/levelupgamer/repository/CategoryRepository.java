package com.example.levelupgamer.repository;

import com.example.levelupgamer.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
