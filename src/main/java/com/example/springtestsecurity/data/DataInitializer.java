package com.example.springtestsecurity.data;

import com.example.springtestsecurity.entity.User;
import com.example.springtestsecurity.mapper.UserMapper;
import com.example.springtestsecurity.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Transactional
@RequiredArgsConstructor
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        createUserIfNotExits();
    }
//    private void createUserIfNotExits(){
//        for(int i=100;i<=120;i++){
//            String defaultEmail="user"+i+"@email.com";
//            User user=new User();
//            user.setUsername(defaultEmail);
//            user.setPassword(bCryptPasswordEncoder.encode("12345"));
//            userMapper.insertUser(user);
//            System.out.println("Default vet user "+i+" created successfully");
//        }
//    }

}
