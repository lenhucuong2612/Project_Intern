package com.example.springtestsecurity.service;

import com.example.springtestsecurity.entity.Role;
import com.example.springtestsecurity.mapper.UserMapper;
import com.example.springtestsecurity.model.Permission;
import com.example.springtestsecurity.permission.PermissionIgnore;
import com.example.springtestsecurity.permission.PermissionValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final Permission permission;
    private final UserMapper userMapper;

    public boolean hasPermission(String path, String method, String username) {
        // Tạo khóa bỏ qua (ignoreKey)
        String ignoreKey = path + "." + method;

        // Kiểm tra nếu đường dẫn nằm trong danh sách bỏ qua
        if (permission.getPathToPermissionIgnore().containsKey(ignoreKey)) {
            System.out.println("Path is ignored: " + ignoreKey);
            return true; // Được phép nếu là đường dẫn bị bỏ qua
        }

        // Lấy vai trò của người dùng từ cơ sở dữ liệu
        Role role = userMapper.findRoleByUsername(username);
        System.out.println(role);
        if (role == null) {
            System.out.println("Role is null for username: " + username);
            return false;
        }

        // Tạo khóa permission từ path và method
        String permissionKey = path + "." + method;
        String permissionValue = permission.getPathToPermission().get(permissionKey);

        // Log để kiểm tra permissionKey và permissionValue
        System.out.println("Permission Key: " + permissionKey);
        System.out.println("Permission Value: " + permissionValue);

        // Nếu permissionValue tồn tại, kiểm tra dựa trên quyền của user
        if (permissionValue != null) {
            System.out.println("Checking permissions for: " + permissionValue);

            // Kiểm tra dựa trên quyền role và permission
            Map<String, Boolean> permissions = Map.of(
                    "can_create", permissionValue.equals("can_create"),
                    "can_update", permissionValue.equals("can_update"),
                    "can_delete", permissionValue.equals("can_delete")
            );

            // Gọi hàm checkUserRolePermission để kiểm tra vai trò của user
            boolean hasPermission = checkUserRolePermission(role, permissions);
            return hasPermission;
        }

        return false;
    }

    private boolean checkUserRolePermission(Role role, Map<String, Boolean> permissions) {
        boolean canCreate = permissions.getOrDefault("can_create", false);
        boolean canUpdate = permissions.getOrDefault("can_update", false);
        boolean canDelete = permissions.getOrDefault("can_delete", false);

        return (canCreate && role.isCan_create()) ||
                (canUpdate && role.isCan_update()) ||
                (canDelete && role.isCan_delete());
    }


}
