package com.example.springtestsecurity.mapper;

import com.example.springtestsecurity.entity.Role;
import com.example.springtestsecurity.entity.User;
import com.example.springtestsecurity.request.*;
import com.example.springtestsecurity.response.UserResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UserMapper {
    int insertUser(User user);
    User findUserName(String username);
    User checkLogin(String username);
    int updateUser(@Param("userRequestUpdate") UserRequestUpdate userRequest);
    int updateUserByName(Long id,UserNameRequest userNameRequest);
    int changePasswordUser(@Param("userChangePassword") UserChangePassword userChangePassword);
    User checkExitsUserById(Long id, String username);
    int deleteUserByName(String username);
    @Select("select username, create_time from users where create_time<=#{create_time}")
    List<FindUserRequest> findListUserByCreateDay(LocalDate create_time);
    List<UserResponse> findUser(String username,
                                @Param("start_time") String start_time, @Param("end_time") String end_time,
                                @Param("limit") int limit, @Param("offset") int offset);
    int countUsers(String username,
                   @Param("start_time") String start_time, @Param("end_time") String end_time);
    Role findRoleUser(String username);
    boolean findRoleByUsername(@Param("username") String username,@Param("role") String role);
    List<ListUser> listUser(@Param("limit") int limit, @Param("offset") int offset);

    int sendOtp(@Param("username") String username,@Param("otp") int otp);

    int checkUserNameAndToken(@Param("userChangePassword") UserChangePassword userChangePassword);
}
