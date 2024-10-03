package com.example.springtestsecurity.exception;

import com.example.springtestsecurity.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

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
        return ResponseEntity.ok(apiResponse);
    }
    @ExceptionHandler(value = DateTimeParseException.class)
    public ResponseEntity<ApiResponse> handleDateTimeParseException(DateTimeParseException ex){
        return ResponseEntity.ok(new ApiResponse("003","Date is not in correct format"));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse> handleMethodNoSupportException(HttpRequestMethodNotSupportedException e){
        return ResponseEntity.ok(new ApiResponse("003","Method no support"));
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse> handleNullPointerException(NullPointerException ex){
        return ResponseEntity.ok(new ApiResponse("003","Null pointer exception"));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException e){
        return ResponseEntity.ok(new ApiResponse("003","Missing servlet request param"));
    }
}
