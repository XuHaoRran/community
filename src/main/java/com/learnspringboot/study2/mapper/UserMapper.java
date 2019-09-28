package com.learnspringboot.study2.mapper;

import com.learnspringboot.study2.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("insert into public.user(account_id, name, token, gmt_create, gmt_modified) VALUES(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insert(User user);
}
