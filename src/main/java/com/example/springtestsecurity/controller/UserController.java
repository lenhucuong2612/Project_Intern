package com.example.springtestsecurity.controller;

import com.example.springtestsecurity.entity.User;
import com.example.springtestsecurity.mapper.UserMapper;
import com.example.springtestsecurity.request.FindUserRequest;
import com.example.springtestsecurity.request.ListUser;
import com.example.springtestsecurity.request.UserNameRequest;
import com.example.springtestsecurity.request.UserRequest;
import com.example.springtestsecurity.response.ApiResponse;
import com.example.springtestsecurity.response.PageApiResponse;
import com.example.springtestsecurity.response.UserResponse;
import com.example.springtestsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    @PostMapping("/create")
    public ApiResponse createUser(@Valid @RequestBody UserRequest userRequest){
        ApiResponse apiResponse=userService.createUser(userRequest);
        return apiResponse;
    }

    @PostMapping("/login")
    public ApiResponse loginUser(@Valid @RequestBody UserRequest userRequest){
        ApiResponse apiResponse=userService.loginUser(userRequest);
        return apiResponse;
    }
    @GetMapping("/findByName")
    public User findUserByName(@RequestParam(name="username") String username){
        User apiResponse=userService.findUserByName(username);
        return apiResponse;
    }
    @PutMapping("/update/username")
    public ApiResponse updateUser(
            @Valid @RequestBody UserNameRequest userRequestBody
    ){
        UserNameRequest userRequest=new UserNameRequest();
        userRequest.setId(userRequestBody.getId());
        userRequest.setUsername(userRequestBody.getUsername());
        ApiResponse apiResponse=userService.updateUserName(userRequest);
        return apiResponse;

    }
    @PutMapping("/update")
    public ApiResponse updateUserName(
            @Valid @RequestBody UserRequest userRequestBody
    ){
        UserRequest userRequest=new UserRequest();
        userRequest.setId(userRequestBody.getId());
        userRequest.setUsername(userRequestBody.getUsername());
        userRequest.setPassword(userRequestBody.getPassword());
        ApiResponse apiResponse=userService.updateUser(userRequest);
        return apiResponse;

    }
    @DeleteMapping("/delete")
    public ApiResponse deleteUser(@RequestParam(name="username") String username){
        ApiResponse apiResponse=userService.deleteUser(username);
        return apiResponse;
    }

    @PostMapping("/findUser")
    public PageApiResponse findUserByNameAndCreateTime(
           @RequestBody FindUserRequest userRequest,
           @RequestParam(value="page",defaultValue = "1") int page,
           @RequestParam(value="size",defaultValue = "10") int size){
        List<UserResponse> listResult=userService.findUser(userRequest, page, size);
        return new PageApiResponse(listResult, userService.countUser(userRequest)>0?userService.countUser(userRequest):0);
    }
    @GetMapping("/listUser")
    public PageApiResponse getAllUser(@RequestParam(value="page",defaultValue = "1") int page,@RequestParam(value="size",defaultValue = "10") int size){
         userService.getAllUser(page,size);
        return new PageApiResponse(userService.getAllUser(page,size),20);
    }
    @GetMapping("/logout")
    public ApiResponse logout(@RequestHeader("Authorization") String token){
        return userService.logOut(token);
    }
}