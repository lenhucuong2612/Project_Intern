package com.example.springtestsecurity.request;

import lombok.Data;

@Data
public class UserChangePassword {
    private String username;
    private int otp;
    private String password;
}
