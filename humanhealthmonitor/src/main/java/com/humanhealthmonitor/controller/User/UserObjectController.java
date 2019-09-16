package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.pojo.User;
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
public class UserObjectController {
    @Autowired
    private ObjectService objectService;

    //监测中心-添加监测对象
    @RequestMapping("/monitorCenter/objectAdd")
    public String objectAdd(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        return "monitorCenter/objectAdd";
    }

    //监测中心-添加监测对象结果处理
    @RequestMapping("/monitorCenter/objectAddResult")
    public String objectAddResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从页面取信息
        String objectId = request.getParameter("objectId");
        String objectName = request.getParameter("objectName");
        String password = request.getParameter("password1");
        String objectTel = request.getParameter("telephone");
        String birthDate = request.getParameter("birthday");
        String sex = request.getParameter("sex");

        int objectAddResult = objectService.objectRegister(objectId, user.getUserId(), objectName, password, objectTel, birthDate, sex);
        if (objectAddResult == -1) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('账号已被注册！请更换账号名称');</script>");
            return "monitorCenter/objectAdd";
        }
        if (objectAddResult > 0) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('恭喜您，注册成功！');</script>");
            //需要日志记录相关操作
            return "monitorCenter/objectAdd";
        }

        return "monitorCenter/objectAdd";
    }

    //监测中心-监测对象信息管理
    @RequestMapping("/monitorCenter/objectInfoManage")
    public String objectInfoManage(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从数据库中获取所有该用户关联的监测对象并传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);
        //获取第一个监测对象的实例传到前台
        Object objectFirst = objectList.get(0);
        request.setAttribute("object", objectFirst);
        request.setAttribute("female", "女");
        request.setAttribute("male", "男");
        return "monitorCenter/objectInfoManage";
    }

    //监测中心-监测对象信息管理-选择对象后展示信息
    @RequestMapping("/monitorCenter/objectInfoManageSelect")
    public String objectInfoManageSelect(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从数据库中获取所有该用户关联的监测对象并传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);
        //获取被选中的对象的id并把对象实例传到前台
        String objectIdSelected = request.getParameter("objectId");
        Object object = objectService.queryObjectByObjectId(objectIdSelected);
        request.setAttribute("object", object);
        request.setAttribute("female", "女");
        request.setAttribute("male", "男");
        return "monitorCenter/objectInfoManage";
    }

    //监测中心-监测对象信息修改
    @RequestMapping("/monitorCenter/objectInfoModify")
    public String objectInfoModify(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从页面获取修改后的数据
        String objectId = request.getParameter("objectIdInfo");
        String objectName = request.getParameter("objectName");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        String telephone = request.getParameter("telephone");
//        System.out.println(objectId);
//        System.out.println(objectName);
//        System.out.println(sex);
//        System.out.println(birthday);
//        System.out.println(telephone);

        //在数据库中更改监测对象信息
        Object object = new Object();
        object.setObjectId(objectId);
        object.setObjectName(objectName);
        object.setSex(sex);
        object.setBirthDate(java.sql.Date.valueOf(birthday));
//        System.out.println(object.getBirthDate());
        object.setObjectTel(telephone);
        objectService.updateObjectByUser(object);

        //从数据库中获取所有该用户关联的监测对象并传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);
        //获取刚刚修改的监测对象的实例传到前台
        request.setAttribute("object", object);
        request.setAttribute("female", "女");
        request.setAttribute("male", "男");

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('信息修改成功！');</script>");

        return "monitorCenter/objectInfoManage";
    }

}
