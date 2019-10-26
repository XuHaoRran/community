package com.learnspringboot.study2.service;

import com.learnspringboot.study2.dto.CommentDTO;
import com.learnspringboot.study2.dto.NotificationDTO;
import com.learnspringboot.study2.mapper.CommentMapper;
import com.learnspringboot.study2.mapper.NotificationMapper;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.Comment;
import com.learnspringboot.study2.model.User;
import com.mysql.cj.util.TimeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Notification;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;
    public List<CommentDTO> listByQuestionId(int id,int type){



        List<Comment> comments = commentMapper.listByQuestionId(id,type);
        if (comments.size() == 0){
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Integer> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Integer> userIds = new ArrayList<>();
        userIds.addAll(commentators);


        //获取评论人并转换为Map
        List<User> users = userMapper.list();
        Map<Integer,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));



        //转换comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        //列表倒叙
        Collections.reverse(commentDTOS);
        return commentDTOS;
    }


}
