package com.laxmi.smart_banking_system.dto;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

}