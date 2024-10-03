package com.example.springtestsecurity.service;

import com.example.springtestsecurity.entity.User;
import com.example.springtestsecurity.mapper.UserMapper;
import com.example.springtestsecurity.request.UserChangePassword;
import com.example.springtestsecurity.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    public ApiResponse sendOtpForUser(String email) {
        String subject = "Email Authentication";
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        String message = "Otp for you: " + otp;

        User userForgotPassword = userMapper.findUserName(email);
        if (userForgotPassword == null) {
            return apiResponse("003", "Account does not exist");
        }

        int checkSendOtp = userMapper.sendOtp(email, otp);
        if (checkSendOtp > 0) {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setFrom("Api User");
            mailMessage.setSubject(subject);
            mailMessage.setText(message);
            mailSender.send(mailMessage);
            return apiResponse("000", "Sending otp code successfully");
        }
        return apiResponse("001", "Sending otp code failed");
    }

    public ApiResponse changeUserPassword(UserChangePassword userChangePassword){
        int checkUserAndToken= userMapper.checkUserNameAndToken(userChangePassword);
        if(checkUserAndToken==0){
            return apiResponse("001", "Password change failed");
        }
        userChangePassword.setPassword(passwordEncoder.encode(userChangePassword.getPassword()));
        int checkChangePassword= userMapper.changePasswordUser(userChangePassword);
        if(checkChangePassword==0){
            return apiResponse("001", "Password change failed");
        }
        return apiResponse("000", "Change password successfully");
    }
    public ApiResponse apiResponse(String error_cd, String error_msg){
        return  new ApiResponse(error_cd,error_msg);
    }

}
