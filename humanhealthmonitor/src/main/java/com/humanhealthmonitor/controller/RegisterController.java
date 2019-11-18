package com.humanhealthmonitor.controller;

//import com.example.humanhealthmonitor.pojo.*;

import com.humanhealthmonitor.service.ObjectService;
import com.humanhealthmonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class RegisterController {
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectService objectService;

    /**
     * 用户注册页面
     */
    @RequestMapping("/register")
    public String register(HttpServletRequest request) {
//        String userId = request.getParameter();
//        User user = new User();
//        user.setUserId(userId);
//        userService.insertUser(user);
        return "register";
    }

    /**
     * 用户提交注册信息
     */
    @RequestMapping("/registerSubmit")
    public String registerSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //从页面取信息
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
//        String password1 = request.getParameter("password1");
//        String password2 = request.getParameter("password2");
        String password = request.getParameter("password1");
        String userTel = request.getParameter("telephone");
        String birthDate = request.getParameter("birthday");
        String sex = request.getParameter("sex");

//        System.out.println(userId);
//        System.out.println(userName);
////        System.out.println(password1);
////        System.out.println(password2);
//        System.out.println(password);
//        System.out.println(userTel);
//        System.out.println(birthDate);
//        System.out.println(sex);

        int registerResult = userService.userRegister(userId, userName, password, userTel, birthDate, sex);
        if (registerResult == -1 || registerResult == -2) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('账号已被注册！请更换账号名称');</script>");
            return "register";
        }
        if (registerResult > 0) {
//            response.setContentType("text/html;charset=utf-8");
//            PrintWriter out = response.getWriter();
//            out.print("<script language=\"javascript\">alert('恭喜您，注册成功！');</script>");

            //注册用户自身同id的监测对象供监测自己健康指标使用
            int objectAddResult = objectService.objectRegister(userId, userId, userName, password, userTel, birthDate, sex);
            if (objectAddResult > 0) {
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script language=\"javascript\">alert('恭喜您，注册成功！');</script>");
                //日志记录相关操作
            } else {//如果出现未知错误导致用户自身同id的监测对象注册失败，删除该用户并提示注册失败，请检查本地网络连接并重试
                userService.deleteUserByUserId(userId);
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script language=\"javascript\">alert('注册失败：出现未知错误，请检查网络连接并重新尝试');</script>");
            }
            return "login";
        }
//        //将页面上获取的信息放到User对象中
//        User user = new User();
//        user.setUserId(userId);
//        user.setUserName(userName);
//
//        userService.insertUser(user);

        return "login";
    }
}
