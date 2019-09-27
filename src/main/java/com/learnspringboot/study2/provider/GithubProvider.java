package com.learnspringboot.study2.provider;

import com.alibaba.fastjson.JSON;
import com.learnspringboot.study2.dto.AccessTokenDTO;
import com.learnspringboot.study2.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
//    GithubProvider githubProvider = new GithubProvider();

    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        System.out.println(JSON.toJSONString(accessTokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
                String str = response.body().string();
//                System.out.println(str);
                return response.body().string();

    }
    public GithubUser getUser(String accessToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        Response response = client.newCall(request).execute();
        String string = response.body().string();
        //自动解析成对象,把string的对象解析成java的类对象
        GithubUser githubUser = JSON.parseObject(string,GithubUser.class);
        System.out.println(githubUser.getName());
        return githubUser;
    }
}
