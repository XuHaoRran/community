package com.learnspringboot.study2.controller;

import ch.qos.logback.core.db.dialect.MySQLDialect;
import com.learnspringboot.study2.dto.PageDTO;
import com.learnspringboot.study2.dto.QuestionDTO;
import com.learnspringboot.study2.mapper.QuestionMapper;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.Question;
import com.learnspringboot.study2.model.User;
import com.learnspringboot.study2.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Controller
@Slf4j
public class HelloController {
    @Autowired
    private QuestionService questionService;

    @RequestMapping("/")

    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size){
        log.info("成功显示首页的了啊");
        PageDTO pageDTO = questionService.list(page,size);
        model.addAttribute("pageDTO",pageDTO);
        return "/index.html";
    }
    @RequestMapping("/search")
    public String search(){
        return "search.html";
    }
    @PostMapping("/search")
    public String dosearch(Model model, @RequestParam(name = "search") String search){
        if (search.isEmpty()){
            return "/index.html";
        }
        List<QuestionDTO> questionDTOList = questionService.listBySearch(search);
        model.addAttribute("questions",questionDTOList);
        return "/search.html";
    }
    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        String s = "hello world";
        return "/hello.html";
    }
    //查村一些数据，在页面显示
    @RequestMapping("/success")
    public String success(Map<String,Object> map){
        map.put("hello","你好");
        map.put("name","名字");
        map.put("users", Arrays.asList("张三","李四","王五"));
        return "sucess.html";
    }
    @Bean
    public ViewResolver myViewResolver(){
        return new MyViewResolver();
    }
    public static class MyViewResolver implements ViewResolver{

        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }

}
