package com.example.feishu_api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.feishu_api.entity.User;
import com.example.feishu_api.common.R;
public interface UserService extends IService<User>{
    R<String> login(String username, String password);
}
