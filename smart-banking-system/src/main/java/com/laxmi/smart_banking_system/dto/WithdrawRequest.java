package com.laxmi.smart_banking_system.dto;

import lombok.Data;

@Data
public class WithdrawRequest {

    private String accountNumber;

    private Double amount;
}