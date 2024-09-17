package com.example.springtestsecurity.request;

import lombok.Data;

@Data
public class FindUserRequest {
    private String username;
    private String create_time;
}
