package com.learnspringboot.study2.interceptor;

import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        //先制造precookie，防止后面的空指针，
        Cookie precookie = new Cookie("precookie","123123123");
        response.addCookie(precookie);
        Cookie[] cookies = request.getCookies();
        //第一次登录时      如果数组为空
        if(cookies == null){
            System.out.println("asb");
            return true;
        }
        for (Cookie cookie : cookies){
            if ("token".equals(cookie.getName())){
                System.out.println(cookie.getValue());
                String token = cookie.getValue();
                User user = userMapper.findByToken(token);
                System.out.println(request.getSession().getAttribute("user"));
                if(user != null){
                    request.getSession().setAttribute("user",user);
                }
                break;
            }
        }
        System.out.println("这里使用拦截器了Pre");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
