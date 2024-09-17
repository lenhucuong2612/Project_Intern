package com.example.springtestsecurity.interceptor;

import com.example.springtestsecurity.service.RedisService;
import com.example.springtestsecurity.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtUtils jwtUtils;
    private final RedisService redisService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return unauthorizedResponse(response, "001", "Missing or invalid token");
        }

        token = token.substring(7);

        if (!redisService.isTokenExists(token)) {
            return unauthorizedResponse(response, "001", "Missing or invalid token in redis");
        }

        if (!jwtUtils.validateToken(token)) {
            return unauthorizedResponse(response, "001", "Invalid token");
        }

        // Check quyen user
        // Lay danh sach quyen & get column can query
        // Truy van database => Quyen la true or false
        request.getRequestURI();


        return true;
    }

    private boolean unauthorizedResponse(HttpServletResponse response, String errorCode, String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write(String.format("{\"error_cd\": \"%s\", \"error_msg\": \"%s\"}", errorCode, errorMessage));
        return false;
    }
}
