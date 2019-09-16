package com.humanhealthmonitor.controller.Manager;

import com.humanhealthmonitor.pojo.Admin;
import com.humanhealthmonitor.pojo.MainPic;
import com.humanhealthmonitor.pojo.News;
import com.humanhealthmonitor.service.AdminLogService;
import com.humanhealthmonitor.service.AdminService;
import com.humanhealthmonitor.service.MainPicService;
import com.humanhealthmonitor.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.io.File;

//@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class})
@Controller
public class MnNewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private MainPicService mainPicService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminLogService adminLogService;

    //管理中心-发布新闻
    @RequestMapping("/manageCenter/newsMngPublish")
    public String newsMngPublish(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        return "manageCenter/newsMngPublish";
    }

    //管理中心-发布新闻-结果
    @RequestMapping("/manageCenter/newsMngPublishResult")
    public String newsMngPublishResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面获取新闻信息
        String newsHead = request.getParameter("newsHead");
        String newsType = request.getParameter("newsType");
        String newsContent = request.getParameter("newsContent");
//        System.out.println(newsHead);
//        System.out.println(newsType);
//        System.out.println(newsContent);

        //验证新闻标题未被占用后，将新闻插入数据库
        if (newsService.queryNewsByNewsHead(newsHead) != null) {
//            System.out.println("Line59");
            request.setAttribute("newsContent", newsContent);//保留原来编辑的文字内容
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('标题与已有新闻标题冲突，请重新设置')</script>");
        } else {
            News news = new News();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String publishDate = dateFormat.format(System.currentTimeMillis()).substring(0, 10);
            news.setPublishDate(java.sql.Date.valueOf(publishDate));
            news.setNewsHead(newsHead);
            news.setNewsType(newsType);
            news.setNewsContent(newsContent);
            newsService.insertNews(news);

            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">" +
                    "alert('发布成功!');" +
                    "window.close();" +
                    "</script>");
        }
        return "manageCenter/newsMngPublish";
    }

    //管理中心-新闻信息管理
    @RequestMapping("/manageCenter/newsMngInfoManage")
    public String newsMngInfoManage(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //查询全部新闻的基本信息并传到页面
        List<News> newsList = newsService.queryALlNewsBasicInfo();
        request.setAttribute("newsList", newsList);

        return "manageCenter/newsMngInfoManage";
    }

    //管理中心-新闻信息管理-关键词搜索结果
    @RequestMapping("/manageCenter/newsMngInfoManageSearch")
    public String newsMngInfoManageSearch(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面取回关键词，如果查询到相关结果就返回，查不到就提示
        String searchKey = request.getParameter("searchKey");
//        System.out.println(searchKey);
        List<News> newsList = newsService.queryNewsByNewsHeadKey(searchKey);
        if (newsList.size() == 0)//如果没有查到关键字相关的新闻
        {
//            System.out.println("Line106");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('未查找到相关新闻')</script>");

            //查询全部新闻的基本信息并传到页面
            newsList = newsService.queryALlNewsBasicInfo();
            request.setAttribute("newsList", newsList);
            request.setAttribute("searchKey", searchKey);
        } else//查到了符合关键字的新闻，把列表传到页面
        {
//            System.out.println("Line113");
            request.setAttribute("newsList", newsList);
            request.setAttribute("searchKey", searchKey);
        }
        return "manageCenter/newsMngInfoManage";
    }

    //管理中心-新闻信息管理-删除新闻
    @RequestMapping("/manageCenter/newsMngInfoManageDelete")
    public String newsMngInfoManageDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面获取要删除的新闻Id
        int newsId = Integer.valueOf(request.getParameter("newsId"));
        newsService.deleteNewsByNewsId(newsId);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('已成功删除该新闻')</script>");

        //查询全部新闻的基本信息并传到页面
        List<News> newsList = newsService.queryALlNewsBasicInfo();
        request.setAttribute("newsList", newsList);

        return "manageCenter/newsMngInfoManage";
    }

    //管理中心-新闻内容页
    @RequestMapping("/manageCenter/newsPageManager")
    public String newsPageManager(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //根据取到的新闻id获取新闻全部信息并传到页面上
        int newsId = Integer.valueOf(request.getParameter("newsId"));
//        System.out.println(newsId);
        News news = newsService.queryNewsByNewsId(newsId);
        request.setAttribute("news", news);
        //浏览次数加1
        newsService.updateNewsViewCountByNewsId(newsId);

        return "manageCenter/newsPageManager";
    }

    //管理中心-主图新闻内容页
    @RequestMapping("/manageCenter/newsPageManagerMainPic")
    public String newsPageManagerMainPic(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //根据取到的新闻id获取新闻全部信息并传到页面上
//        int newsId = Integer.valueOf(request.getParameter("newsId"));
        int newsId = Integer.valueOf(request.getParameter("mainPicNewsId"));
//        System.out.println(newsId);
        News news = newsService.queryNewsByNewsId(newsId);
        request.setAttribute("news", news);
        //浏览次数加1
        newsService.updateNewsViewCountByNewsId(newsId);

        return "manageCenter/newsPageManager";
    }

    //管理中心-主页图片管理
    @RequestMapping("/manageCenter/newsMainPictureManage")
    public String newsMainPictureManage(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //取出主页图片的URL以及所有新闻标题传到页面
        List<News> newsList = newsService.queryALlNewsBasicInfo();
        List<MainPic> mainPicList = mainPicService.queryAllMainPic();
//        System.out.println(mainPicList.get(0).getPicUrl());
//        System.out.println(newsList.get(0).getNewsHead());
        request.setAttribute("newsList", newsList);
        request.setAttribute("mainPicList", mainPicList);


        return "manageCenter/newsMainPictureManage";
    }

//    //管理中心-主页图片信息更新windows下
////    @ResponseBody//在使用 @RequestMapping后，返回值通常解析为跳转路径，但是加上 @ResponseBody 后返回结果不会被解析为跳转路径，而是直接写入 HTTP response body 中
//    @RequestMapping("/manageCenter/newsMainPictureUpdate")
//    public String newsMainPictureUpdate(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) throws IOException, NullPointerException {
//        Admin admin = (Admin) request.getSession().getAttribute("admin");
//        request.setAttribute("admin", admin);
//
//        //获取页面图片Id
//        int mainPicId = Integer.valueOf(request.getParameter("mainPicId"));
//
//        if (file != null) {// 判断上传的文件是否为空
//            String path = null;// 文件路径
//            String type = null;// 文件类型
//            String fileName = file.getOriginalFilename();// 获取文件原名称
//            System.out.println("上传的文件原名称:" + fileName);
//            // 判断文件类型
////            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
//            type = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
//            if (type != null) {// 判断文件类型是否为空
//                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
//                    // 项目在容器中实际发布运行的根路径
////                    String realPath=request.getSession().getServletContext().getRealPath("/");//这个获取的是target目录，具体放到linux服务器上可能需要修改
//                    // 自定义的文件名称
//                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;//保证不会重名
//                    // 设置存放图片文件的路径
////                    path=realPath+/*System.getProperty("file.separator")+*/trueFileName;
////                    H:\Java\IntellijProject2019\humanhealthmonitor\src\main\resources\static\images
////                    File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
////                    if(!rootPath.exists())
////                    {
////                        rootPath = new File("");
////                    }
////                    System.out.println("rootPath:"+rootPath.getAbsolutePath()+"line242");
////                    File upload = new File(rootPath.getAbsolutePath(),"static/images/");
////                    if(!upload.exists())
////                    {
////                        upload.mkdirs();
////                    }
////                    System.out.println("upload url:"+upload.getAbsolutePath());
////                    path = upload.getAbsolutePath()+System.getProperty("file.separator")+trueFileName;
//                    path = System.getProperty("user.dir") + System.getProperty("file.separator") + "src" +
//                            System.getProperty("file.separator") + "main" + System.getProperty("file.separator") +
//                            "resources" + System.getProperty("file.separator") + "static" + System.getProperty("file.separator") +
//                            "images" + System.getProperty("file.separator") + trueFileName;
//                    System.out.println("存放图片文件的路径:" + path);
//                    // 转存文件到指定的路径
//                    file.transferTo(new File(path));
//                    System.out.println("文件成功上传到指定目录下");
//                    //将新的图片文件路径更新到数据库
////                    String sqlPath = "images"+System.getProperty("file.separator")+trueFileName;//分隔符使用/才有效
//                    String sqlPath = "images/" + trueFileName;
//                    MainPic mainPic = mainPicService.queryMainPicById(mainPicId);
//                    mainPic.setPicUrl(sqlPath);
//                    mainPicService.updateMainPicUrl(mainPic);
//
//                    response.setContentType("text/html;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    out.print("<script language=\"javascript\">alert('已成功上传新图片并替换原图')</script>");
//                } else {
////                    System.out.println("只支持jpg/png/gif格式，请重新上传正确格式的图片");
////                    return null;
//                    response.setContentType("text/html;charset=utf-8");
//                    PrintWriter out = response.getWriter();
//                    out.print("<script language=\"javascript\">alert('只支持jpg/png/gif格式，请重新上传正确格式的图片')</script>");
//                }
//            } else {
////                System.out.println("文件类型为空");
////                return null;
//                response.setContentType("text/html;charset=utf-8");
//                PrintWriter out = response.getWriter();
//                out.print("<script language=\"javascript\">alert('错误，文件类型不能为空')</script>");
//            }
//        } else {
////            System.out.println("没有找到相对应的文件");
////            return null;
//            response.setContentType("text/html;charset=utf-8");
//            PrintWriter out = response.getWriter();
//            out.print("<script language=\"javascript\">alert('错误，没有找到该文件')</script>");
//        }
//
//        //取出主页图片的URL以及所有新闻标题传到页面
//        List<News> newsList = newsService.queryALlNewsBasicInfo();
//        List<MainPic> mainPicList = mainPicService.queryAllMainPic();
//        request.setAttribute("newsList", newsList);
//        request.setAttribute("mainPicList", mainPicList);
//
//        return "manageCenter/newsMainPictureManage";
//    }


    //管理中心-主页图片信息更新Linux下
//    @ResponseBody//在使用 @RequestMapping后，返回值通常解析为跳转路径，但是加上 @ResponseBody 后返回结果不会被解析为跳转路径，而是直接写入 HTTP response body 中
    @RequestMapping("/manageCenter/newsMainPictureUpdate")
    public String newsMainPictureUpdate(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //获取页面图片Id
        int mainPicId = Integer.valueOf(request.getParameter("mainPicId"));

        if (file != null) {// 判断上传的文件是否为空
            String path = null;// 文件路径
            String type = null;// 文件类型
            String fileName = file.getOriginalFilename();// 获取文件原名称
            System.out.println("MnNewsController: name of upload file: " + fileName);
            // 判断文件类型
//            type=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            type = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
            if (type != null) {// 判断文件类型是否为空
                if ("GIF".equals(type.toUpperCase()) || "PNG".equals(type.toUpperCase()) || "JPG".equals(type.toUpperCase())) {
                    // 项目在容器中实际发布运行的根路径
//                    String realPath=request.getSession().getServletContext().getRealPath("/");//这个获取的是target目录，具体放到linux服务器上可能需要修改
                    // 自定义的文件名称
//                    String trueFileName = String.valueOf(System.currentTimeMillis()) + fileName;//保证不会重名
                    //只用上传时刻命名，避免中文，后续如有必要可添加中文支持,文件没有".jpg"等后缀名，如需添加，可直接取发了fileName后四个字符
                    String trueFileName = String.valueOf(System.currentTimeMillis());
                    // 设置存放图片文件的路径
//                    path=realPath+/*System.getProperty("file.separator")+*/trueFileName;
//                    H:\Java\IntellijProject2019\humanhealthmonitor\src\main\resources\static\images
//                    File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
//                    if(!rootPath.exists())
//                    {
//                        rootPath = new File("");
//                    }
//                    System.out.println("rootPath:"+rootPath.getAbsolutePath()+"line242");
//                    File upload = new File(rootPath.getAbsolutePath(),"static/images/");
//                    if(!upload.exists())
//                    {
//                        upload.mkdirs();
//                    }
//                    System.out.println("upload url:"+upload.getAbsolutePath());
//                    path = upload.getAbsolutePath()+System.getProperty("file.separator")+trueFileName;

//                    path = System.getProperty("user.dir") + System.getProperty("file.separator") + "src" +
//                            System.getProperty("file.separator") + "main" + System.getProperty("file.separator") +
//                            "resources" + System.getProperty("file.separator") + "static" + System.getProperty("file.separator") +
//                            "images" + System.getProperty("file.separator") + trueFileName;
//                    System.out.println("存放图片文件的路径:" + path);
                    path = "/root/SpringRun/images/" + trueFileName;
                    // 转存文件到指定的路径
                    file.transferTo(new File(path));
//                    System.out.println("文件成功上传到指定目录下");
                    //将新的图片文件路径更新到数据库
//                    String sqlPath = "images"+System.getProperty("file.separator")+trueFileName;//分隔符使用/才有效
                    String sqlPath = "images/" + trueFileName;
                    MainPic mainPic = mainPicService.queryMainPicById(mainPicId);
                    mainPic.setPicUrl(sqlPath);
                    mainPicService.updateMainPicUrl(mainPic);

                    response.setContentType("text/html;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("<script language=\"javascript\">alert('已成功上传新图片并替换原图')</script>");
                } else {
//                    System.out.println("只支持jpg/png/gif格式，请重新上传正确格式的图片");
//                    return null;
                    response.setContentType("text/html;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("<script language=\"javascript\">alert('只支持jpg/png/gif格式，请重新上传正确格式的图片')</script>");
                }
            } else {
//                System.out.println("文件类型为空");
//                return null;
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script language=\"javascript\">alert('错误，文件类型不能为空')</script>");
            }
        } else {
//            System.out.println("没有找到相对应的文件");
//            return null;
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('错误，没有找到该文件')</script>");
        }

        //取出主页图片的URL以及所有新闻标题传到页面
        List<News> newsList = newsService.queryALlNewsBasicInfo();
        List<MainPic> mainPicList = mainPicService.queryAllMainPic();
        request.setAttribute("newsList", newsList);
        request.setAttribute("mainPicList", mainPicList);

        return "manageCenter/newsMainPictureManage";
    }

    //管理中心-主页新闻信息更新
    @RequestMapping("/manageCenter/newsMainNewsUpdate")
    public String newsMainNewsUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面获取新选中的新闻id和主页图片id
        int newsIdSelected = Integer.valueOf(request.getParameter("newsSelected"));
        int mainPicId = Integer.valueOf(request.getParameter("mainPicId"));
//        System.out.println(newsIdSelected);
//        System.out.println(mainPicId);
        //更新图片所对应新闻的Id和标题
        MainPic mainPic = mainPicService.queryMainPicById(mainPicId);
        mainPic.setNewsId(newsIdSelected);
        mainPic.setNewsHead(newsService.queryNewsByNewsId(newsIdSelected).getNewsHead());
        mainPicService.updateMainPicNewsInfo(mainPic);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('已成功更新该图片对应的新闻')</script>");

        //取出主页图片的URL以及所有新闻标题传到页面
        List<News> newsList = newsService.queryALlNewsBasicInfo();
        List<MainPic> mainPicList = mainPicService.queryAllMainPic();
        request.setAttribute("newsList", newsList);
        request.setAttribute("mainPicList", mainPicList);

        return "manageCenter/newsMainPictureManage";
    }

    //管理中心-新闻分类及搜索页面
    @RequestMapping("/manageCenter/newsSelectedKindManager")
    public String newsSelectedKindManager(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面获取新闻种类并返回指定种类新闻
        String newsType = request.getParameter("newsType");
        List<News> newsList = newsService.queryNewsByNewsType(newsType);
        request.setAttribute("newsList", newsList);
        request.setAttribute("newsType", newsType);

        return "manageCenter/newsSelectedKindManager";
    }

    //管理中心-主页新闻搜索结果
    @RequestMapping("/manageCenter/newsSearchKindManager")
    public String newsSearchKindManager(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面获取查询关键词并返回查询到的新闻
        String newsType = request.getParameter("newsType");//这里实际上是关键词，叫newsType是为了统一页面
        List<News> newsList = newsService.queryNewsByNewsHeadKey(newsType);
        //如果没查到，提示用户没查到
        if (newsList.size() == 0) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('没有查到相关新闻');javascript:history.back(-1);</script>");
        } else {
            request.setAttribute("newsList", newsList);
            request.setAttribute("newsType", "搜索结果-" + newsType);
        }

        return "manageCenter/newsSelectedKindManager";
    }
}