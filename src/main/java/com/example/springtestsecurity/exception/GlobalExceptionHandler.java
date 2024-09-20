package com.example.springtestsecurity.exception;

import com.example.springtestsecurity.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleValidationExceptionsNotEmpty(MethodArgumentNotValidException ex) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError_cd("003");

        StringBuilder errorMsg = new StringBuilder();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();

            if (defaultMessage.contains("Password must be at least 5 characters long")) {
                errorMsg.append(String.format("Password error: %s; ", defaultMessage));
            } else if (defaultMessage.contains("Username must not be null")) {
                errorMsg.append(String.format("Username error: %s; ", defaultMessage));
            } else {
                errorMsg.append(String.format("%s: %s; ", fieldName, defaultMessage));
            }
        });

        apiResponse.setError_msg(errorMsg.toString());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
