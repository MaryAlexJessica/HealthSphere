package com.healthsphere.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.healthsphere.model.User;
import com.healthsphere.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }
    // ✅ Fetch User entity by username
    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public User createUser(User user, Map<String,Object> metadata) {
        // business logic, save user + audit logging
        return repo.save(user);
    }

    public User updateUserStatus(Long id, User.Status status, Map<String,Object> metadata) {
        User user = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus(status);
        return repo.save(user);
    }
}
