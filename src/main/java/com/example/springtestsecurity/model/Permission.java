package com.example.springtestsecurity.model;

import com.example.springtestsecurity.permission.PermissionIgnore;
import com.example.springtestsecurity.permission.PermissionValue;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
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
}
