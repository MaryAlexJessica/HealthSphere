package com.healthsphere.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.healthsphere.dto.LoginRequest;
import com.healthsphere.dto.LoginResponse;
import com.healthsphere.dto.SignupRequest;
import com.healthsphere.model.User;
import com.healthsphere.repository.UserRepository;
import com.healthsphere.security.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserRepository repo, PasswordEncoder encoder,
                          JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<LoginResponse> signup(@RequestBody SignupRequest req) {
        System.out.println("Signup request received: " + req.getUsername() + ", role=" + req.getRole());
        if (repo.existsByUsername(req.getUsername()))
            return ResponseEntity.badRequest().body(null);
        if (repo.existsByEmail(req.getEmail()))
            return ResponseEntity.badRequest().body(null);    
        System.out.println("signup");
        User u = new User();
        u.setUsername(req.getUsername());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRole(User.Role.valueOf(req.getRole().toUpperCase()));  // <-- convert String to enum
        u.setEmail(req.getEmail()); 
        String message;
    String token = null;
// 👇 Business rule for status + message
    if (u.getRole() == User.Role.DOCTOR) {
        u.setStatus(User.Status.PENDING);
        message = "Signup successful! Your account is pending admin approval.";
        // 🚫 No token yet
    } else if (u.getRole() == User.Role.PATIENT) {
        u.setStatus(User.Status.ACTIVE);
        message = "Welcome! Your patient account is active and ready to use.";
        token = jwtService.generateToken(u); // ✅ only active users get token
    } else if (u.getRole() == User.Role.ADMIN) {
        u.setStatus(User.Status.PENDING); 
        message = "Admin account request received. Please wait for system approval.";
        // 🚫 No token
    } else {
        u.setStatus(User.Status.ACTIVE);
        message = "Account created successfully.";
        token = jwtService.generateToken(u); 
    }

    repo.save(u);
        return ResponseEntity.ok(new LoginResponse(token,"ROLE_" + u.getRole().name(), u.getStatus().name(),message));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        System.out.println("🔹 Login attempt: " + request.getUsername());
       try {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        System.out.println("✅ Authentication success for: " + auth.getName());
        System.out.println("✅ Authorities: " + auth.getAuthorities());
    } catch (Exception e) {
        System.out.println("❌ Authentication failed: " + e.getMessage());
        return ResponseEntity.status(401).body(
                new LoginResponse(null, null, null, "Invalid username or password")
        );
    }
        User u = repo.findByUsername(request.getUsername()).orElseThrow();
        // ✅ Check account status
    if (u.getStatus() != User.Status.ACTIVE) {
        System.out.println("⚠️ User status not active: " + u.getStatus());
        return ResponseEntity
                .status(403)
                .body(new LoginResponse(null, "ROLE_" + u.getRole(), u.getStatus().name(),"Account is " + u.getStatus() + ". Please wait for admin approval."));
    }
        String token = jwtService.generateToken(u);
        System.out.println("🎟️ Token generated for " + u.getUsername());
        return ResponseEntity.ok(new LoginResponse(token, "ROLE_" + u.getRole(), u.getStatus().name(),"Account is " + u.getStatus() + ". Logging in..."));
    }
}
