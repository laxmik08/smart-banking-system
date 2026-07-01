package com.laxmi.smart_banking_system.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.laxmi.smart_banking_system.entity.User;
import com.laxmi.smart_banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Save User
    public User saveUser(User user) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Password Hash
        user.setPassword(encoder.encode(user.getPassword()));

        // Auto Account Number
        String accountNumber = "SBI" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        user.setAccountNumber(accountNumber);

        // Default Balance
        user.setBalance(0.0);

        // Default Role
        user.setRole("CUSTOMER");

        return userRepository.save(user);
    }

    // Get All Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser != null) {

            existingUser.setFullName(updatedUser.getFullName());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setMobile(updatedUser.getMobile());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setAccountNumber(updatedUser.getAccountNumber());
            existingUser.setBalance(updatedUser.getBalance());
            existingUser.setAccountType(updatedUser.getAccountType());
            existingUser.setRole(updatedUser.getRole());

            return userRepository.save(existingUser);
        }

        return null;
    }
    public String deleteUser(Long id) {

        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return "User Deleted Successfully";
        }

        return "User Not Found";
    }


}