package com.example.feishu_api.common;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e) {
        return R.fail(e.getMessage());
    }
}