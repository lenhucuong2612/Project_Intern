package com.example.springtestsecurity;

import com.example.springtestsecurity.model.Permission;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class SpringTestSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringTestSecurityApplication.class, args);
    }

}
