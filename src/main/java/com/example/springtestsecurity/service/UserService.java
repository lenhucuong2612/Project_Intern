package com.example.springtestsecurity.service;

import com.example.springtestsecurity.entity.Role;
import com.example.springtestsecurity.entity.User;
import com.example.springtestsecurity.mapper.UserMapper;
import com.example.springtestsecurity.request.FindUserRequest;
import com.example.springtestsecurity.request.UserRequest;
import com.example.springtestsecurity.response.ApiResponse;
import com.example.springtestsecurity.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public ApiResponse loginUser(UserRequest userRequest){
        ApiResponse apiResponse=new ApiResponse();
        User user = userMapper.checkLogin(userRequest.getUsername());
        if (user == null || !bCryptPasswordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            apiResponse.setError_cd("001");
            apiResponse.setError_msg("Invalid username or password");
            return apiResponse;
        }

        String token = jwtUtils.generateToken(userRequest.getUsername());
        apiResponse.setError_cd("000");
        apiResponse.setError_msg("Success");
        apiResponse.setTokenType("Bearer");
        apiResponse.setToken(token);
        redisService.saveToken(token);
        return apiResponse;
    }
    public ApiResponse createUser(UserRequest request){
        ApiResponse apiResponse=new ApiResponse();
         if(userMapper.findUserName(request.getUsername())!=null){
            apiResponse.setError_cd("002");
            apiResponse.setError_msg("User already exits");
            return apiResponse;
        }
        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword().trim());
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(encodedPassword);
        Role role_id= userMapper.findRoleUser(request.getRole());
        newUser.setRole(role_id);
        int checkInsert=userMapper.insertUser(newUser);
        if(checkInsert>0){
            apiResponse.setError_cd("000");
            apiResponse.setError_msg("Register success");
        }else{
            apiResponse.setError_cd("001");
            apiResponse.setError_msg("No success");
        }
        return apiResponse;
    }
    public FindUserRequest findUserByName(String username){
        FindUserRequest userResponse=userMapper.findUserName(username);
        if(userResponse==null){
            FindUserRequest resultNull=new FindUserRequest();
            resultNull.setUsername(null);
            resultNull.setCreate_time(null);
            return resultNull;
        }
        return userResponse;
    }
    public ApiResponse updateUser(Long id,UserRequest userRequest){
        ApiResponse apiResponse=new ApiResponse();
//        if(userMapper.checkUserById(id)==null){
//            apiResponse.setError_cd("001");
//            apiResponse.setError_msg("No exist");
//            return apiResponse;
//        }
        if(userMapper.checkExitsUserById(id, userRequest.getUsername())!=null){
            apiResponse.setError_cd("001");
            apiResponse.setError_msg("Email already exist");
            return apiResponse;
        }
        UserRequest userUpdate=new UserRequest();
        userUpdate.setUsername(userRequest.getUsername());
        userUpdate.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword().trim()));
        int checkUpdate= userMapper.updateUser(id,userUpdate);
        if(checkUpdate>0){
            apiResponse.setError_cd("000");
            apiResponse.setError_msg("Update success");
        }else{
            apiResponse.setError_cd("001");
            apiResponse.setError_msg("No success");
        }
        return apiResponse;
    }
    public ApiResponse deleteUser(String username){
        ApiResponse apiResponse=new ApiResponse();
//        if(userMapper.findUserName(username)==null){
//            apiResponse.setError_cd("001");
//            apiResponse.setError_msg("Username not exist");
//            return apiResponse;
//        }
        int deleteUser= userMapper.deleteUserByName(username);
        if(deleteUser>0){
            apiResponse.setError_cd("000");
            apiResponse.setError_msg("Delete success");
        }else{
            apiResponse.setError_cd("001");
            apiResponse.setError_msg("No delete success");
        }
        return apiResponse;
    }
    public List<FindUserRequest> findUserByCreateTime(String time){
        DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(time,timeFormatter);
        List<FindUserRequest> result=userMapper.findListUserByCreateDay(localDate);
        return result;
    }

    public List<FindUserRequest> findUser(String name, String time){
        String formattedDate = null;
        if (time != null && !time.isEmpty()) {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            formattedDate = LocalDate.parse(time, timeFormatter).toString(); // yyyy-MM-dd
        }

        List<FindUserRequest> listUser = userMapper.findUser(name, formattedDate);
        return listUser;
    }
}
