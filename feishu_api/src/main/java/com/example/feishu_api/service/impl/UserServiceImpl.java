package com.example.feishu_api.service.impl;

import com.example.feishu_api.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.feishu_api.common.R;
import com.example.feishu_api.entity.User;
import com.example.feishu_api.mapper.UserMapper;
import com.example.feishu_api.service.UserService;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    private static final SecretKey HS512_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    @Override
    public R<String> login(String username, String password) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, username);
        User user = getOne(wrapper);

        if (user == null) {
            return R.fail("用户不存在");
        }

        if (!user.getPassword().equals(password)) {
            return R.fail("密码错误");
        }

        // 生成JWT
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(HS512_SECRET_KEY)
                .compact();

        return R.ok(token);
    }
}
