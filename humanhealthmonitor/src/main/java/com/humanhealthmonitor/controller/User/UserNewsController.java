package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.pojo.News;
import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class UserNewsController {
    @Autowired
    private NewsService newsService;

    //监测中心-主图新闻内容页
    @RequestMapping("/monitorCenter/newsPageUser")
    public String newsPageUser(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //根据取到的新闻id获取新闻全部信息并传到页面上
        int newsId = Integer.valueOf(request.getParameter("newsId"));
//        int newsId = Integer.valueOf(request.getParameter("mainPicNewsId"));
//        System.out.println(newsId);
        News news = newsService.queryNewsByNewsId(newsId);
        request.setAttribute("news", news);
        //浏览次数加1
        newsService.updateNewsViewCountByNewsId(newsId);

        return "monitorCenter/newsPageUser";
    }

    //监测中心-主图新闻内容页
    @RequestMapping("/monitorCenter/newsPageUserMainPic")
    public String newsPageUserMainPic(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //根据取到的新闻id获取新闻全部信息并传到页面上
//        int newsId = Integer.valueOf(request.getParameter("newsId"));
        int newsId = Integer.valueOf(request.getParameter("mainPicNewsId"));
//        System.out.println(newsId);
        News news = newsService.queryNewsByNewsId(newsId);
        request.setAttribute("news", news);
        //浏览次数加1
        newsService.updateNewsViewCountByNewsId(newsId);

        return "monitorCenter/newsPageUser";
    }

    //监测中心-新闻分类及搜索页面
    @RequestMapping("/monitorCenter/newsSelectedKindUser")
    public String newsSelectedKindUser(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从页面获取新闻种类并返回指定种类新闻
        String newsType = request.getParameter("newsType");
        List<News> newsList = newsService.queryNewsByNewsType(newsType);
        request.setAttribute("newsList", newsList);
        request.setAttribute("newsType", newsType);

        return "monitorCenter/newsSelectedKindUser";
    }

    //监测中心-主页新闻搜索结果
    @RequestMapping("/monitorCenter/newsSearchKindUser")
    public String newsSearchKindUser(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从页面获取查询关键词并返回查询到的新闻
        String newsType = request.getParameter("newsType");//这里实际上是关键词，叫newsType是为了统一页面
        List<News> newsList = newsService.queryNewsByNewsHeadKey(newsType);
        //如果没查到，提示用户没查到
        if (newsList.size() == 0) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('未找到相关新闻');javascript:history.back(-1);</script>");
        } else {
            request.setAttribute("newsList", newsList);
            request.setAttribute("newsType", "搜索结果-" + newsType);
        }

        return "monitorCenter/newsSelectedKindUser";
    }
}
