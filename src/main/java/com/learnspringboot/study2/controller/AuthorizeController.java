package com.learnspringboot.study2.controller;

import com.learnspringboot.study2.dto.AccessTokenDTO;
import com.learnspringboot.study2.dto.GithubUser;
import com.learnspringboot.study2.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

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

    @RequestMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name ="state") String state) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClien_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        System.out.println(code);
        githubProvider.getAccessToken(accessTokenDTO);
        //假装这里已经得到正确的accesstoken
        //假装这里返回了正确的名字，以及github的id
        //假装已经获取名字，以及github的id
        GithubUser githubUser = new GithubUser("番茄薯仔",(long) 123,"认真学习java");
        return "index.html";
    }

}
