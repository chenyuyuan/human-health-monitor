package com.humanhealthmonitor.controller.Object;

import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.service.ObjectLogService;
import com.humanhealthmonitor.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class ObjAccountController {
    @Autowired
    private ObjectService objectService;
    @Autowired
    private ObjectLogService objectLogService;

    //健康中心-用户账户-基本信息
    @RequestMapping("/healthCenter/objAccountBasicInfo")
    public String objAccountBasicInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        return "healthCenter/objAccountBasicInfo";
    }

    //健康中心-用户账户-基本信息-修改
    @RequestMapping("/healthCenter/objAccountBasicInfoModify")
    public String objAccountBasicInfoModify(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //从页面获取信息
        String telephone = request.getParameter("telephone");
        System.out.println("ObjAccountController: telephone: "+telephone);

        //判断是否改变，没改提示没改，改了就更新数据库，提示操作成功
        if (telephone.equals(object.getObjectTel())) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('提示：手机号未作改动');</script>");
        } else {
            object.setObjectTel(telephone);
            objectService.updateObjectTel(object);

            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('手机号码修改成功！');</script>");

            //更新Session
            HttpSession session = request.getSession();
            session.setAttribute("object", object);
            request.setAttribute("object", object);
        }

        return "healthCenter/objAccountBasicInfo";
    }

    //监测中心-用户账户-密码修改
    @RequestMapping("/healthCenter/objAccountPwdModify")
    public String accountPwdModify(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        return "healthCenter/objAccountPwdModify";
    }

    //监测中心-用户账户-密码修改-结果
    @RequestMapping("/healthCenter/objAccountPwdModifyResult")
    public String accountPwdModifyResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //从页面取得原密码和新密码
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword1");
//        System.out.println(oldPassword);
//        System.out.println(newPassword);

        //查看原密码是否输入正确
        if (oldPassword.equals(object.getPwd())) {//如果输入正确，则修改密码成功
            //在数据库中更新监测对象密码(session中的密码未改变)
            object.setPwd(newPassword);
            objectService.updateObjectPassword(object);

            //密码修改成功提示
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('密码修改成功，请重新登录！');</script>");
            //注销session
            HttpSession session = request.getSession();
            session.removeAttribute("object");
            return "login";
        } else {//如果原密码错误，则提示用户原密码不对

            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('原密码不正确！密码修改失败');</script>");

            return "healthCenter/objAccountPwdModify";
        }

    }
}
