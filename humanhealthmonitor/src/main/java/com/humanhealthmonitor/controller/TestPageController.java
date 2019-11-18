package com.humanhealthmonitor.controller;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestPageController {
    private static final Logger log = LoggerFactory.getLogger(TestPageController.class);

    @RequestMapping("/test")
    public String login(HttpServletRequest request) throws InterruptedException {

        return "test";
    }
    @RequestMapping("/loginnew")
    public String loginNew(HttpServletRequest request) throws InterruptedException {

        return "login";
    }
}
