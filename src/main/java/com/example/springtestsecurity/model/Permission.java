package com.example.springtestsecurity.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "api-permission")
@Component
public class Permission {
    private List<PermissionValue> pathToPermission;
    private List<PermissionIgnore> pathToPermissionIgnore;

    public Permission(List<PermissionValue> pathToPermission, List<PermissionIgnore> pathToPermissionIgnore) {
        this.pathToPermission = pathToPermission;
        this.pathToPermissionIgnore = pathToPermissionIgnore;
    }
}
