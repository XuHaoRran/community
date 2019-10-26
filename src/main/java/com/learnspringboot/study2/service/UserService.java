package com.learnspringboot.study2.service;

import com.learnspringboot.study2.dto.GithubUser;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbuser = userMapper.findByAccountId(user.getAccount_id());
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowtime = dateFormat.format(date);
        if (dbuser == null){
            //插入

            System.out.println(nowtime);
            user.setGmt_create(nowtime);
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }else{
            //更新
            dbuser.setGmt_modified(nowtime);
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            userMapper.update(dbuser);
        }
    }
    public User getByCreatorId(int id){
        User user = userMapper.findById(id);
        return user;
    }
}
