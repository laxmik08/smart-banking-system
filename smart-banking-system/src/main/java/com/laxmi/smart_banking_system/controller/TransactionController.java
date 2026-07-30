package com.laxmi.smart_banking_system.controller;
import com.laxmi.smart_banking_system.entity.Transaction;
import java.util.List;

import com.laxmi.smart_banking_system.dto.DepositRequest;
import com.laxmi.smart_banking_system.dto.WithdrawRequest;
import com.laxmi.smart_banking_system.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.laxmi.smart_banking_system.dto.TransferRequest;
@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public String deposit(@RequestBody DepositRequest request) {
        System.out.println("Deposit API Called");

        return transactionService.deposit(
                request.getAccountNumber(),
                request.getAmount()
        );
    }

    @PostMapping("/withdraw")
    public String withdraw(@RequestBody WithdrawRequest request) {

        return transactionService.withdraw(
                request.getAccountNumber(),
                request.getAmount()
        );
    }
    @PostMapping("/transfer")
    public String transfer(@RequestBody TransferRequest request) {

        return transactionService.transfer(
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        );
    }
    @GetMapping("/history/{accountNumber}")
    public List<Transaction> getHistory(@PathVariable String accountNumber) {

        return transactionService.getTransactionHistory(accountNumber);
    }
    @GetMapping("/balance/{accountNumber}")
    public Double checkBalance(@PathVariable String accountNumber) {

        return transactionService.checkBalance(accountNumber);
    }
}