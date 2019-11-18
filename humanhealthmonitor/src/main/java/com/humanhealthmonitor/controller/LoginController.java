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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminLogService adminLogService;
    @Autowired
    private ObjectService objectService;
    @Autowired
    private ObjectLogService objectLogService;
    @Autowired
    private MainPicService mainPicService;
    @Autowired
    private NewsService newsService;
    //    @Autowired
    //    private EquipmentService equipmentService;
    @Autowired
    private AlarmLogService alarmLogService;
    @Autowired
    private ObjectResouceUseService objectResouceUseService;

    //    CloudMsgUtil cloudMsgUtil;
    //    CloudMsgUtil cloudMsgUtil = new CloudMsgUtil();
    //    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    //            TimeZone.getTimeZone("GMT+08:00")
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //    format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    @RequestMapping("/login")
    public String login(HttpServletRequest request) throws InterruptedException {
        //先清除原来的session
        HttpSession session = request.getSession();
        //        session.removeAttribute("user");
        //        session.removeAttribute("object");
        //        session.removeAttribute("admin");

        //        sendAMQPQueue.offer("FEFE0401040005AABB");///////////////////测试0524

        //        //测试5-sendMsgQueue
        //        sendMsgQueue.offer("FEFE0401040005AABB");

        //        //测试1，根据网关id和网关内部顺序号查询设备信息
        //        Equipment equipmentData = equipmentService .queryEquipmentByNetSerial(1,0);//////////////////////
        //        System.out.println("EqpId: "+equipmentData.getEqpId());////////////////////////
        //        Testttt();

        //        //测试2，/manageCenter/userInfoHallGroupData数据展示
        //        Timestamp beginTime = java.sql.Timestamp.valueOf(dateformat.format((System.currentTimeMillis()-7200000)));//一小时前
        //        Timestamp endTime = java.sql.Timestamp.valueOf(dateformat.format((System.currentTimeMillis()-3600000)));//现在
        //        int count = alarmLogService.queryAlarmLogCountDuringCertainTime(beginTime,endTime);
        //        System.out.println("count: "+ count);

        //        //测试3，为了方便展示，每次运行时更改数据库中的alarmLog所有记录的时间
        //        alarmLogService.updateAllAlarmLogWriteTime();

        //        //测试4，腾讯云短信发送测试
        //        //准备必要的参数
        //        int appid = 1400176937; // 1400开头// 短信应用SDK AppID
        //        String appkey = "9b9899ca5e4510220410d6298df2f3cc";// 短信应用SDK AppKey
        //        String[] phoneNumbers = {"18463100658", "13371184957"};// 需要发送短信的手机号码
        //        int templateId = 259905; // 短信模板ID，真实的模板ID需要在短信控制台中申请,如："您的验证码是: {1}"
        //        String smsSign = "威小工健康";// 签名// NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`
        ////        try {
        ////            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,"A00060302","高压168超出正常范围");
        ////        } catch (HTTPException e) {
        ////            e.printStackTrace();
        ////        } catch (IOException e) {
        ////            e.printStackTrace();
        ////        }
        //        //短信单发/群发
        //        try {
        //            String[] params = {"A00020201","高压168超出正常范围"};//数组具体的元素个数和模板中变量个数必须一致
        //            //单发
        ////            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        ////            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
        ////                    templateId, params, smsSign, "", "");//单发// 签名参数未提供或者为空时，会使用默认签名发送短信
        //            //群发
        //            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
        //            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
        //                    templateId, params, smsSign, "", "");//群发// 签名参数未提供或者为空时，会使用默认签名发送短信
        //            System.out.println(result.toString());
        //            String reStr;//结果字符串
        //            if(result.result == 0)
        //            {
        //                reStr = "send message successfully";
        //            }
        //            else
        //            {
        //                reStr = "!send error";
        //            }
        //            System.out.println(reStr);
        //        } catch (HTTPException e) {
        //            // HTTP响应码错误
        //            e.printStackTrace();
        //        } catch (JSONException e) {
        //            // json解析错误
        //            e.printStackTrace();
        //        } catch (IOException e) {
        //            // 网络IO错误
        //            e.printStackTrace();
        //        }

        return "login";
    }
    //    public void Testttt()
    //    {
    //        Equipment equipmentData = equipmentService.queryEquipmentByNetSerial(1,0);//////////////////////
    //        System.out.println("EqpId: "+equipmentData.getEqpId());
    //    }

    @RequestMapping("/loginSubmit")
    public String loginSubmit(HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException, ParseException {
        //从页面取信息
        String userId = request.getParameter("userId");
        //        System.out.println(userId);
        String password = request.getParameter("password");
        //        System.out.println(password);
        //角色判断与登录，按照管理员、用户、监测对象的顺序逐个判断
        Admin admin = adminService.adminLogin(userId, password);

        if (admin != null) {//管理员登录
            //将管理员登录操作写入Log
            AdminLog adminLog = new AdminLog();
            adminLog.setAdminId(admin.getAdminId());
            adminLog.setLogType("login");
//            String writeTime = dateformat.format(new Date());
//            String writeTime = dateformat.format((System.currentTimeMillis()+50400000));//CST时间加上14小时即50400000毫秒为北京时间，后来设置时区为上海，就不用了
            String writeTime = dateformat.format(System.currentTimeMillis());
            adminLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
            int adminLoginLogReturn = adminLogService.insertAdminLog(adminLog);
//            System.out.println(adminLoginLogReturn);

            request.setAttribute("admin", admin);
            //设置Session
            HttpSession session = request.getSession();
            session.setAttribute("admin", admin);
//            //检验Session是否取到值
//            Admin adminSession = (Admin)session.getAttribute("admin");
//            System.out.println(adminSession.getAdminId());
//            System.out.println(adminSession.getAdminGroup());

            //设置主页图片
            List<MainPic> mainPicList = mainPicService.queryAllMainPic();
//            System.out.println(mainPicList.get(0).getPicUrl());
//            System.out.println(mainPicList.get(0).getNewsId());
            request.setAttribute("mainPicList", mainPicList);

            //设置主页分类新闻列表
            List<News> healthNewsList = newsService.queryNewsByNewsTypeTop5("健康阅读");
            List<News> latestNewsList = newsService.queryNewsByNewsTypeTop5("最新消息");
            List<News> hotPointNewsList = newsService.queryNewsByNewsTypeTop5("热点新闻");
            request.setAttribute("healthNewsList", healthNewsList);
            request.setAttribute("latestNewsList", latestNewsList);
            request.setAttribute("hotPointNewsList", hotPointNewsList);

            return "indexAdmin";
        } else {
            User user = userService.userLogin(userId, password);
            if (user != null) { //用户登录
                //将用户登录操作写入Log
                UserLog userLog = new UserLog();
                userLog.setUserId(user.getUserId());
                userLog.setLogType("login");
                String writeTime = dateformat.format(System.currentTimeMillis());
                userLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                int userLoginLogReturn = userLogService.insertUserLog(userLog);

                //查看本月资源用量条目是否建立，未建立则新建
                String date = dateformat.format(System.currentTimeMillis());
                String yearMonth = date.substring(0,7);//如2019-03
                String year = date.substring(0,4);
                String month = date.substring(5,7);
                ObjectResourceUse objectResourceUse = objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(userId,yearMonth);
                if (objectResourceUse == null) {
                    ObjectResourceUse newObjectResourceUse = new ObjectResourceUse();
                    newObjectResourceUse.setObjectId(userId);
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
                        System.out.println("LoginController: resourceUse endDate month error...");
                    }
                    newObjectResourceUse.setEndDate(endDate);
                    newObjectResourceUse.setMsgCount(0);
                    newObjectResourceUse.setOnlineTimeLength(0);
                    newObjectResourceUse.setDataCount(0);
                    objectResouceUseService.insertObjectResourceUse(newObjectResourceUse);
                }
                else {
                    System.out.println("LoginController: resourceUse already exist...");
                }

                request.setAttribute("user", user);
                request.setAttribute("userName", user.getUserName());
                //设置Session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
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
            } else {
                Object object = objectService.objectLogin(userId, password);
                if (object != null) {//监测对象登录
                    //将监测对象登录操作写入Log
                    ObjectLog objectLog = new ObjectLog();
                    objectLog.setObjectId(object.getObjectId());
                    objectLog.setLogType("login");
//                    objectLog.setDetail("");
//                    String writeTime = dateformat.format((System.currentTimeMillis()+50400000));
                    String writeTime = dateformat.format(System.currentTimeMillis());
                    objectLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                    int objectLoginLogReturn = objectLogService.insertObjectLog(objectLog);

                    //查看本月资源用量条目是否建立，未建立则新建
                    String date = dateformat.format(System.currentTimeMillis());
                    String yearMonth = date.substring(0,7);//如2019-03
                    String year = date.substring(0,4);
                    String month = date.substring(5,7);
                    ObjectResourceUse objectResourceUse = objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(object.getObjectId(),yearMonth);
                    if (objectResourceUse == null) {
                        ObjectResourceUse newObjectResourceUse = new ObjectResourceUse();
                        newObjectResourceUse.setObjectId(userId);
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
                            System.out.println("LoginController: resourceUse endDate month error...");
                        }
                        newObjectResourceUse.setEndDate(endDate);
                        newObjectResourceUse.setMsgCount(0);
                        newObjectResourceUse.setOnlineTimeLength(0);
                        newObjectResourceUse.setDataCount(0);
                        objectResouceUseService.insertObjectResourceUse(newObjectResourceUse);
                    }
                    else {
                        System.out.println("LoginController: resourceUse already exist...");
                    }

                    request.setAttribute("object", object);
                    request.setAttribute("objectName", object.getObjectName());
                    //设置Session
                    HttpSession session = request.getSession();
                    session.setAttribute("object", object);
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
                } else {//登录失败提示
                    response.setContentType("text/html;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("<script language=\"javascript\">alert('登录失败,账号不存在或者密码错误！');</script>");
                    return "login";
                }
            }
        }
    }
}
