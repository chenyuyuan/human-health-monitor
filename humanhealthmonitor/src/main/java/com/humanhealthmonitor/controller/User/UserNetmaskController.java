package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.service.UserNetmaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserNetmaskController {

    @Autowired
    private UserNetmaskService userNetmaskService;

    @RequestMapping("/monitorCenter/netmaskRelated")
    public String login(HttpServletRequest request) throws InterruptedException {



        return "monitorCenter/netmaskRelated";
    }





}
