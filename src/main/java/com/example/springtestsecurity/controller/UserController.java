package com.example.springtestsecurity.controller;

import com.example.springtestsecurity.entity.User;
import com.example.springtestsecurity.request.FindUserRequest;
import com.example.springtestsecurity.request.UserRequest;
import com.example.springtestsecurity.response.ApiResponse;
import com.example.springtestsecurity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/create")
    public ApiResponse createUser(@RequestBody UserRequest userRequest){
        ApiResponse apiResponse=userService.createUser(userRequest);
        return apiResponse;
    }

    @PostMapping("/login")
    public ApiResponse loginUser(@RequestBody UserRequest userRequest){
        ApiResponse apiResponse=userService.loginUser(userRequest);
        return apiResponse;
    }
    @GetMapping("/findByName")
    public FindUserRequest findUserByName(@RequestParam(name="username") String username){
        FindUserRequest apiResponse=userService.findUserByName(username);
        return apiResponse;
    }
    @PutMapping("/update/{id}")
    public ApiResponse updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserRequest userRequestBody
    ){
        UserRequest userRequest=new UserRequest();
        userRequest.setUsername(userRequestBody.getUsername());
        userRequest.setPassword(userRequestBody.getPassword());
        ApiResponse apiResponse=userService.updateUser(id,userRequest);
        return apiResponse;

    }
    @DeleteMapping("/delete")
    public ApiResponse deleteUser(@RequestParam(name="username") String username){
        ApiResponse apiResponse=userService.deleteUser(username);
        return apiResponse;
    }
    @GetMapping("/findByCreateTime")
    public List<FindUserRequest> findByUserByCreateTime(@RequestParam(name="create_time") String time){
        List<FindUserRequest> listResult=userService.findUserByCreateTime(time);
        return listResult;
    }

    @GetMapping("/findUser")
    public List<FindUserRequest> findUserByNameAndCreateTime(
           @RequestBody FindUserRequest userRequest){
        List<FindUserRequest> listResult=userService.findUser(userRequest.getUsername(),userRequest.getCreate_time());
        return listResult;
    }
}
