package com.humanhealthmonitor.controller;

import com.humanhealthmonitor.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class HelpController {
    /**
     * 用户帮助页面
     */
//    @Autowired
//    private LoginController loginController;

    @RequestMapping("/help")
    public String help(HttpServletRequest request) {
//        String userId = request.getParameter();
//        User user = new User();
//        user.setUserId(userId);
//        userService.insertUser(user);
//        LoginController loginController = new LoginController();
//        loginController.Testttt();
        return "help";
    }
}
