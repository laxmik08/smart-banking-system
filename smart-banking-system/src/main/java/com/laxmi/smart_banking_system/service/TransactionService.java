package com.laxmi.smart_banking_system.service;

import com.laxmi.smart_banking_system.entity.Transaction;
import com.laxmi.smart_banking_system.entity.User;
import com.laxmi.smart_banking_system.repository.TransactionRepository;
import com.laxmi.smart_banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Deposit
    public String deposit(String accountNumber, Double amount) {

        User user = userRepository.findByAccountNumber(accountNumber)
                .orElse(null);

        if (user == null) {
            return "Account Not Found";
        }

        user.setBalance(user.getBalance() + amount);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType("DEPOSIT");
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return "Amount Deposited Successfully";
    }
    
    

    // Withdraw
    public String withdraw(String accountNumber, Double amount) {

        User user = userRepository.findByAccountNumber(accountNumber)
                .orElse(null);

        if (user == null) {
            return "Account Not Found";
        }

        if (user.getBalance() < amount) {
            return "Insufficient Balance";
        }

        user.setBalance(user.getBalance() - amount);
        userRepository.save(user);

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setTransactionType("WITHDRAW");
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return "Amount Withdrawn Successfully";
    }
    public String transfer(String fromAccount, String toAccount, Double amount) {
        System.out.println("From Account = " + fromAccount);
        System.out.println("To Account = " + toAccount);

        User sender = userRepository.findByAccountNumber(fromAccount).orElse(null);
        User receiver = userRepository.findByAccountNumber(toAccount).orElse(null);

        if (sender == null) {
            return "Sender Account Not Found";
        }

        if (receiver == null) {
            return "Receiver Account Not Found";
        }

        if (sender.getBalance() < amount) {
            return "Insufficient Balance";
        }

        // Sender balance deduct
        sender.setBalance(sender.getBalance() - amount);

        // Receiver balance add
        receiver.setBalance(receiver.getBalance() + amount);

        userRepository.save(sender);
        userRepository.save(receiver);

        // Sender Transaction
        Transaction debit = new Transaction();
        debit.setAccountNumber(fromAccount);
        debit.setAmount(amount);
        debit.setTransactionType("TRANSFER_DEBIT");
        debit.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(debit);

        // Receiver Transaction
        Transaction credit = new Transaction();
        credit.setAccountNumber(toAccount);
        credit.setAmount(amount);
        credit.setTransactionType("TRANSFER_CREDIT");
        credit.setTransactionDate(LocalDateTime.now());
        transactionRepository.save(credit);

        return "Money Transferred Successfully";
    }
    public java.util.List<Transaction> getTransactionHistory(String accountNumber) {

        return transactionRepository.findByAccountNumber(accountNumber);

    }
    public Double checkBalance(String accountNumber) {

        User user = userRepository.findByAccountNumber(accountNumber)
                .orElse(null);

        if (user == null) {
            return null;
        }

        return user.getBalance();
    }

}