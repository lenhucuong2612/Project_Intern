package com.example.springtestsecurity.request;

import com.example.springtestsecurity.Annotation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestUpdate {
    private Long id;
    @NotEmpty
    private String username;

    @ValidPassword
    private String password;
}
