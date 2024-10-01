package com.example.springtestsecurity.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNameRequest {
    private Long id;
    @NotEmpty
    private String username;
}
