package com.learnspringboot.study2.controller;

import com.learnspringboot.study2.dto.NotificationDTO;
import com.learnspringboot.study2.dto.PageDTO;
import com.learnspringboot.study2.mapper.UserMapper;
import com.learnspringboot.study2.model.User;
import com.learnspringboot.study2.service.NotificationService;
import com.learnspringboot.study2.service.QuestionService;
import javafx.scene.control.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionServiceById;
    @Autowired
    private NotificationService notificationService;
    @RequestMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page",defaultValue = "1") Integer page,
                          @RequestParam(name = "size",defaultValue = "5") Integer size
                          ){


        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:/";
        }

        if ("questions".equals(action)){
            PageDTO pageDTO = questionServiceById.listById(user.getId(),page,size);
            model.addAttribute("pageDTO",pageDTO);
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");

        }else if("replies".equals(action)){
            List<NotificationDTO> notificationDTOS = notificationService.list(user.getId());
            model.addAttribute("notifications",notificationDTOS);
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }
}
