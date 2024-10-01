package com.example.springtestsecurity.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageApiResponse {
    private Object object;
    private int record;
}
