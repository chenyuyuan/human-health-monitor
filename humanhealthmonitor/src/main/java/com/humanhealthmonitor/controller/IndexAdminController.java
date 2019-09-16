package com.humanhealthmonitor.controller;

import com.humanhealthmonitor.pojo.Admin;
import com.humanhealthmonitor.pojo.AdminLog;
import com.humanhealthmonitor.pojo.MainPic;
import com.humanhealthmonitor.pojo.News;
import com.humanhealthmonitor.service.AdminLogService;
import com.humanhealthmonitor.service.AdminService;
import com.humanhealthmonitor.service.MainPicService;
import com.humanhealthmonitor.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class IndexAdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private MainPicService mainPicService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private AdminLogService adminLogService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //管理员主页
    @RequestMapping("/indexAdmin")
    public String indexAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");

        if (admin == null) {
//            System.out.println("indexAdmin Null Session");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('Session不存在或已过期，请重新登录！');</script>");
            return "login";
        } else {
//            System.out.println("indexAdmin Not Null Session");
//            System.out.println(admin.getAdminId());
            request.setAttribute("admin", admin);

            //设置主页图片
            List<MainPic> mainPicList = mainPicService.queryAllMainPic();
//            System.out.println(mainPicList.get(0).getPicUrl());
//            System.out.println("Line51");
//            System.out.println(mainPicList.get(0).getNewsId());
//            System.out.println("Line53");
//            System.out.println(mainPicList.size());
            request.setAttribute("mainPicList", mainPicList);

            //设置主页分类新闻列表
            List<News> healthNewsList = newsService.queryNewsByNewsTypeTop5("健康阅读");
            List<News> latestNewsList = newsService.queryNewsByNewsTypeTop5("最新消息");
            List<News> hotPointNewsList = newsService.queryNewsByNewsTypeTop5("热点新闻");
            request.setAttribute("healthNewsList", healthNewsList);
            request.setAttribute("latestNewsList", latestNewsList);
            request.setAttribute("hotPointNewsList", hotPointNewsList);


            return "indexAdmin";
        }
    }

    //管理员退出登录
    @RequestMapping("/adminLogout")
    public String adminLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        //将退出登录动作写入日志
        AdminLog adminLog = new AdminLog();
        adminLog.setAdminId(admin.getAdminId());////////////
        adminLog.setLogType("logout");
        String writeTime = dateformat.format(System.currentTimeMillis());
        adminLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
        int adminLogoutLogReturn = adminLogService.insertAdminLog(adminLog);
//        System.out.println("adminLogoutLogReturn: "+adminLogoutLogReturn);
        //将管理员登录状态设置为离线
        admin.setLoginState(false);
        adminService.updateAdminLoginState(admin);
        //销毁session
//        session.removeAttribute("admin");

        return "login";
    }

}
