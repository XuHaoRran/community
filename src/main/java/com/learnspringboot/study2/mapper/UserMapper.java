package com.learnspringboot.study2.mapper;

import com.learnspringboot.study2.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Insert("insert into public.user(account_id, name, token, gmt_create, gmt_modified) VALUES(#{account_id},#{name},#{token},#{gmt_create},#{gmt_modified})")
    void insert(User user);
    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);
    @Select("select * from public.user where id =#{id}")
    User findById(@Param("id") Integer id);
    @Select("select * from user where account_id=#{account_id}")
    User findByAccountId(@Param("account_id")String account_id);
    @Update("update user set name = #{name}, token=#{token},gmt_modified = #{gmt_modified} where id = #{id}")
    void update(User user);
    @Select("select * from user   ")
    List<User> list();
}
