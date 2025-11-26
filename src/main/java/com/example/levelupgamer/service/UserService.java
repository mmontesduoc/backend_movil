package com.example.levelupgamer.service;

import com.example.levelupgamer.model.User;
import com.example.levelupgamer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public User register(User u) {
        u.setPassword(encoder.encode(u.getPassword()));
        return repo.save(u);
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    
    public List<User> findAll() {
        return repo.findAll();
    }

    
    public User findById(Long id) {
        return repo.findById(id).orElse(null);
    }

    
    public User save(User user) {
        return repo.save(user);
    }

    
    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
