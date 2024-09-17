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

    public boolean hasPermission(String path,String method,String username){
        String ignoreKey = path + ":" + method;
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

        // Tạo khóa cho quyền
        String permissionKey = path + ":" + method;
        String permissionValue = permission.getPathToPermission().get(permissionKey);

        if (permissionValue != null) {
            // Bạn có thể thực hiện kiểm tra quyền ở đây nếu cần
            // Ví dụ:
            // return checkUserRolePermission(role, permissionValue);
            System.out.println("Permission value: " + permissionValue);
            return true; // Ví dụ: tất cả các yêu cầu có cấu hình sẽ được phép
        }
        return false;
    }
    private boolean checkUserRolePermission(Role role, Map<String, Boolean> permissions){
        boolean canCreate=permissions.getOrDefault("create",false);
        boolean canUpdate=permissions.getOrDefault("update",false);
        boolean canDelete=permissions.getOrDefault("delete",false);
        return  (canCreate && role.isCreate()) ||
                (canUpdate && role.isUpdate()) ||
                (canDelete && role.isDelete());
    }
    private Role getUserRoleFromUsername(String username){
        return userMapper.findRoleByUsername(username);
    }


}
