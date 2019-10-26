package com.learnspringboot.study2.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.learnspringboot.study2.Enums.NotificationStatusEnum;
import com.learnspringboot.study2.Enums.NotificationTypeEnum;
import com.learnspringboot.study2.dto.CommentCreateDTO;
import com.learnspringboot.study2.dto.CommentDTO;
import com.learnspringboot.study2.mapper.CommentMapper;
import com.learnspringboot.study2.mapper.NotificationMapper;
import com.learnspringboot.study2.mapper.QuestionMapper;
import com.learnspringboot.study2.model.Comment;
import com.learnspringboot.study2.model.Notification;
import com.learnspringboot.study2.model.Question;
import com.learnspringboot.study2.model.User;
import com.learnspringboot.study2.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private NotificationMapper notificationMapper;
    @ResponseBody
    @RequestMapping(value = "/api/comment",method = RequestMethod.POST)
    @Transactional
    //RequestDTO自动生成json数据
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO, HttpServletRequest request){



        JSONObject res = new JSONObject();

        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());

        if (commentCreateDTO == null || commentCreateDTO.getContent() == null ||commentCreateDTO.getContent() == ""){
            res.put("code",2005);
            res.put("msg", "评论内容不可以为空");
            return res;
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = dateFormat.format(new Date());
        comment.setGmtModified(datetime);
        comment.setGmtCreate(datetime);
        User user = (User) request.getSession().getAttribute("user");
        //未登录
        if (user == null) {
            res.put("code", 2003);
            res.put("msg", "未登录，请立即登录");
            return res;
        } else{
            comment.setCommentator(user.getId());

            commentMapper.insert(comment);
            commentMapper.inCommentCount((int) comment.getParentId());


            //通知的评论
            Notification notification = new Notification();

            //如果评论的类型为0,那么就需要在question上添加评论的数量
            if (comment.getType()==0) {

                Question question = questionMapper.getById((int) comment.getParentId());
                questionMapper.inQuesCommentCount(question);
                res.put("commentcount",question.getCommentCount()+1);
                //这里设置通知为回复了问题
                notification.setType(NotificationTypeEnum.REPLY_QUESTION.getType());
                //标题的id是
                notification.setOuterid(question.getId());
                //应该需要提示的人是该评论回复的人,即直接是问题的作者
                notification.setReceiver(question.getCreator());
            }else {
                //如果该评论为2级评论
                //先取出question的id
                Comment comment0 = commentMapper.getById(comment.getParentId());
                notification.setType(NotificationTypeEnum.REPLY_COMMENT.getType());
                notification.setOuterid((int) comment0.getId());
                notification.setReceiver(comment0.getCommentator());
            }

            notification.setGmt_create(datetime);
            notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
            notification.setNotifier(comment.getCommentator());
            notificationMapper.insert(notification);
            res.put("code",200);
            res.put("msg","发送成功");
            return res;
        }



    }
    @ResponseBody
    @RequestMapping(value = "/api/comments/{id}",method = RequestMethod.GET)
    public Object comments(@PathVariable(name = "id")int id){
        //获取type=1的评论，表示子评论列表
        List<CommentDTO> commentDTOS = commentService.listByQuestionId(id, 1);

        String str = JSON.toJSONString(commentDTOS);
        System.out.println(str);
        return str;
    }


}
