package com.healthsphere.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.healthsphere.model.User;
import com.healthsphere.repository.UserRepository;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public DataLoader(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        if (!repo.existsByUsername("admin")) {
            User a = new User();
            a.setUsername("admin");
            a.setPassword(encoder.encode("admin123"));
            a.setRole(User.Role.ADMIN); // <-- use enum, not String
            a.setStatus(User.Status.ACTIVE);
            repo.save(a);
            System.out.println("✅ Default admin created: admin / admin123");
        }
    }
}
