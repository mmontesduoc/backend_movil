package com.example.levelupgamer.service;

import com.example.levelupgamer.model.CartItem;
import com.example.levelupgamer.model.Product;
import com.example.levelupgamer.repository.CartItemRepository;
import com.example.levelupgamer.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartRepo;
    private final ProductRepository productRepo;

    public List<CartItem> getCart(String username) {
        return cartRepo.findByUsername(username);
    }

    public CartItem addToCart(String username, Long productId, Integer quantity) {
        Product p = productRepo.findById(productId).orElseThrow();
        if (p.getStock() < quantity)
            throw new RuntimeException("Stock insuficiente");
        p.setStock(p.getStock() - quantity);
        productRepo.save(p);
        CartItem it = new CartItem();
        it.setUsername(username);
        it.setProduct(p);
        it.setQuantity(quantity);
        return cartRepo.save(it);
    }

    public void removeItem(Long id) {
        CartItem it = cartRepo.findById(id).orElseThrow();
        Product p = it.getProduct();
        p.setStock(p.getStock() + it.getQuantity());
        productRepo.save(p);
        cartRepo.deleteById(id);
    }
}
