package com.example.springtestsecurity.request;

import lombok.Data;

@Data
public class ListUser {
    private Long id;
    private String username;
    private String create_time;
}
