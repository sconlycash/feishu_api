package com.example.feishu_api.controller;
import com.example.feishu_api.common.R;
import com.example.feishu_api.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public R<String> login(@RequestParam String username,
                           @RequestParam String password) {
        return userService.login(username, password);
    }
}