package com.example.springtestsecurity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseToken {
    private String error_cd;
    private String error_msg;
    private String tokenType;
    private String token;
}
