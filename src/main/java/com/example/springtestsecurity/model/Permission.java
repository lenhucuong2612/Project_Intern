package com.example.springtestsecurity.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

@ConfigurationProperties(prefix = "api-permission")
@Component
public class Permission {
    private Map<String, String> pathToPermission;
    private Map<String, String> pathToPermissionIgnore;

    public Map<String, String> getPathToPermission() {
        return pathToPermission;
    }

    public void setPathToPermission(Map<String, String> pathToPermission) {
        this.pathToPermission = pathToPermission;
    }

    public Map<String, String> getPathToPermissionIgnore() {
        return pathToPermissionIgnore;
    }

    public void setPathToPermissionIgnore(Map<String, String> pathToPermissionIgnore) {
        this.pathToPermissionIgnore = pathToPermissionIgnore;
    }
    @PostConstruct
    public void init() {
        System.out.println("Loaded pathToPermission: " + pathToPermission);
        for (Map.Entry<String, String> entry : pathToPermission.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }
    }
}
