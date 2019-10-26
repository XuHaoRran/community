package com.learnspringboot.study2.interceptor;

import com.learnspringboot.study2.mapper.NotificationMapper;
import com.learnspringboot.study2.model.Notification;
import com.learnspringboot.study2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/*
用于查看现在的notifcation数
 */
@Service
public class NotificationInterceptor implements HandlerInterceptor {
    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        //查看该user的未读信息数
        if (user!= null){
            Integer notificationCount = notificationMapper.count(user.getId());
            System.out.println(notificationCount);
            request.getSession().setAttribute("notificationCount",notificationCount);
        }
        return true;
    }
}

