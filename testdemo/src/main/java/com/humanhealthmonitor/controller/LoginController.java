package com.humanhealthmonitor.controller;

import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.pojo.UserLog;
import com.humanhealthmonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/login")
    public String login(HttpServletRequest request) throws InterruptedException {
        HttpSession session = request.getSession();
        return "login";
    }
}
