package com.laxmi.smart_banking_system.service;

import com.laxmi.smart_banking_system.dto.LoginRequest;
import com.laxmi.smart_banking_system.dto.LoginResponse;
import com.laxmi.smart_banking_system.entity.User;
import com.laxmi.smart_banking_system.exception.UserNotFoundException;
import com.laxmi.smart_banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    // Register User
    public User saveUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        String accountNumber = "SBI" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        user.setAccountNumber(accountNumber);

        user.setBalance(0.0);

        user.setRole("CUSTOMER");

        return userRepository.save(user);
    }


    // Get All Users
    public List<User> getAllUsers() {

        return userRepository.findAll();

    }


    // Get User By Id
    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(
                        () -> new UserNotFoundException("User Not Found")
                );

    }


    // Update User Profile
    // Update User Profile
    public User updateUser(Long id, User updatedUser) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User Not Found")
                );

        // Sirf profile ki details update hongi
        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setMobile(updatedUser.getMobile());

        // Password, Account Number, Balance,
        // Account Type aur Role same rahenge

        return userRepository.save(existingUser);
    }



    // Delete User
    public String deleteUser(Long id) {

        if(userRepository.existsById(id)) {

            userRepository.deleteById(id);

            return "User Deleted Successfully";

        }

        return "User Not Found";

    }



    // Login
    public LoginResponse login(LoginRequest request) {


        Optional<User> user =
                userRepository.findByEmail(request.getEmail());


        if(user.isEmpty()) {

            return new LoginResponse(
                    "User Not Found",
                    null,
                    null,
                    null
            );

        }


        if(!passwordEncoder.matches(
                request.getPassword(),
                user.get().getPassword()
        )) {


            return new LoginResponse(
                    "Invalid Password",
                    null,
                    null,
                    null
            );

        }



        String token =
                jwtService.generateToken(
                        user.get().getEmail()
                );


        return new LoginResponse(
                "Login Successful",
                token,
                user.get().getAccountNumber(),
                user.get().getFullName()
        );


    }




    // Get Profile
    public User getProfile(String email) {

        return userRepository.findByEmail(email)
                .orElse(null);

    }



    // Get Account Details
    public User getAccountDetails(String accountNumber) {

        return userRepository.findByAccountNumber(accountNumber)
                .orElse(null);

    }

}