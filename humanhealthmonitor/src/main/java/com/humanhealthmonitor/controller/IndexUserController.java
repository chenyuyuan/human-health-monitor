package com.humanhealthmonitor.controller;

import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.service.*;
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
public class IndexUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MainPicService mainPicService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private ObjectResouceUseService objectResouceUseService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //User主页
    @RequestMapping("/indexUser")
    public String indexUser(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);
        if (user == null) {
//            System.out.println("indexUser Null Session");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('Session不存在或已过期，请重新登录！');</script>");
            return "login";
        } else {
//            System.out.println("indexUser Not Null Session");
//            System.out.println(user.getUserId());
            //设置主页图片
            List<MainPic> mainPicList = mainPicService.queryAllMainPic();
            request.setAttribute("mainPicList", mainPicList);



            //设置主页分类新闻列表
            List<News> healthNewsList = newsService.queryNewsByNewsTypeTop5("健康阅读");
            List<News> latestNewsList = newsService.queryNewsByNewsTypeTop5("最新消息");
            List<News> hotPointNewsList = newsService.queryNewsByNewsTypeTop5("热点新闻");


            request.setAttribute("healthNewsList", healthNewsList);
            request.setAttribute("latestNewsList", latestNewsList);
            request.setAttribute("hotPointNewsList", hotPointNewsList);
            return "indexUser";
        }
    }

    //用户退出登录
    @RequestMapping("/userLogout")
    public String userLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        //将退出登录动作写入日志
        UserLog userLog = new UserLog();
        userLog.setUserId(user.getUserId());
        userLog.setLogType("logout");
        String writeTime = dateformat.format(System.currentTimeMillis());
        userLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
        int userLogoutLogReturn = userLogService.insertUserLog(userLog);
//        System.out.println("userLogoutLogReturn: "+userLogoutLogReturn);
        //将登录状态设置为离线
        user.setLastLogoutTime(java.sql.Timestamp.valueOf(writeTime));
        user.setLoginState(false);
        userService.updateUserLogoutState(user);

        //在线时间更新
        String date = dateformat.format(System.currentTimeMillis());
        String yearMonth = date.substring(0,7);//如2019-03
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        ObjectResourceUse objectResourceUse = objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(user.getUserId(),yearMonth);
        if (objectResourceUse != null) {
            int minuteOnlineTimeLength =(int)((user.getLastLogoutTime().getTime()-user.getLastLoginTime().getTime())/(1000*60));
            System.out.println("IndexUserController: minuteOnlineTimeLength: "+minuteOnlineTimeLength);
            objectResourceUse.setOnlineTimeLength(objectResourceUse.getOnlineTimeLength()+minuteOnlineTimeLength);
            objectResouceUseService.updateObjectResourceUseOnlyOnlineTimeLength(objectResourceUse);
        }else {
            ObjectResourceUse newObjectResourceUse = new ObjectResourceUse();
            newObjectResourceUse.setObjectId(user.getUserId());
            newObjectResourceUse.setYearMonth(yearMonth);
            java.sql.Date beginDate=java.sql.Date.valueOf(yearMonth+"-01");
            newObjectResourceUse.setBeginDate(beginDate);
            java.sql.Date endDate = beginDate;
            if(month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||
                    month.equals("08")||month.equals("10")||month.equals("12")) {
                endDate = java.sql.Date.valueOf(yearMonth+"-31");
            }else if(month.equals("04")||month.equals("06")||month.equals("09")||month.equals("11")) {
                endDate = java.sql.Date.valueOf(yearMonth+"-30");
            }else if(month.equals("02")) {
                if(Integer.valueOf(year)%100 == 0) {
                    if (Integer.valueOf(year)%400 == 0) {
                        endDate = java.sql.Date.valueOf(yearMonth+"-29");
                    }
                    else {
                        endDate = java.sql.Date.valueOf(yearMonth+"-28");
                    }
                }else {
                    if (Integer.valueOf(year)%4 == 0) {
                        endDate = java.sql.Date.valueOf(yearMonth+"-29");
                    }else {
                        endDate = java.sql.Date.valueOf(yearMonth+"-28");
                    }
                }
            }else {
                System.out.println("IndexUserController: resourceUse endDate month error...");
            }
            newObjectResourceUse.setEndDate(endDate);
            newObjectResourceUse.setMsgCount(0);
            newObjectResourceUse.setOnlineTimeLength(0);
            newObjectResourceUse.setDataCount(0);
            objectResouceUseService.insertObjectResourceUse(newObjectResourceUse);

            int minuteOnlineTimeLength =(int)((user.getLastLogoutTime().getTime()-user.getLastLoginTime().getTime())/(1000*60));
            System.out.println("IndexUserController: minuteOnlineTimeLength: "+minuteOnlineTimeLength);
            newObjectResourceUse.setOnlineTimeLength(minuteOnlineTimeLength);
            objectResouceUseService.updateObjectResourceUseOnlyOnlineTimeLength(newObjectResourceUse);
        }
        //销毁session
//        session.removeAttribute("user");

        return "login";
    }
}
