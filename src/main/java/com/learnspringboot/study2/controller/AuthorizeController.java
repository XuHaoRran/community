package com.learnspringboot.study2.controller;

import com.learnspringboot.study2.dto.AccessTokenDTO;
import com.learnspringboot.study2.dto.GithubUser;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.User;
import com.learnspringboot.study2.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name ="state") String state,
                           HttpServletRequest request) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClien_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        System.out.println(code);
//        githubProvider.getAccessToken(accessTokenDTO);

        //假装这里已经得到正确的accesstoken
        //假装这里返回了正确的名字，以及github的id
        //假装已经获取名字，以及github的id
        GithubUser user = new GithubUser("番茄薯仔",(long) 123,"认真学习java");
        if (user!=null){
            //登录成功，写cookie 和session
            User user1 = new User();
            user1.setToken(UUID.randomUUID().toString());
            user1.setName(user.getName());
            user1.setAccount_id(String.valueOf(user.getId()));
            //获取日期字符串
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String nowtime = dateFormat.format(date);
            System.out.println(nowtime);
            user1.setGmt_create(nowtime);
            user1.setGmt_modified(user1.getGmt_create());
            userMapper.insert(user1);
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
    }

}
