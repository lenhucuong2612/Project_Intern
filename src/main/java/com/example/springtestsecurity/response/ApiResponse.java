package com.example.springtestsecurity.response;

import lombok.Data;

@Data
public class ApiResponse {
    private String error_cd;
    private String error_msg;
    private String tokenType;
    private String token;
}
