package com.learnspringboot.study2.controller;

import ch.qos.logback.core.db.dialect.MySQLDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

@Controller
public class HelloController {
    @RequestMapping("/")
    public String index(){
        return "/index.html";
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
