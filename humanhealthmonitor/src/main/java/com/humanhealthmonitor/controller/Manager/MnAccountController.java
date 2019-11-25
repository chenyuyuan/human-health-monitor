package com.humanhealthmonitor.controller.Manager;

import com.humanhealthmonitor.pojo.Admin;
import com.humanhealthmonitor.service.AdminLogService;
import com.humanhealthmonitor.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class MnAccountController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminLogService adminLogService;

    //管理中心-管理员账户-密码修改
    @RequestMapping("/manageCenter/mngAccountPwdModify")
    public String mngAccountPwdModify(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        return "manageCenter/mngAccountPwdModify";
    }

    //管理中心-管理员账户-密码修改-结果
    @RequestMapping("/manageCenter/mngAccountPwdModifyResult")
    public String mngAccountPwdModifyResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面取得原密码和新密码
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword1");

        //查看原密码是否输入正确
        if (oldPassword.equals(admin.getPwd())) {//如果输入正确，则修改密码成功
            //在数据库中更新管理员密码(session中的密码未改变)
            admin.setPwd(newPassword);
            adminService.updateAdminPassword(admin);
            //密码修改成功提示
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('密码修改成功!请重新登录');</script>");
            //注销session
            HttpSession session = request.getSession();
            session.removeAttribute("admin");
            return "login";
        } else {//如果原密码错误，则提示用户原密码不对
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('原密码不正确！密码修改失败');</script>");

            return "manageCenter/mngAccountPwdModify";
        }
    }
}
