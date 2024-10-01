package com.example.springtestsecurity.request;

import lombok.Data;

@Data
public class FindUserRequest {
    private Long id;
    private String username;
    private String start_time;
    private String end_time;
}
