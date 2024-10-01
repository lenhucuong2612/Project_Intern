package com.example.springtestsecurity.service;

import com.example.springtestsecurity.entity.Role;
import com.example.springtestsecurity.entity.User;
import com.example.springtestsecurity.mapper.UserMapper;
import com.example.springtestsecurity.request.FindUserRequest;
import com.example.springtestsecurity.request.ListUser;
import com.example.springtestsecurity.request.UserNameRequest;
import com.example.springtestsecurity.request.UserRequest;
import com.example.springtestsecurity.response.ApiResponse;
import com.example.springtestsecurity.response.UserResponse;
import com.example.springtestsecurity.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisService redisService;

    public ApiResponse loginUser(UserRequest userRequest) {
        User user = userMapper.checkLogin(userRequest.getUsername());
        if (user == null || !bCryptPasswordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            return apiResponse("001", "Invalid username or password", null, null);
        }

        String token = jwtUtils.generateToken(userRequest.getUsername());
        redisService.saveToken(userRequest.getUsername(), token);
        return apiResponse("000", "Success", "Bearer", token);
    }

    public ApiResponse createUser(UserRequest request) {
        if (userMapper.findUserName(request.getUsername()) != null) {
            return apiResponse("001", "User already exits", null, null);
        }
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword().trim());
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(encodedPassword);
        Role role_id = userMapper.findRoleUser(request.getRole().toUpperCase());
        System.out.println("Role id" + role_id);
        if (role_id == null) {
            return apiResponse("001", "Role no empty", null, null);
        }
        newUser.setRole(role_id);
        int checkInsert = userMapper.insertUser(newUser);
        if (checkInsert > 0) {
            return apiResponse("000", "Register success", null, null);
        } else {
            return apiResponse("001", "No success", null, null);
        }
    }

    public User findUserByName(String username) {
        User userResponse = userMapper.findUserName(username);
        if (userResponse == null) {
            User resultNull = new User();
            resultNull.setUsername(null);
            resultNull.setCreate_time(null);
            return resultNull;
        }
        return userResponse;
    }

    public ApiResponse updateUser(UserRequest userRequest) {
        if (userMapper.checkExitsUserById(userRequest.getId(), userRequest.getUsername()) != null) {
            return apiResponse("001", "Email already exist", null, null);
        }
        UserRequest userUpdate = new UserRequest();
        userUpdate.setUsername(userRequest.getUsername());
        userUpdate.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword().trim()));
        int checkUpdate = userMapper.updateUser(userRequest.getId(), userUpdate);
        if (checkUpdate > 0) {
            return apiResponse("000", "Update success", null, null);
        } else {
            return apiResponse("001", "Update fail", null, null);
        }
    }

    public ApiResponse updateUserName(UserNameRequest userRequest) {
        if (userMapper.checkExitsUserById(userRequest.getId(), userRequest.getUsername()) != null) {
            return apiResponse("001", "Email already exist", null, null);
        }
        UserNameRequest userUpdate = new UserNameRequest();
        userUpdate.setUsername(userRequest.getUsername());
        int checkUpdate = userMapper.updateUserByName(userRequest.getId(), userUpdate);
        if (checkUpdate > 0) {
            return apiResponse("000", "Update success", null, null);
        } else {
            return apiResponse("001", "Update fail", null, null);
        }
    }

    public ApiResponse deleteUser(String username) {
        int deleteUser = userMapper.deleteUserByName(username);
        if (deleteUser > 0) {
            return apiResponse("000", "Delete success", null, null);
        } else {
            return apiResponse("001", "No delete success", null, null);
        }
    }

    public List<FindUserRequest> findUserByCreateTime(String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(time, timeFormatter);
        return userMapper.findListUserByCreateDay(localDate);
    }

    public List<UserResponse> findUser(FindUserRequest userRequest, int page, int size) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate_start = null;
        String formattedDate_end = LocalDate.now().toString();
        if (userRequest.getStart_time() != null && !userRequest.getStart_time().isEmpty()) {
            formattedDate_start = LocalDate.parse(userRequest.getStart_time(), timeFormatter).toString();
        }
        if (userRequest.getEnd_time() != null && !userRequest.getEnd_time().isEmpty()) {
            formattedDate_end = LocalDate.parse(userRequest.getEnd_time(), timeFormatter).toString();
        }
        int offset = (page - 1) * size;
        List<UserResponse> listUser = userMapper.findUser(userRequest.getUsername(), formattedDate_start, formattedDate_end, size, offset);
        System.out.println("Start date: " + formattedDate_start);
        return listUser;
    }
    public int countUser(FindUserRequest userRequest){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate_start = null;
        String formattedDate_end = LocalDate.now().toString();
        if (userRequest.getStart_time() != null && !userRequest.getStart_time().isEmpty()) {
            formattedDate_start = LocalDate.parse(userRequest.getStart_time(), timeFormatter).toString();
        }
        if (userRequest.getEnd_time() != null && !userRequest.getEnd_time().isEmpty()) {
            formattedDate_end = LocalDate.parse(userRequest.getEnd_time(), timeFormatter).toString();
        }
        return userMapper.countUsers(userRequest.getUsername(),formattedDate_start,formattedDate_end);
    }
    public List<ListUser> getAllUser(int page, int size) {
        int offset = (page - 1) * size;
        return userMapper.listUser(size, offset);
    }

    public ApiResponse logOut(String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        boolean deleteToken = redisService.deleteToken(jwtUtils.getUsernameFromToken(token));
        if (deleteToken) {
            return apiResponse("000", "Logout success", null, null);
        }
        return apiResponse("001", "Logout fail", null, null);
    }

    private ApiResponse apiResponse(String error_cd, String error_msg, String tokenType, String token) {
        return new ApiResponse(error_cd, error_msg, tokenType, token);
    }
}
