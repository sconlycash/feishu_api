package com.example.feishu_api.controller;
import com.example.feishu_api.common.R;
import com.example.feishu_api.entity.User;
import com.example.feishu_api.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public R<String> register(@RequestBody User user) {
        return userService.register(user);
    }

    @GetMapping("/info")
    public R<User> info(@RequestHeader("token") String token) {
        return userService.getCurrentUser(token);
    }
}