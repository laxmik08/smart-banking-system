package com.laxmi.smart_banking_system.controller;

import com.laxmi.smart_banking_system.entity.User;
import com.laxmi.smart_banking_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import com.laxmi.smart_banking_system.dto.LoginRequest;
import com.laxmi.smart_banking_system.dto.LoginResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5176")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User saveUser(@Valid @RequestBody User user) {

        System.out.println("========== REGISTER API CALLED ==========");
        System.out.println(user);

        return userService.saveUser(user);
    }
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    @PutMapping("/{id}")
    public User updateUser(
            @PathVariable Long id,
            @RequestBody User user
    ) {

        return userService.updateUser(id, user);
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
    @GetMapping("/profile")
    public User getProfile(@RequestParam String email) {

        return userService.getProfile(email);

    }
    @GetMapping("/account/{accountNumber}")
    public User getAccountDetails(@PathVariable String accountNumber) {

        return userService.getAccountDetails(accountNumber);

    }
}
