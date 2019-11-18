package com.humanhealthmonitor.controller;

import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.pojo.Object;
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
public class IndexObjectController {
    @Autowired
    private ObjectService objectService;
    @Autowired
    private MainPicService mainPicService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private ObjectLogService objectLogService;
    @Autowired
    private ObjectResouceUseService objectResouceUseService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //Object主页
    @RequestMapping("/indexObject")
    public String indexObject(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);
        if (object == null) {
//            System.out.println("indexObject Null Session");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('Session不存在或已过期，请重新登录！');</script>");
            return "login";
        } else {
//            System.out.println("indexObject Not Null Session");
//            System.out.println(object.getObjectId());
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
            return "indexObject";
        }
    }
    //Object退出登录
    @RequestMapping("/objectLogout")
    public String objectLogout(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        HttpSession session = request.getSession();
        Object object = (Object)session.getAttribute("object");

        //将监测对象登录操作写入Log
        ObjectLog objectLog = new ObjectLog();
        objectLog.setObjectId(object.getObjectId());
        objectLog.setLogType("logout");
        String writeTime = dateformat.format(System.currentTimeMillis());
        objectLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
        int objectLogoutLogReturn = objectLogService.insertObjectLog(objectLog);
//        System.out.println("objectLogoutLogReturn: "+objectLogoutLogReturn);
        //将登录状态设置为离线
        object.setLastLogoutTime(java.sql.Timestamp.valueOf(writeTime));
        object.setLoginState(false);
        objectService.updateLogoutState(object);

        //在线时间更新
        String date = dateformat.format(System.currentTimeMillis());
        String yearMonth = date.substring(0,7);//如2019-03
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        ObjectResourceUse objectResourceUse = objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(object.getObjectId(),yearMonth);
        if (objectResourceUse != null) {
            int minuteOnlineTimeLength =(int)((object.getLastLogoutTime().getTime()-object.getLastLoginTime().getTime())/(1000*60));
            System.out.println("IndexObjectController: minuteOnlineTimeLength: "+minuteOnlineTimeLength);
            objectResourceUse.setOnlineTimeLength(objectResourceUse.getOnlineTimeLength()+minuteOnlineTimeLength);
            objectResouceUseService.updateObjectResourceUseOnlyOnlineTimeLength(objectResourceUse);
        }else {
            ObjectResourceUse newObjectResourceUse = new ObjectResourceUse();
            newObjectResourceUse.setObjectId(object.getObjectId());
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
                System.out.println("IndexObjectController: resourceUse endDate month error...");
            }
            newObjectResourceUse.setEndDate(endDate);
            newObjectResourceUse.setMsgCount(0);
            newObjectResourceUse.setOnlineTimeLength(0);
            newObjectResourceUse.setDataCount(0);
            objectResouceUseService.insertObjectResourceUse(newObjectResourceUse);

            int minuteOnlineTimeLength =(int)((object.getLastLogoutTime().getTime()-object.getLastLoginTime().getTime())/(1000*60));
            System.out.println("IndexObjectController: minuteOnlineTimeLength: "+minuteOnlineTimeLength);
            newObjectResourceUse.setOnlineTimeLength(minuteOnlineTimeLength);
            objectResouceUseService.updateObjectResourceUseOnlyOnlineTimeLength(newObjectResourceUse);
        }

        //销毁session
//        session.removeAttribute("object");
        return "login";
    }
}
