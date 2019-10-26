package com.learnspringboot.study2.controller;

import com.learnspringboot.study2.dto.CommentCreateDTO;
import com.learnspringboot.study2.dto.CommentDTO;
import com.learnspringboot.study2.dto.QuestionDTO;
import com.learnspringboot.study2.mapper.NotificationMapper;
import com.learnspringboot.study2.model.User;
import com.learnspringboot.study2.service.CommentService;
import com.learnspringboot.study2.service.NotificationService;
import com.learnspringboot.study2.service.QuestionService;
import com.learnspringboot.study2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private CommentService commentService;
    @RequestMapping("/question/{id}")
    public String question(@PathVariable(name = "id")Integer id, Model model, HttpServletRequest request){
        QuestionDTO questionDTO = questionService.getById(id);
        //获取评论
        List<CommentDTO> comments = commentService.listByQuestionId(id,0);
        List<QuestionDTO> relateQuestions = questionService.selectRealated(questionDTO);
        User user = userService.getByCreatorId(questionDTO.getCreator());
        questionDTO.setUser(user);
        questionService.inView(id);
        //还没有解决已经看完评论滴问题
        User meUser = (User) request.getSession().getAttribute("user");
        notificationService.haveNotificationRead(meUser.getId(),questionDTO.getId());
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        model.addAttribute("relatedQuestions",relateQuestions);
        return "question.html";
    }
}
