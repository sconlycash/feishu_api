package com.example.feishu_api.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.feishu_api.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}