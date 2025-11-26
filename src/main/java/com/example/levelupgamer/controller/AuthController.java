package com.example.levelupgamer.controller;

import com.example.levelupgamer.config.JwtUtil;
import com.example.levelupgamer.model.User;
import com.example.levelupgamer.dto.AuthRequest;
import com.example.levelupgamer.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth") // Asegúrate que en Android tu @POST apunte aquí (ej: "auth/login")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "*"}) // "*" permite conexiones desde el emulador/celular
public class AuthController {

    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    // --- REGISTRO (Soluciona el crash en Android) ---
    @PostMapping("/register")
    public org.springframework.http.ResponseEntity<User> register(@RequestBody User u) {
        // Guardamos el usuario en la BD
        User nuevoUsuario = userService.register(u);

        // Devolvemos el objeto JSON completo. 
        // Retrofit espera un objeto JSON, si devolvemos un String plano, explota.
        return org.springframework.http.ResponseEntity.ok(nuevoUsuario);
    }

    // --- LOGIN ---
    @PostMapping("/login")
    public org.springframework.http.ResponseEntity<?> login(@RequestBody AuthRequest req) {
        String email = req.getEmail();
        if (email == null || email.isBlank()) {
            return org.springframework.http.ResponseEntity.badRequest().body("Email requerido");
        }

        User userFound = userService.findByEmail(email);

        if (userFound == null) {
            return org.springframework.http.ResponseEntity.status(404).body("Usuario no registrado");
        }

        try {
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, req.getPassword())
            );

            if (auth.isAuthenticated()) {
                String token = jwtUtil.generateToken(userFound.getEmail());

                // Devolvemos el usuario encontrado para que la App pueda guardar nombre/apellido
                // (Podrías devolver un DTO más limpio con el token incluido, pero esto funciona con tu código actual)
                return org.springframework.http.ResponseEntity.ok(userFound);
            }
            return org.springframework.http.ResponseEntity.status(401).body("No autenticado");
        } catch (AuthenticationException ex) {
            return org.springframework.http.ResponseEntity.status(401).body("No autenticado: " + ex.getMessage());
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PutMapping("/users/{id}")
    public org.springframework.http.ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        // Buscamos el usuario existente por el ID de la URL
        User user = userService.findById(id);

        if (user != null) {
            // Actualizamos los campos
            user.setNombre(userDetails.getNombre());
            user.setApellido(userDetails.getApellido());
            user.setEmail(userDetails.getEmail());
            user.setUsername(userDetails.getUsername());

            // IMPORTANTE: Aseguramos que el objeto a guardar tenga el MISMO ID que buscamos
            user.setId(id);

            // Contraseña opcional
            if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
                user.setPassword(userDetails.getPassword());
            }

            // Al tener el ID seteado, .save() hará un UPDATE, no un INSERT
            User updatedUser = userService.save(user);
            return org.springframework.http.ResponseEntity.ok(updatedUser);
        }
        return org.springframework.http.ResponseEntity.notFound().build();
    }

    // 3. Eliminar: DELETE /api/auth/users/{id}
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }
}
