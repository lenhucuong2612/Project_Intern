package com.example.springtestsecurity.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class PermissionValue {
    private String path;
    private String method;
    private Map<String, Boolean> permissions;
}
