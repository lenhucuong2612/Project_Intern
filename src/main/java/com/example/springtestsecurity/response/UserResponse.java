package com.example.springtestsecurity.response;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String create_time;
}
