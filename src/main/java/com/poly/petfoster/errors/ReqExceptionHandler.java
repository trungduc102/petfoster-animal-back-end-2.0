package com.poly.petfoster.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.poly.petfoster.response.ApiResponse;

@ControllerAdvice
public class ReqExceptionHandler extends ResponseEntityExceptionHandler {

        @Override
        @Nullable
        protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

            Map<String, String> errorMap = new HashMap<>();

            ex.getBindingResult().getFieldErrors().forEach(error -> {
                errorMap.put(error.getField(), error.getDefaultMessage());
            });

            ApiResponse apiResponceErrors = ApiResponse.builder()
                                            .message("Data invalid")
                                            .status(status.value())
                                            .errors(errorMap)
                                            .build();

            return new ResponseEntity<>(apiResponceErrors,  headers, status);
        }

    
}
