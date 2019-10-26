package com.learnspringboot.study2.service;

import com.learnspringboot.study2.dto.NotificationDTO;
import com.learnspringboot.study2.dto.PageDTO;
import com.learnspringboot.study2.dto.QuestionDTO;
import com.learnspringboot.study2.mapper.CommentMapper;
import com.learnspringboot.study2.mapper.NotificationMapper;
import com.learnspringboot.study2.mapper.QuestionMapper;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.Comment;
import com.learnspringboot.study2.model.Notification;
import com.learnspringboot.study2.model.Question;
import com.learnspringboot.study2.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    public List<NotificationDTO> list(Integer id) {
        //查看自己的通知
        List<Notification> notifications = notificationMapper.list(id);
        List<NotificationDTO> notificationDTOS = new ArrayList<>();
        //装载每条通知
        for (Notification notification : notifications) {
            Question question = new Question();
            if (notification.getType() == 1){
                //如果直接回复文章
                question = questionMapper.getById(notification.getOuterid());
            }else if (notification.getType() == 2){
                //如果是回复文章里面的评论
                Comment comment = commentMapper.getById(notification.getOuterid());
                question = questionMapper.getById((int) comment.getParentId());
            }
            NotificationDTO notificationDTO = new NotificationDTO();
            User user = userMapper.findById(notification.getNotifier());
            notificationDTO.setNotifier(user);
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setOuterTitle(question.getTitle());
            notificationDTO.setQuestion(question);

            notificationDTOS.add(notificationDTO);

        }
        return notificationDTOS;
    }
    public void haveNotificationRead(int id,int questionId){
        //使该用户notification都变成已读
        //找到type=0的comment,则为直接评论文章的comment
        List<Comment> commentsQuestion = commentMapper.listByQuestionId(questionId,0);
        //找到type=1的comment，则为评论评论的评论
        ArrayList<Comment> commentsComment = new ArrayList<>();
        for (Comment comment1 : commentsQuestion){
            //获得该评论的底下所有评论
            List<Comment> comments = commentMapper.listByQuestionId((int) comment1.getId(),1);
            //添加评论到列表
            if (!comments.isEmpty()){
                commentsComment.addAll(comments);
            }
        }
        List<Comment> commentsAll = new ArrayList<>();

        commentsAll.addAll(commentsComment);
        commentsAll.addAll(commentsQuestion);
        //通过当前comment的id，设置回复notification变成已读
        for (Comment comment : commentsAll){
            notificationMapper.haveNotificationRead(id, (int) comment.getParentId());
        }


    }
}
