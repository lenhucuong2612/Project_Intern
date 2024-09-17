package com.example.springtestsecurity.config;

import com.example.springtestsecurity.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
@RequiredArgsConstructor
public class PermissionLoggerService {
    private final Permission permission;
    @PostConstruct
    public void logPermissionData() {
        // Log dữ liệu cấu hình
        System.out.println("Permission data: " + permission);
    }
}
