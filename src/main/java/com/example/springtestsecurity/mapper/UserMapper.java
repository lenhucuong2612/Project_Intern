package com.example.springtestsecurity.mapper;

import com.example.springtestsecurity.entity.Role;
import com.example.springtestsecurity.entity.User;
import com.example.springtestsecurity.request.FindUserRequest;
import com.example.springtestsecurity.request.UserRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(User user);
    FindUserRequest findUserName(String username);
    User checkLogin(String username);
    int updateUser(Long id,UserRequest userRequest);
    User checkUserById(Long id);
    User checkExitsUserById(Long id, String username);
    int deleteUserByName(String username);
    @Select("select username, create_time from users where create_time<=#{create_time}")
    List<FindUserRequest> findListUserByCreateDay(LocalDate create_time);

    List<FindUserRequest> findUser(String username, String  create_time);
    Role findRoleUser(String username);
    Role findRoleByUsername(String username);
}
