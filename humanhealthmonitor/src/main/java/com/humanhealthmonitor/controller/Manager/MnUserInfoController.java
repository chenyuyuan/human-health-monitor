package com.humanhealthmonitor.controller.Manager;

import com.humanhealthmonitor.InfluxDBConnector;
import com.humanhealthmonitor.pojo.Admin;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.pojo.ObjectResourceUse;
import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.service.*;
import com.humanhealthmonitor.service.*;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MnUserInfoController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectService objectService;
    @Autowired
    private AlarmLogService alarmLogService;
    @Autowired
    private ObjectResouceUseService objectResouceUseService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private InfluxDBConnector influxDBConnector;//创建influxDB连接实例

    //管理中心-用户群体数据
//    @RequestMapping("/manageCenter/userInfoHallGroupData")
    @RequestMapping("/userInfoHallGroupData")
    public String userInfoHallGroupData(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //获取12小时内分时警报短信量并存入alarmMessageCountList
        List<Integer> alarmMessageCountList = new ArrayList<>();
        Timestamp beginTime;
        Timestamp endTime;
        for (int i = 11; i >= 0;i--)
        {
            beginTime = java.sql.Timestamp.valueOf(dateformat.format((System.currentTimeMillis()-3600000*i-3600000)));
            endTime = java.sql.Timestamp.valueOf(dateformat.format((System.currentTimeMillis()-3600000*i)));
            alarmMessageCountList.add(alarmLogService.queryAlarmLogCountDuringCertainTime(beginTime,endTime));
//            alarmMessageCountList.add(i+2);//test
        }
        request.setAttribute("alarmMessageCountList",alarmMessageCountList);

        //获取当前在线人数（用户+监测对象）
        List<Integer> onlineCountList = new ArrayList<>();
        for (int i = 0; i < 11;i++)
        {
            onlineCountList.add(0);//初始全部设置为0
        }
        request.setAttribute("onlineCountList",onlineCountList);

        //时序数据传输量
        ArrayList<Integer> influxCountList = getInfluxCountList();
        request.setAttribute("influxCountList",influxCountList);

        return "manageCenter/userInfoHallGroupData";
    }

    //管理中心-用户群体数据-自动刷新数据
    @RequestMapping("/userInfoHallGroupData/AutoRefresh")
    @ResponseBody
    public Map<String,ArrayList<Integer>> userInfoHallGroupDataAjaxTest(@RequestBody ArrayList<ArrayList<Integer>> array) {
        System.out.println("MnUserInfoHallController: userInfoHallGroupData: AutoRefresh: array: "+array);
        //警报短信量更新
        ArrayList<Integer> alarmMessageCountList = getAlarmMessageCountList();
        //在线人数更新
        ArrayList<Integer> onlineCountList = array.get(1);
        onlineCountList.remove(0);
        onlineCountList.add(10,userService.queryAllOnlineUsersCount()+objectService.queryAllOnlineObjectCount());
        //时序数据量更新
        ArrayList<Integer> influxCountList = getInfluxCountList();

        Map<String,ArrayList<Integer>> map = new HashMap<>();
        map.put("alarmMessageCountList",alarmMessageCountList);
        map.put("onlineCountList",onlineCountList);
        map.put("influxCountList",influxCountList);

        return map;
    }

    //获取警报短信量分时数据
    public ArrayList<Integer> getAlarmMessageCountList() {
        ArrayList<Integer> alarmMessageCountList = new ArrayList<>();
        Timestamp beginTime;
        Timestamp endTime;
        for (int i = 11; i >= 0;i--) {
            beginTime = java.sql.Timestamp.valueOf(dateformat.format((System.currentTimeMillis()-3600000*i-3600000)));
            endTime = java.sql.Timestamp.valueOf(dateformat.format((System.currentTimeMillis()-3600000*i)));
            alarmMessageCountList.add(alarmLogService.queryAlarmLogCountDuringCertainTime(beginTime,endTime));
        }
        return alarmMessageCountList;
    }

    //获取influxdb分时数据量
    public ArrayList<Integer> getInfluxCountList() {
        //连接InfluxDB
        influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
        influxDBConnector.connectToDatabase();
        //时序数据传输量
        ArrayList<Integer> influxCountList = new ArrayList<>();
        String startTimeString;
        String endTimeString;
        for (int i = 11; i >= 0;i--) {
//            QueryResult resultsTest =  influxDBConnector.queryData("select count(spo2) from bloodOxygen");
//            System.out.println("test127: "+resultsTest);
//            startTimeString = String.valueOf((System.currentTimeMillis()-3600000*(8+1+i))*1000000);//目前系统时间是北京时间，而influxDB的time字段是格林威治时间，相差8小时
//            endTimeString = String.valueOf((System.currentTimeMillis()-3600000*(8+i))*1000000);
            startTimeString = String.valueOf((System.currentTimeMillis()-3600000*(1+i))*1000000);
            endTimeString = String.valueOf((System.currentTimeMillis()-3600000*(i))*1000000);
//            System.out.println((System.currentTimeMillis()-3600000));
            QueryResult results =  influxDBConnector.queryData("select count(spo2) from bloodOxygen where time > "
                    +startTimeString+" and time < "+endTimeString);
            System.out.println("MnUserInfoHallController: getInfluxCountList results: "+results);
            QueryResult results2 =  influxDBConnector.queryData("select count(heartRate) from bloodPressure where time > "
                    +startTimeString+" and time < "+endTimeString);
            System.out.println("MnUserInfoHallController: getInfluxCountList results: "+results);
            QueryResult results3 =  influxDBConnector.queryData("select count(bodyTemp) from temperature where time > "
                    +startTimeString+" and time < "+endTimeString);
            System.out.println("MnUserInfoHallController: getInfluxCountList results: "+results);
            int count = 0;
            if(results.getResults().get(0).getSeries() == null){
//                influxCountList.add(0);
                count += 0;
            }else {
                count += (int)(double)results.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//Object强转为int，原本是小数，转为int后舍弃小数
//                influxCountList.add(count);
                System.out.println("MnUserInfoHallController: count+"+count);
            }
            if(results2.getResults().get(0).getSeries() == null){
                count += 0;
            } else {
                count += (int)(double)results2.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//Object强转为int，原本是小数，转为int后舍弃小数
                System.out.println("MnUserInfoHallController: count+"+count);
            }
            if(results3.getResults().get(0).getSeries() == null){
                count += 0;
            } else {
                count += (int)(double)results3.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//Object强转为int，原本是小数，转为int后舍弃小数
                System.out.println("MnUserInfoHallController: count+"+count);
            }
            influxCountList.add(count);
//            influxCountList.add(0);//初始全部设置为0//test
        }
        return influxCountList;
    }





//    @RequestMapping("/manageCenter")
//    public String globalRefresh(HttpServletRequest request, HttpServletResponse response) {
//        List<Map<String,String>> lists = new ArrayList<>();
//        Map<String,String> map = new HashMap<>();
//        map.put("author", "曹雪芹");
//        map.put("title", "《红楼梦》");
//        map.put("url", "www.baidu.com");
//        lists.add(map);
//
//        // 用作对照
//        request.setAttribute("refresh", "我不会被刷新");
//
//        request.setAttribute("title", "我的书单");
//        request.setAttribute("books", lists);
//        return "manageCenter/test";
//    }
//
//    /**
//     * 局部刷新，注意返回值
//     * @param model
//     * @return
//     */
//    @RequestMapping("/manageCenter/local")
//    public String localRefresh(HttpServletRequest request, HttpServletResponse response) {
//        List<Map<String,String>> lists = new ArrayList<>();
//        Map<String,String> map = new HashMap<>();
//        map.put("author", "罗贯中");
//        map.put("title", "《三国演义》");
//        map.put("url", "www.qq.com");
//        lists.add(map);
//
//        request.setAttribute("title", "我的书单");
//        request.setAttribute("books", lists);
//        // "test"是test.html的名，
//        // "table_refresh"是test.html中需要刷新的部分标志,
//        // 在标签里加入：th:fragment="table_refresh"
//        System.out.println("refreshed");
//        return "manageCenter/test::table_refresh";
////        return "manageCenter/userInfoHallGroupData::table_refresh";
//    }
//
//    @RequestMapping("/manageCenter/local2")
//    public String localRefresh2(HttpServletRequest request, HttpServletResponse response) {
//        List<Map<String,String>> lists = new ArrayList<>();
//        Map<String,String> map = new HashMap<>();
//        map.put("author", "罗贯中");
//        map.put("title", "《三国演义》");
//        map.put("url", "www.qq.com");
//        lists.add(map);
//
//        request.setAttribute("title", "我的书单");
//        request.setAttribute("books", lists);
//        // "test"是test.html的名，
//        // "table_refresh"是test.html中需要刷新的部分标志,
//        // 在标签里加入：th:fragment="table_refresh"
//        System.out.println("refreshed2");
//        return "manageCenter/test::alarmMessage";
////        return "manageCenter/userInfoHallGroupData::table_refresh";
//    }
    //管理中心-用户详细数据-搜索用户
    @RequestMapping("/manageCenter/userInfoHallSingleSearch")
    public String userInfoHallSingleSearch(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //获取用户数据传到页面
        List<User> userList = userService.queryAllUser();
        request.setAttribute("userList", userList);

        return "manageCenter/userInfoHallSingleSearch";
    }

    //管理中心-用户详细数据-搜索结果
    @RequestMapping("/manageCenter/userInfoHallSingleSearchResult")
    public String userInfoHallSingleSearchResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面获取关键字，在数据库中查找匹配关键字的相应用户
        String searchKey = request.getParameter("searchKey");
//        System.out.println(searchKey);
        List<User> userList = userService.queryUserByKey(searchKey, searchKey);
        if (userList.size() == 0) {//如果没有查到关键字相关的用户，给出提示
//            System.out.println("Line58");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('未查找到相关用户')</script>");
        } else {//查到了符合关键字的用户，把列表传到页面
//            System.out.println("Line65");
//            System.out.println(userList.get(0).getUserId());
            request.setAttribute("userList", userList);
        }

        return "manageCenter/userInfoHallSingleSearch";
    }

    //管理中心-用户详细数据-个人详情查看
    @RequestMapping("/manageCenter/userInfoHallSingleData")
    public String userInfoHallSingleData(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        String userId = request.getParameter("userId");//get到的是input的name属性
//        System.out.println(userId);
        User user = userService.queryUserByUserId(userId);
        request.setAttribute("user", user);
        //获取前12个月用户关联对象的资源用量总和
        String date = dateformat.format(System.currentTimeMillis());
//        String yearMonth = date.substring(0,7);//如2019-03
        String year = date.substring(0,4);
        String month = date.substring(5,7);
        if (!month.equals("12")) {//如果不是12月，年份-1，月份+1，为起始查询年月
            year = String.valueOf(Integer.valueOf(year)-1);
            month = String.format("%02d",Integer.valueOf(month)+1);//至少两位，不足补0
        }
        else {//如果现在是12月份，那么从1月开始取
            month = "01";
        }
//        System.out.println("start year: "+year+" month: "+month);
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        ArrayList<Integer> alarmMessageCountList = new ArrayList<>();
        ArrayList<Integer> onlineTimeCountList = new ArrayList<>();
        ArrayList<Integer> influxCountList = new ArrayList<>();
        ArrayList<String> yearMonthList = new ArrayList<>();
        ObjectResourceUse objectResourceUse;
        for (int i = 0; i < 12;i++) {
            alarmMessageCountList.add(0);
            onlineTimeCountList.add(0);
            influxCountList.add(0);
            yearMonthList.add(year+"-"+month);
            for (int j = 0;j < objectList.size(); j++) {
                objectResourceUse = objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(objectList.get(j).getObjectId(),year+"-"+month);
                if (objectResourceUse != null) {
                    alarmMessageCountList.set(i,alarmMessageCountList.get(i) + objectResourceUse.getMsgCount());
                    onlineTimeCountList.set(i,onlineTimeCountList.get(i) + objectResourceUse.getOnlineTimeLength());
                    influxCountList.set(i,influxCountList.get(i) + objectResourceUse.getDataCount());
                }
            }
            if(!month.equals("12")) {
                month = String.format("%02d",Integer.valueOf(month)+1);
            }
            else {//如果即将到达下一年，年份+1，月份从01开始
                month = "01";
                year = String.valueOf(Integer.valueOf(year)+1);
            }
        }
//        System.out.println(alarmMessageCountList);
//        System.out.println(onlineTimeCountList);
//        System.out.println(influxCountList);
//        System.out.println(yearMonthList);
        request.setAttribute("alarmMessageCountList",alarmMessageCountList);
        request.setAttribute("onlineTimeCountList",onlineTimeCountList);
        request.setAttribute("influxCountList",influxCountList);
        String tmpStr;
        for (int i = 0;i < yearMonthList.size();i++) {
            tmpStr = yearMonthList.get(i);
            tmpStr = tmpStr.substring(0,4)+"."+tmpStr.substring(5,7);
            yearMonthList.set(i,tmpStr);
        }
        System.out.println("MnUserInfoHallController: after: "+yearMonthList);
        request.setAttribute("yearMonthList",yearMonthList);

        return "manageCenter/userInfoHallSingleData";
    }
}
