package com.example.springtestsecurity.service;
import com.example.springtestsecurity.mapper.UserMapper;
import com.example.springtestsecurity.model.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final Permission permission;
    private final UserMapper userMapper;

    public boolean hasPermission(String path, String method, String username) {
        String ignoreKey = path + "." + method;
        if (permission.getPathToPermissionIgnore().containsKey(ignoreKey)) {
            System.out.println("Path is ignored: " + ignoreKey);
            return true;
        }
        String permissionKey = path + "." + method;
        String permissionValue = permission.getPathToPermission().get(permissionKey);
        System.out.println("Path: " + permissionKey);
        System.out.println("Value: " + permissionValue);
        return userMapper.findRoleByUsername(username,permissionValue);
    }


}
