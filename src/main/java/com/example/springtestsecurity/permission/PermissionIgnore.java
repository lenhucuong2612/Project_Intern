package com.example.springtestsecurity.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermissionIgnore {
    private String path;
    private String method;
}
