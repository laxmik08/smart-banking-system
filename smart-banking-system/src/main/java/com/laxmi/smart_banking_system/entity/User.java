package com.laxmi.smart_banking_system.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String mobile;

    private String password;

    @Column(unique = true)
    private String accountNumber;

    private Double balance;

    private String accountType;

    private String role;
}