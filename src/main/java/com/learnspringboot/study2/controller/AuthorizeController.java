package com.learnspringboot.study2.controller;

import com.learnspringboot.study2.dto.AccessTokenDTO;
import com.learnspringboot.study2.dto.GithubUser;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.User;
import com.learnspringboot.study2.provider.GithubProvider;
import com.learnspringboot.study2.service.UserService;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.util.resources.cldr.CalendarData;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    @Autowired
    private UserService userService;
    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name ="state") String state,
                           HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        Calendar calendar = Calendar.getInstance();
        long longg = calendar.getTimeInMillis();
        GithubUser user = new GithubUser("番茄薯仔",(long) longg,"认真学习java");
        if (user!=null){
            //登录成功，写cookie 和session
            User user1 = new User();
            String token = UUID.randomUUID().toString();
            user1.setToken(token);
            user1.setName(user.getName());
            user1.setAccount_id(String.valueOf(user.getId()));
            userService.createOrUpdate(user1);
            response.addCookie(new Cookie("token",token));

            return "redirect:/";
        }else{
            //登录失败，重新登录
            return "redirect:/";
        }
    }
    @RequestMapping("/loggout")
    public String loggout(HttpServletRequest request,
                          HttpServletResponse response){
        //删除session
        request.getSession().removeAttribute("user");
        //删除cookie
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

}
