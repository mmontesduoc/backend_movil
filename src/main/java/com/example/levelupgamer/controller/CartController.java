package com.example.levelupgamer.controller;

import com.example.levelupgamer.model.CartItem;
import com.example.levelupgamer.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/{username}")
    public List<CartItem> getCart(@PathVariable String username) {
        return cartService.getCart(username);
    }

    @PostMapping("/add")
    public CartItem add(@RequestParam String username, @RequestParam Long productId, @RequestParam Integer quantity) {
        return cartService.addToCart(username, productId, quantity);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        cartService.removeItem(id);
    }
}
