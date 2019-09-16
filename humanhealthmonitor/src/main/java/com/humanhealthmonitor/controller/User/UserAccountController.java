package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.service.UserLogService;
import com.humanhealthmonitor.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UserAccountController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserLogService userLogService;

    //监测中心-用户账户-基本信息
    @RequestMapping("/monitorCenter/accountBasicInfo")
    public String accountBasicInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        request.setAttribute("female", "女");
        request.setAttribute("male", "男");

        return "monitorCenter/accountBasicInfo";
    }

    //监测中心-用户账户-基本信息-修改
    @RequestMapping("/monitorCenter/accountBasicInfoModify")
    public String accountBasicInfoModify(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
//        User user = (User) request.getSession().getAttribute("user");
//        request.setAttribute("user", user);

        //取回页面修改后的信息并更新数据库
        String userId = request.getParameter("userIdInfo");
        String userName = request.getParameter("userName");
        String sex = request.getParameter("sex");
        String birthDate = request.getParameter("birthday");
        String userTel = request.getParameter("telephone");
//        System.out.println(userId);
//        System.out.println(userName);
//        System.out.println(sex);
//        System.out.println(birthDate);
//        System.out.println(userTel);

        User newUser = new User();
        newUser.setUserId(userId);
        newUser.setUserName(userName);
        newUser.setSex(sex);
        newUser.setBirthDate(java.sql.Date.valueOf(birthDate));
        newUser.setUserTel(userTel);

        userService.updateUserBasicInfo(newUser);

        //提示用户信息修改成功
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('信息修改成功！');</script>");

        //更新Session并重新向页面传值
        HttpSession session = request.getSession();
//        session.removeAttribute("user");
        newUser = userService.queryUserByUserId(userId);
        session.setAttribute("user", newUser);
        request.setAttribute("user", newUser);
        request.setAttribute("female", "女");
        request.setAttribute("male", "男");

        return "monitorCenter/accountBasicInfo";
    }

    //监测中心-用户账户-密码修改
    @RequestMapping("/monitorCenter/accountPwdModify")
    public String accountPwdModify(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        return "monitorCenter/accountPwdModify";
    }

    //监测中心-用户账户-密码修改-结果
    @RequestMapping("/monitorCenter/accountPwdModifyResult")
    public String accountPwdModifyResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从页面取得原密码和新密码
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword1");
//        System.out.println(oldPassword);
//        System.out.println(newPassword);

        //查看原密码是否输入正确
        if (oldPassword.equals(user.getPwd())) { //如果输入正确，则修改密码成功
            //在数据库中更新用户密码(session中的密码未改变)
            user.setPwd(newPassword);
            userService.updateUserPassword(user);
            //密码修改成功提示
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('密码修改成功，请重新登录！');</script>");
            //注销session
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            return "login";
        } else { //如果原密码错误，则提示用户原密码不对
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('原密码不正确！密码修改失败');</script>");

            return "monitorCenter/accountPwdModify";
        }

    }
}
