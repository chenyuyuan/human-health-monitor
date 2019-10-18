package com.humanhealthmonitor.controller;

import org.json.JSONObject;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;

@Controller
public class TestPageController {
    private static final Logger log = LoggerFactory.getLogger(TestPageController.class);

    @RequestMapping("/test")
    public String login(HttpServletRequest request) throws InterruptedException {

        return "test";
    }
}
