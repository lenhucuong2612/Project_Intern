package com.example.springtestsecurity.request;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    @Size(min=5, message="Password min 5 character")
    private String password;
    private String role;
}
