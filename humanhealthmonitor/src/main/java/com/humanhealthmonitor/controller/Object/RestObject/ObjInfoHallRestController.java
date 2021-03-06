package com.humanhealthmonitor.controller.Object.RestObject;


import com.alibaba.fastjson.JSONObject;
import com.humanhealthmonitor.MsgQueue;
import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/rest")
public class ObjInfoHallRestController {
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private EquipmentTypeService equipmentTypeService;
    @Autowired
    private AlarmNormalValueService alarmNormalValueService;
    @Autowired
    private AlarmSpecialValueService alarmSpecialValueService;
    @Autowired
    private ObjectService objectService;
    @Autowired
    private UserNetmaskService userNetmaskService;
    @Autowired
    private DataService dataService;


    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @CrossOrigin
    @RequestMapping(value = "/monitorCenter/objsearchHistoryData", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap objsearchHistoryData(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response)
            throws IOException, NullPointerException, InterruptedException {
        System.out.println("[UserEquipmentRestController]:");
        //String content = params.getString("content");
        HashMap res = new HashMap();

        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);



        String objectId = object.getObjectId();



        String startTimeParam = params.getString("startTime");
        String endTimeParam = params.getString("endTime");

        String timestampStart = dateTimeLocalToTimeStampString(startTimeParam).substring(0,10);
        String timestampEnd = dateTimeLocalToTimeStampString(endTimeParam).substring(0,10);

        SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
        String timeinformatStart = format.format(Long.parseLong(timestampStart + "000"));
        String timeinformatEnd = format.format(Long.parseLong(timestampEnd + "000"));


        System.out.println("<<<<searchHistoryData>>>>"+objectId+" "+timestampStart+" "+timestampEnd);
        System.out.println("<<<<searchHistoryData>>>>"+objectId+" "+timeinformatStart+" "+timeinformatEnd);

        ArrayList<Temperature> temperatureArrayList = dataService.queryTemperatureLimitTime(objectId,timeinformatStart,timeinformatEnd);
        ArrayList<BloodOxygen> bloodOxygenArrayList = dataService.queryBloodOxygenLimitTime(objectId,timeinformatStart,timeinformatEnd);
        ArrayList<BloodPressure> bloodPressureArrayList = dataService.queryBloodPressureLimitTime(objectId,timeinformatStart,timeinformatEnd);
        ArrayList<Mattress> mattressArrayList = dataService.queryMattressLimitTime(objectId,timeinformatStart,timeinformatEnd);

        ArrayList<Double> bodyTempList = new ArrayList<>();
        ArrayList<Double> envTempList = new ArrayList<>();
        ArrayList<String> tempTimeList = new ArrayList<>();

        ArrayList<Double> lowPressureList = new ArrayList<>();
        ArrayList<Double> highPressureList = new ArrayList<>();
        ArrayList<Double> heartRateList = new ArrayList<>();
        ArrayList<String> bloodPressureTimeList = new ArrayList<>();

        ArrayList<Double> spo2List = new ArrayList<>();
        ArrayList<String> bloodOxygenTimeList = new ArrayList<>();

        ArrayList<Double> breathList=new ArrayList<>();
        ArrayList<Double> actList = new ArrayList<>();
        ArrayList<String> mattressTimeList=new ArrayList<>();

        for (int i=0;i<temperatureArrayList.size();++i) {
            //Double.valueOf(String.format("%.2f",bodyTemp))
            bodyTempList.add(Double.valueOf(String.format("%.2f",(double)temperatureArrayList.get(i).getBodyTemp())));
            envTempList.add(Double.valueOf(String.format("%.2f",(double)temperatureArrayList.get(i).getEnvTemp())));
            tempTimeList.add(temperatureArrayList.get(i).getTime());
        }
        for (int i=0;i<mattressArrayList.size();++i) {
            breathList.add((double)mattressArrayList.get(i).getBreath());
            actList.add((double)mattressArrayList.get(i).getAct());
            mattressTimeList.add(mattressArrayList.get(i).getTime());
        }
        for (int i=0;i<bloodPressureArrayList.size();++i) {
            highPressureList.add((double)bloodPressureArrayList.get(i).getHighPressure());
            lowPressureList.add((double)bloodPressureArrayList.get(i).getLowPressure());
            heartRateList.add((double)bloodPressureArrayList.get(i).getHeartRate());
            bloodPressureTimeList.add(bloodPressureArrayList.get(i).getTime());
        }
        for (int i=0;i< bloodOxygenArrayList.size();++i) {
            spo2List.add((double)bloodOxygenArrayList.get(i).getSpo2());
            bloodOxygenTimeList.add(bloodOxygenArrayList.get(i).getTime());
        }


        res.putIfAbsent("msg", "success");
        res.putIfAbsent("temperatureList", temperatureArrayList);
        res.putIfAbsent("bloodOxygenList", bloodOxygenArrayList);
        res.putIfAbsent("bloodPressureList", bloodPressureArrayList);
        res.putIfAbsent("mattressList", mattressArrayList);

        res.putIfAbsent("bodyTempList", bodyTempList);
        res.putIfAbsent("envTempList", envTempList);
        res.putIfAbsent("tempTimeList", tempTimeList);

        res.putIfAbsent("lowPressureList", lowPressureList);
        res.putIfAbsent("highPressureList", highPressureList);
        res.putIfAbsent("heartRateList", heartRateList);
        res.putIfAbsent("bloodPressureTimeList", bloodPressureTimeList);

        res.putIfAbsent("spo2List", spo2List);
        res.putIfAbsent("bloodOxygenTimeList", bloodOxygenTimeList);

        res.putIfAbsent("breathList", breathList);
        res.putIfAbsent("actList", actList);
        res.putIfAbsent("mattressTimeList", mattressTimeList);

        return res;
    }


    public String dateTimeLocalToTimeStampString(String dateTimeLocal) {
        dateTimeLocal = dateTimeLocal.replace("T"," ");
        dateTimeLocal = dateTimeLocal+":00";//加上秒值00
        LocalDateTime localDateTimeStart = LocalDateTime.parse(dateTimeLocal,df);
        long timestamp = localDateTimeStart.toInstant(ZoneOffset.of("+8")).toEpochMilli();//时区东8区
        return String.valueOf(timestamp*1000000);
    }

    //向网关发送获取数据的命令
    public void sendMessage(int netMaskId,String order) {
        if (MsgQueue.protocolState[netMaskId - 1] == 1 ) {
            MsgQueue.sendMsgQueue.get(netMaskId - 1).offer(order);////added0524
        } else if (MsgQueue.protocolState[netMaskId - 1] == 2) {
            MsgQueue.sendAMQPQueue.offer(netMaskId + order);
        } else {
            System.out.println("ObjInfoHallController: Cannot get info, the netMask owing the equipment is offline...");
        }
    }
}
