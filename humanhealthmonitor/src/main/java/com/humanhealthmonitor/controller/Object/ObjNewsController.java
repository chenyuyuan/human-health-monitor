package com.humanhealthmonitor.controller.Object;

import com.humanhealthmonitor.pojo.News;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.service.NewsService;
import com.humanhealthmonitor.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class ObjNewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private ObjectService objectService;

    //健康中心-主图新闻内容页
    @RequestMapping("/healthCenter/newsPageObject")
    public String newsPageObject(HttpServletRequest request, HttpServletResponse response) {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //根据取到的新闻id获取新闻全部信息并传到页面上
        int newsId = Integer.valueOf(request.getParameter("newsId"));
//        int newsId = Integer.valueOf(request.getParameter("mainPicNewsId"));
//        System.out.println(newsId);
        News news = newsService.queryNewsByNewsId(newsId);
        request.setAttribute("news", news);
        //浏览次数加1
        newsService.updateNewsViewCountByNewsId(newsId);

        return "healthCenter/newsPageObject";
    }

    //健康中心-主图新闻内容页
    @RequestMapping("/healthCenter/newsPageObjectMainPic")
    public String newsPageObjectMainPic(HttpServletRequest request, HttpServletResponse response) {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //根据取到的新闻id获取新闻全部信息并传到页面上
//        int newsId = Integer.valueOf(request.getParameter("newsId"));
        int newsId = Integer.valueOf(request.getParameter("mainPicNewsId"));
//        System.out.println(newsId);
        News news = newsService.queryNewsByNewsId(newsId);
        request.setAttribute("news", news);
        //浏览次数加1
        newsService.updateNewsViewCountByNewsId(newsId);

        return "healthCenter/newsPageObject";
    }

    //健康中心-新闻分类及搜索页面
    @RequestMapping("/healthCenter/newsSelectedKindObject")
    public String newsSelectedKindObject(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //从页面获取新闻种类并返回指定种类新闻
        String newsType = request.getParameter("newsType");
        List<News> newsList = newsService.queryNewsByNewsType(newsType);
        request.setAttribute("newsList", newsList);
        request.setAttribute("newsType", newsType);

        return "healthCenter/newsSelectedKindObject";
    }

    //监测中心-主页新闻搜索结果
    @RequestMapping("/healthCenter/newsSearchKindObject")
    public String newsSearchKindObject(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //从页面获取查询关键词并返回查询到的新闻
        String newsType = request.getParameter("newsType");//这里实际上是关键词，叫newsType是为了统一页面
        List<News> newsList = newsService.queryNewsByNewsHeadKey(newsType);
        //如果没查到，提示用户没查到
        if (newsList.size() == 0) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('未能找到相关新闻');javascript:history.back(-1);</script>");
        } else {
            request.setAttribute("newsList", newsList);
            request.setAttribute("newsType", "搜索结果-" + newsType);
        }

        return "healthCenter/newsSelectedKindObject";
    }
}
