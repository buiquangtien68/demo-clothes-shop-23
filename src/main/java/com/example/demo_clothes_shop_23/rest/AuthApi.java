package com.example.demo_clothes_shop_23.rest;

import com.example.demo_clothes_shop_23.entities.User;
import com.example.demo_clothes_shop_23.request.LoginRequest;
import com.example.demo_clothes_shop_23.request.RegisterRequest;
import com.example.demo_clothes_shop_23.request.UpdateInfoUserRequest;
import com.example.demo_clothes_shop_23.request.UpdatePasswordRequest;
import com.example.demo_clothes_shop_23.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthApi {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = authService.createUser(registerRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED); //201
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        authService.login(loginRequest);
        return ResponseEntity.ok("Logged in successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        authService.logout();
        return ResponseEntity.ok("Logged out successfully");
    }

    @PutMapping("/updateInfo")
    public ResponseEntity<?> updateInfo(@Valid @RequestBody UpdateInfoUserRequest updateInfoUserRequest){
        User user= authService.updateInfo(updateInfoUserRequest);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update-password/{id}")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest, @Valid @PathVariable Integer id) {
        User user = authService.updatePassword(updatePasswordRequest,id);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
