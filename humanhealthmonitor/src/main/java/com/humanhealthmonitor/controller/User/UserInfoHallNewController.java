package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.InfluxDBConnector;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.service.*;
import com.humanhealthmonitor.MsgQueue;
import com.humanhealthmonitor.pojo.Equipment;
import com.humanhealthmonitor.pojo.User;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserInfoHallNewController {
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectService objectService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private AlarmNormalValueService alarmNormalValueService;
    @Autowired
    private AlarmSpecialValueService alarmSpecialValueService;
    @Autowired
    private EquipmentTypeService equipmentTypeService;
    @Autowired
    private AlarmLogService alarmLogService;

    private InfluxDBConnector influxDBConnector;//创建influxDB连接实例

    private String objectSelectedIdS;
    private ArrayList<String> temperature01TimeStampList = new ArrayList<>();
    private ArrayList<String> bloodPressure01TimeStampList = new ArrayList<>();
    private ArrayList<String> bloodOxygen01TimeStampList = new ArrayList<>();
    //监测中心-实时信息
    @RequestMapping("/infoHallOnTimeNew")
    public String infoHallOnTime(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从数据库中获取所有该用户关联的监测对象并传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);

        //获取第一个对象的id并把他所绑定的监测设备信息传到前台
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(objectList.get(0).getObjectId());
        request.setAttribute("equipmentList", equipmentList);
        request.setAttribute("objectNameSelected", objectList.get(0).getObjectName());

        String order = "FEFE020404AABB";
        sendMessage(1, order);

        //判断监测对象有没有设备
        if (equipmentList.size() != 0) { //如果有设备

            //准备列表
            ArrayList<Double> bodyTempList = new ArrayList<>();
            ArrayList<Double> envTempList = new ArrayList<>();
            ArrayList<Double> highPressureList = new ArrayList<>();
            ArrayList<Double> lowPressureList = new ArrayList<>();
            ArrayList<Double> heartRateList = new ArrayList<>();
            ArrayList<Double> spo2List = new ArrayList<>();

            int flagTemperature01 = 0;
            String temperature01Order="";
            int netMaskIdTemperature01=0;
            int flagBloodPressure01 = 0;
            String bloodPressure01Order="";
            int netMaskIdBloodPressure01=0;
            int flagBloodOxygen01 = 0;
            String bloodOxygen01Order="";
            int netMaskIdBloodOxygen01=0;

            int netMaskIdTemp;
            int deviceSerialTemp;
            int checkCal;


            for (int i = 0;i < equipmentList.size();i++) {
                if (equipmentList.get(i).getEqpType().equals("Temperature01")) {
                    flagTemperature01 = 1;
                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    temperature01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdTemperature01 = netMaskIdTemp;
                    System.out.println("UserInfoHallController: temperature01Order: "+temperature01Order);////
                }
                else if(equipmentList.get(i).getEqpType().equals("BloodPressure01")) {
                    flagBloodPressure01 = 1;
                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    bloodPressure01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdBloodPressure01 = netMaskIdTemp;
                    System.out.println("UserInfoHallController: bloodPressure01Order: "+bloodPressure01Order);
                }
                else if(equipmentList.get(i).getEqpType().equals("BloodOxygen01")) {
                    flagBloodOxygen01 = 1;
                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    bloodOxygen01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdBloodOxygen01 = netMaskIdTemp;
                    System.out.println("UserInfoHallController: bloodOxygen01Order: "+bloodOxygen01Order);////
                }
            }

            System.out.println("UserInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+" flagBloodPressure01: "+flagBloodPressure01+" flagTemperature01: "+flagTemperature01);

            List<Equipment> noEquipmentList = new ArrayList<>();
            Equipment noNewEquipment = new Equipment();
            //连接InfluxDB
            influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
            influxDBConnector.connectToDatabase();
            if (flagTemperature01 == 1) {
                sendMessage(1, order);

                for (int i = 0;i < 10;i++) {
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;

                QueryResult temperatureResults =  influxDBConnector.queryData("select last(bodyTemp),(envTemp) from temperature where objectId = 'hitwhob001' time > "+timestamp10);
                System.out.println("UserInfoHallController: temperatureResults: "+temperatureResults);
                if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }else {
                    double bodyTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取体温，Object转为两位小数
                    bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
                    double envTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取环境温度，Object转为两位小数
                    envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
                }
                System.out.println("UserInfoHallController: bodyTempList"+bodyTempList);
                System.out.println("UserInfoHallController: envTempList"+envTempList);
            } else {
                noNewEquipment.setEqpType("Temperature01");
                for (int i = 0;i < 11;i++) {
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }
                noEquipmentList.add(noNewEquipment);
            }
            if (flagBloodPressure01 == 1) {
                sendMessage(1, order);//added0526
                for (int i = 0;i < 10;i++) {
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                QueryResult bloodPressureResults =  influxDBConnector.queryData("select last(highPressure),(lowPressure),(heartRate) from bloodPressure where objectId = "
                        +"'"+objectList.get(0).getObjectId()+"'"+" and time > "+timestamp10);
                if(bloodPressureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }else {
                    double highPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取高压,get(0)是时间戳
                    highPressureList.add(highPressure);
                    double lowPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取低压
                    lowPressureList.add(lowPressure);
                    double heartRate = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(3);//获取心率
                    heartRateList.add(heartRate);
                }
                System.out.println("UserInfoHallController: highPressureList"+highPressureList+" lowPressureList"+lowPressureList+" heartRateList"+heartRateList);
            } else {
                noNewEquipment.setEqpType("BloodPressure01");
                for (int i = 0;i < 11;i++) {
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }
                noEquipmentList.add(noNewEquipment);
            }
            if (flagBloodOxygen01 == 1) {
                sendMessage(1, order);

                for (int i = 0;i < 10;i++) {
                    spo2List.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                QueryResult spo2Results =  influxDBConnector.queryData("select last(spo2) from bloodOxygen where objectId = "
                        +"'"+objectList.get(0).getObjectId()+"'"+" and time > "+timestamp10);
                if(spo2Results.getResults().get(0).getSeries() == null){//如果值为空,赋0
                    spo2List.add(0.0);
                }else {
                    double spo2 = (double)spo2Results.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取血氧饱和度,get(0)是时间戳
                    spo2List.add(spo2);
                }
                System.out.println("UserInfoHallController: spo2List: "+spo2List);
            }else {
                noNewEquipment.setEqpType("BloodOxygen01");
                for (int i = 0;i < 11;i++) {
                    spo2List.add(0.0);
                }
                noEquipmentList.add(noNewEquipment);
            }


            //设值
            request.setAttribute("chen", "yuyuan");
            request.setAttribute("bodyTempList",bodyTempList);
            request.setAttribute("envTempList",envTempList);
            request.setAttribute("highPressureList",highPressureList);
            request.setAttribute("lowPressureList",lowPressureList);
            request.setAttribute("heartRateList",heartRateList);
            request.setAttribute("spo2List",spo2List);
            request.setAttribute("noEquipmentList",noEquipmentList);
        }

        return "infoHallOnTimeNewList";
    }
    //监测中心-监测设备数据刷新//映射地址为要刷新的地址//AutoRefresh
    @RequestMapping("/infoHallOnTimeGetInfo/AutoRefreshNew")
    @ResponseBody
    public Map<String,ArrayList<Double>> monitorCenterAjaxTest(@RequestBody ArrayList<ArrayList<Double>> array) {

        System.out.println("<<<<<<<<<<AutoRefresh>>>>>>>>>>");
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(objectSelectedIdS);

        ArrayList<Double> bodyTempList = array.get(0);
        ArrayList<Double> envTempList = array.get(1);
        ArrayList<Double> highPressureList = array.get(2);
        ArrayList<Double> lowPressureList = array.get(3);
        ArrayList<Double> heartRateList = array.get(4);
        ArrayList<Double> spo2List = array.get(5);

        int flagTemperature01 = 0;
        String temperature01Order="";
        int netMaskIdTemperature01=0;
        int flagBloodPressure01 = 0;
        String bloodPressure01Order="";
        int netMaskIdBloodPressure01=0;
        int flagBloodOxygen01 = 0;
        String bloodOxygen01Order="";
        int netMaskIdBloodOxygen01=0;

        int netMaskIdTemp;
        int deviceSerialTemp;
        int checkCal;

        String order = "FEFE020404AABB";
        sendMessage(1, order);

        for (int i = 0;i < equipmentList.size();i++) {
            if(equipmentList.get(i).getEqpType().equals("BloodOxygen01")) {
                flagBloodOxygen01 = 1;
                netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                if(checkCalStr.length()==1)
                    checkCalStr = "0"+checkCalStr;
                bloodOxygen01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                        +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                netMaskIdBloodOxygen01 = netMaskIdTemp;
                System.out.println("UserInfoHallController: bloodOxygen01Order: "+bloodOxygen01Order);////
            }
            else if(equipmentList.get(i).getEqpType().equals("BloodPressure01")) {
                flagBloodPressure01 = 1;
                netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                if(checkCalStr.length()==1)
                    checkCalStr = "0"+checkCalStr;
                bloodPressure01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03" +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                netMaskIdBloodPressure01 = netMaskIdTemp;
                System.out.println("UserInfoHallController: bloodPressure01Order: "+bloodPressure01Order);
            }
            else if (equipmentList.get(i).getEqpType().equals("Temperature01")) {
                flagTemperature01 = 1;
                netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                if(checkCalStr.length()==1)
                    checkCalStr = "0"+checkCalStr;
                temperature01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03" +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                netMaskIdTemperature01 = netMaskIdTemp;
                System.out.println("UserInfoHallController: temperature01Order: "+temperature01Order);////
            }
        }
        System.out.println("UserInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+" flagBloodPressure01: "+flagBloodPressure01+" flagTemperature01: "+flagTemperature01);
        //连接InfluxDB
        influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
        influxDBConnector.connectToDatabase();

        if (flagTemperature01 == 1) {
            sendMessage(1, order);

            bodyTempList.remove(0);//去掉开头
            envTempList.remove(0);

            long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
            QueryResult temperatureResults =  influxDBConnector.queryData("select last(bodyTemp),(envTemp) from temperature where objectId = "+"'"
                    +objectSelectedIdS+"'"+" and time > "+timestamp10);
            if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
                bodyTempList.add(0.0);
                envTempList.add(0.0);
            }else {
                double bodyTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取体温，Object转为两位小数
                bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
                double envTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取环境温度，Object转为两位小数
                envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
            }
            System.out.println("UserInfoHallController: bodyTempList"+bodyTempList);
            System.out.println("UserInfoHallController: envTempList"+envTempList);
        }
        if (flagBloodPressure01 == 1) {
            sendMessage(1, order);

            highPressureList.remove(0);
            lowPressureList.remove(0);
            heartRateList.remove(0);

            long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
            QueryResult bloodPressureResults =  influxDBConnector.queryData("select last(highPressure),(lowPressure),(heartRate) from bloodPressure where objectId = "+"'"
                    +objectSelectedIdS+"'"+" and time > "+timestamp10);
            if(bloodPressureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
                highPressureList.add(0.0);
                lowPressureList.add(0.0);
                heartRateList.add(0.0);
            }else {
                double lowPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取低压
                lowPressureList.add(lowPressure);
                double heartRate = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(3);//获取心率
                heartRateList.add(heartRate);
                double highPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取高压
                highPressureList.add(highPressure);
//                System.out.println("UserInfoHallController: get new pressure");
            }
            System.out.println("UserInfoHallController: highPressureList"+highPressureList);
            System.out.println("UserInfoHallController: lowPressureList"+lowPressureList);
            System.out.println("UserInfoHallController: heartRateList"+heartRateList);
        }
        if (flagBloodOxygen01 == 1) {
            sendMessage(1, order);

            spo2List.remove(0);
            long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
            QueryResult spo2Results =  influxDBConnector.queryData("select last(spo2) from bloodOxygen where objectId = "+"'"
                    +objectSelectedIdS+"'"+" and time > "+timestamp10);
            if(spo2Results.getResults().get(0).getSeries() == null){//如果值为空,赋0
                spo2List.add(0.0);
            }else {
                double spo2 = (double)spo2Results.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取血氧饱和度,get(0)是时间戳
                spo2List.add(spo2);
            }
            System.out.println("UserInfoHallController: spo2List: "+spo2List);
        }
        //准备传值给前台
        Map<String,ArrayList<Double>> map = new HashMap<>();
        map.put("bodyTempList",bodyTempList);
        map.put("envTempList",envTempList);
        map.put("highPressureList",highPressureList);
        map.put("lowPressureList",lowPressureList);
        map.put("heartRateList",heartRateList);
        map.put("spo2List",spo2List);

        return map;
    }
    //监测中心-切换实时监测对象
    @RequestMapping("/infoHallOnTimeGetInfoNew")
    public String infoHallOnTimeGetInfo(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从数据库中获取所有该用户关联的监测对象并传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);

        //获取被选中的对象的id并把他所绑定的监测设备信息传到前台
        String objectIdSelected = request.getParameter("objectId");
        objectSelectedIdS = objectIdSelected;
//        request.setAttribute("objectIdSelected",objectIdSelected);
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(objectIdSelected);
        request.setAttribute("equipmentList", equipmentList);
        String objectNameSelected = request.getParameter("objectName");
//        System.out.println(objectNameSelected);
        request.setAttribute("objectNameSelected", objectNameSelected);
//        return "monitorCenter/infoHallOnTime::objectTable";

        String order = "FEFE020404AABB";
        sendMessage(1, order);

        //判断监测对象有没有设备
        if (equipmentList.size() != 0) {//如果有设备
//            int netMaskId = equipmentList.get(0).getNetmaskId();//comment0526
//            sendMsgQueue.get(netMaskId-1).offer("FEFE0401040005AABB");//comment0526
            //准备列表
            ArrayList<Double> bodyTempList = new ArrayList<>();
            ArrayList<Double> envTempList = new ArrayList<>();
            ArrayList<Double> highPressureList = new ArrayList<>();
            ArrayList<Double> lowPressureList = new ArrayList<>();
            ArrayList<Double> heartRateList = new ArrayList<>();
            ArrayList<Double> spo2List = new ArrayList<>();

            int flagTemperature01 = 0;
            String temperature01Order="";
            int netMaskIdTemperature01=0;
            int flagBloodPressure01 = 0;
            String bloodPressure01Order="";
            int netMaskIdBloodPressure01=0;
            int flagBloodOxygen01 = 0;
            String bloodOxygen01Order="";
            int netMaskIdBloodOxygen01=0;

            int netMaskIdTemp;
            int deviceSerialTemp;
            int checkCal;


            for (int i = 0;i < equipmentList.size();i++) {
                if (equipmentList.get(i).getEqpType().equals("Temperature01")) {
                    flagTemperature01 = 1;
                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    temperature01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdTemperature01 = netMaskIdTemp;
                    System.out.println("UserInfoHallController: temperature01Order: "+temperature01Order);////
                }
                else if(equipmentList.get(i).getEqpType().equals("BloodOxygen01")) {
                    flagBloodOxygen01 = 1;
                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    bloodOxygen01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03" +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdBloodOxygen01 = netMaskIdTemp;
                    System.out.println("UserInfoHallController: bloodOxygen01Order: "+bloodOxygen01Order);////
                }
                else if(equipmentList.get(i).getEqpType().equals("BloodPressure01")) {
                    flagBloodPressure01 = 1;
                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    bloodPressure01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03" +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdBloodPressure01 = netMaskIdTemp;
                    System.out.println("UserInfoHallController: bloodPressure01Order: "+bloodPressure01Order);
                }
            }
            System.out.println("UserInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+" flagBloodPressure01: "+flagBloodPressure01+" flagTemperature01: "+flagTemperature01);
            List<Equipment> noEquipmentList = new ArrayList<>();
            Equipment noNewEquipment = new Equipment();
            //连接InfluxDB
            influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
            influxDBConnector.connectToDatabase();
            if (flagTemperature01 == 1) {
                sendMessage(1, order);//added0526

                for (int i = 0;i < 10;i++) {
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                QueryResult temperatureResults =  influxDBConnector.queryData("select last(bodyTemp),(envTemp) from temperature where objectId = "+"'"
                        +objectIdSelected+"'"+" and time > "+timestamp10);
                if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }else {
                    double bodyTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取体温，Object转为两位小数
                    bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
                    double envTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取环境温度，Object转为两位小数
                    envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
                }
                System.out.println("UserInfoHallController: bodyTempList"+bodyTempList);
                System.out.println("UserInfoHallController: envTempList"+envTempList);
            } else {
                noNewEquipment.setEqpType("Temperature01");
                for (int i = 0;i < 11;i++) {
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }
                noEquipmentList.add(noNewEquipment);
            }
            if (flagBloodPressure01 == 1) {
                sendMessage(1, order);
                for (int i = 0;i < 10;i++) {
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                QueryResult bloodPressureResults =  influxDBConnector.queryData("select last(highPressure),(lowPressure),(heartRate) from bloodPressure where objectId = "+"'"
                        +objectIdSelected+"'"+" and time > "+timestamp10);
                if(bloodPressureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }else {
                    double lowPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取低压
                    lowPressureList.add(lowPressure);
                    double heartRate = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(3);//获取心率
                    heartRateList.add(heartRate);
                    double highPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取高压
                    highPressureList.add(highPressure);
                }
                System.out.println("UserInfoHallController: highPressureList"+highPressureList);
                System.out.println("UserInfoHallController: lowPressureList"+lowPressureList);
                System.out.println("UserInfoHallController: heartRateList"+heartRateList);
            } else {
                noNewEquipment.setEqpType("BloodPressure01");
                for (int i = 0;i < 11;i++) {
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }
                noEquipmentList.add(noNewEquipment);
            }
            if (flagBloodOxygen01 == 1) {
                sendMessage(1, order);
                for (int i = 0;i < 10;i++) {
                    spo2List.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                QueryResult spo2Results =  influxDBConnector.queryData("select last(spo2) from bloodOxygen where objectId = "+"'"
                        +objectIdSelected+"'"+" and time > "+timestamp10);
                if(spo2Results.getResults().get(0).getSeries() == null){//如果值为空,赋0
                    spo2List.add(0.0);
                }else {
                    double spo2 = (double)spo2Results.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取血氧饱和度,get(0)是时间戳
                    spo2List.add(spo2);
                }
                System.out.println("UserInfoHallController: spo2List: "+spo2List);
            }else {
                noNewEquipment.setEqpType("BloodOxygen01");
                for (int i = 0;i < 11;i++) {
                    spo2List.add(0.0);
                }
                noEquipmentList.add(noNewEquipment);
            }
            //设值
            request.setAttribute("bodyTempList",bodyTempList);
            request.setAttribute("envTempList",envTempList);
            request.setAttribute("highPressureList",highPressureList);
            request.setAttribute("lowPressureList",lowPressureList);
            request.setAttribute("heartRateList",heartRateList);
            request.setAttribute("spo2List",spo2List);
            request.setAttribute("noEquipmentList",noEquipmentList);
        }
        return "infoHallOnTimeNewList";
    }

    //向网关发送获取数据的命令
    public void sendMessage(int netMaskId,String order) {
        if (MsgQueue.protocolState[netMaskId-1] == 1 ) {
            MsgQueue.sendMsgQueue.get(netMaskId-1).offer(order);////added0524
        }
        else if(MsgQueue.protocolState[netMaskId-1] == 2) {
            MsgQueue.sendAMQPQueue.offer(netMaskId + order);
        }
        else {
            System.out.println("ObjInfoHallController: Cannot get info, the netMask owing the equipment is offline...");
        }
    }
}
