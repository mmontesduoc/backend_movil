package com.example.levelupgamer.controller;

import com.example.levelupgamer.model.User;
import com.example.levelupgamer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "*"})
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // --- AGREGAR ESTE MÉTODO PARA PODER EDITAR ---
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {

        // Asegúrate que tu UserService tenga un método update o hazlo aquí:
        User user = userService.findById(id); // Necesitas un método para buscar por ID
        if (user != null) {
            user.setNombre(userDetails.getNombre());
            user.setApellido(userDetails.getApellido());
            user.setEmail(userDetails.getEmail());
            user.setUsername(userDetails.getUsername());
            // user.setPassword(...) // Solo si quieres actualizar pass
            return userService.save(user); // Guarda cambios
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
