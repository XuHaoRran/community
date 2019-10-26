package com.learnspringboot.study2.controller;

import com.learnspringboot.study2.dto.QuestionDTO;
import com.learnspringboot.study2.mapper.QuestionMapper;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.Question;
import com.learnspringboot.study2.model.User;
import com.learnspringboot.study2.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @RequestMapping("/publish")
    public String publish(){
        return "publish.html";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String tile,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam(value = "id",required = false) Integer id,
                            HttpServletRequest request,
                            Model model){
        //设置model直接传输数据到publish中显示iii
        model.addAttribute("title",tile);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(tile == null||tile==""){
            model.addAttribute("error","标题不能为空");
            return "publish.html";
        }
        if(description == null||description==""){
            model.addAttribute("error","描述不可以为空");
            return "publish.html";
        }
        if(tag == null||tag==""){
            model.addAttribute("error","不能没有标签");
            return "publish.html";
        }

        //判断cookie的token是否与数据库的token相等
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            model.addAttribute("error","用户未登录");
        }
        Question question = new Question();
        question.setTitle(tile);
        question.setDescription(description);
        question.setTags(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/profile/questions";
    }
    @Transactional
    @RequestMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Integer id, Model model){
        Question question = questionMapper.getById(id);

        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTags());
        model.addAttribute("id",question.getId());
        return "publish.html";
    }

}
