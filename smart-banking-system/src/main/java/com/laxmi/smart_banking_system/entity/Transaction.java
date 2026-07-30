package com.laxmi.smart_banking_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private Double amount;

    // DEPOSIT, WITHDRAW, TRANSFER
    private String transactionType;

    private LocalDateTime transactionDate;
}