package com.humanhealthmonitor.controller;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestPageController {
    private static final Logger log = LoggerFactory.getLogger(TestPageController.class);

    @RequestMapping("/sendorder")
    public String sendorder(HttpServletRequest request) throws InterruptedException {
        return "sendOrderPage";
    }

    @RequestMapping("/test")
    public String login(HttpServletRequest request) throws InterruptedException {
        return "test";
    }
    @RequestMapping("/hhm-login.html")
    public String hhm_login(HttpServletRequest request) throws InterruptedException {
        return "hhm-login";
    }
    @RequestMapping("/hhm-adddevice.html")
    public String hhm_adddevice(HttpServletRequest request) throws InterruptedException {
        return "hhm-adddevice";
    }
    @RequestMapping("/hhm-addnetmask.html")
    public String hhm_addnetmask(HttpServletRequest request) throws InterruptedException {
        return "hhm-addnetmask";
    }
    @RequestMapping("/hhm-addobject.html")
    public String hhm_addobject(HttpServletRequest request) throws InterruptedException {
        return "hhm-addobject";
    }
    @RequestMapping("/hhm-index.html")
    public String hhm_index(HttpServletRequest request) throws InterruptedException {
        return "hhm-index";
    }
    @RequestMapping("/hhm-managedevice.html")
    public String hhm_managedevice(HttpServletRequest request) throws InterruptedException {
        return "hhm-managedevice";
    }
    @RequestMapping("/hhm-managenetmask.html")
    public String hhm_managenetmask(HttpServletRequest request) throws InterruptedException {
        return "hhm-managenetmask";
    }
    @RequestMapping("/hhm-manageobject.html")
    public String hhm_manageobject(HttpServletRequest request) throws InterruptedException {
        return "hhm-manageobject";
    }
    @RequestMapping("/hhm-monitorinfo.html")
    public String hhm_monitorinfo(HttpServletRequest request) throws InterruptedException {
        return "hhm-monitorinfo";
    }
    @RequestMapping("/hhm-monitorhistory.html")
    public String hhm_monitorhistory(HttpServletRequest request) throws InterruptedException {
        return "hhm-monitorhistory";
    }
    @RequestMapping("/hhm-register.html")
    public String hhm_register(HttpServletRequest request) throws InterruptedException {
        return "hhm-register";
    }
}
