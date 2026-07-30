package com.laxmi.smart_banking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {

    private String message;
    private String token;
    private String accountNumber;
    private String fullName;

}