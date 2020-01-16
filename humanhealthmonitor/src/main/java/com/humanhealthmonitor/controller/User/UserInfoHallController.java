package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.InfluxDBConnector;
import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.service.*;
import com.humanhealthmonitor.MsgQueue;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class
UserInfoHallController {
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
    @Autowired
    private UserNetmaskService userNetmaskService;
    @Autowired
    private DataService dataService;



    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private InfluxDBConnector influxDBConnector;//创建influxDB连接实例

//    private static String objectSelectedIdS;//static为所有本类对象共享，不宜使用
//    private static ArrayList<String> temperature01TimeStampList = new ArrayList<>();
//    private static ArrayList<String> bloodPressure01TimeStampList = new ArrayList<>();
//    private static ArrayList<String> bloodOxygen01TimeStampList = new ArrayList<>();
    private String objectSelectedIdS;
    private ArrayList<String> temperature01TimeStampList = new ArrayList<>();
    private ArrayList<String> bloodPressure01TimeStampList = new ArrayList<>();
    private ArrayList<String> bloodOxygen01TimeStampList = new ArrayList<>();
    //监测中心-实时信息
//    @RequestMapping("/infoHallOnTime")
//    public String infoHallOnTime(HttpServletRequest request, HttpServletResponse response) {
//        User user = (User) request.getSession().getAttribute("user");
//        request.setAttribute("user", user);
//
//        //从数据库中获取所有该用户关联的监测对象并传到前台
//        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
//        request.setAttribute("objectList", objectList);
//
//        //获取第一个对象的id并把他所绑定的监测设备信息传到前台
//        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(objectList.get(0).getObjectId());
//        request.setAttribute("equipmentList", equipmentList);
//        request.setAttribute("objectNameSelected", objectList.get(0).getObjectName());
//
//        String netmaskIdStr=userNetmaskService.queryUserRelatedNetmask(user.getUserId());
//        int netmask=Integer.parseInt(netmaskIdStr);
//
//        String order = "FEFE020404AABB";
//        sendMessage(netmask, order);
//
//        //判断监测对象有没有设备
//        if (equipmentList.size() != 0) { //如果有设备
////            int netMaskId = equipmentList.get(0).getNetmaskId();//added0524
////            sendMsgQueue.get(netMaskId-1).offer("FEFE0401040005AABB");/////////////////////////////////////
//
////            sendMsgQueue.offer("FEFE0401030004AABB");//xueya
////            sendMsgQueue.offer("FEFE0401030105AABB");//wendu
////            sendMsgQueue.offer("FEFE0401030206AABB");//xueyang
//            //准备列表
//            ArrayList<Double> bodyTempList = new ArrayList<>();
//            ArrayList<Double> envTempList = new ArrayList<>();
//            ArrayList<Double> highPressureList = new ArrayList<>();
//            ArrayList<Double> lowPressureList = new ArrayList<>();
//            ArrayList<Double> heartRateList = new ArrayList<>();
//            ArrayList<Double> spo2List = new ArrayList<>();
//            //
//            ArrayList<Double> breathList = new ArrayList<>();
//            ArrayList<Double> actList = new ArrayList<>();
//
//
//            int flagTemperature01 = 0;
//            String temperature01Order="";
//            int netMaskIdTemperature01=0;
//            int flagBloodPressure01 = 0;
//            String bloodPressure01Order="";
//            int netMaskIdBloodPressure01=0;
//            int flagBloodOxygen01 = 0;
//            String bloodOxygen01Order="";
//            int netMaskIdBloodOxygen01=0;
//            //
//            int flagMattress01 = 0;
//            String mattress01Order="";
//            int netMaskIdMattress01=0;
//
//
//            int netMaskIdTemp;
//            int deviceSerialTemp;
//            int checkCal;
//
//
//            for (int i = 0;i < equipmentList.size();i++) {
//                if (equipmentList.get(i).getEqpType().equals("Temperature01")) {
//                    flagTemperature01 = 1;
//                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
//                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
//                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
//                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
//                    if(checkCalStr.length()==1)
//                        checkCalStr = "0"+checkCalStr;
//                    temperature01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
//                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
//                    netMaskIdTemperature01 = netMaskIdTemp;
//                    System.out.println("UserInfoHallController: temperature01Order: "+temperature01Order);////
//                }
//                else if(equipmentList.get(i).getEqpType().equals("BloodPressure01")) {
//                    flagBloodPressure01 = 1;
//                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
//                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
//                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
//                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
//                    if(checkCalStr.length()==1)
//                        checkCalStr = "0"+checkCalStr;
//                    bloodPressure01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
//                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
//                    netMaskIdBloodPressure01 = netMaskIdTemp;
//                    System.out.println("UserInfoHallController: bloodPressure01Order: "+bloodPressure01Order);
//                }
//                else if(equipmentList.get(i).getEqpType().equals("BloodOxygen01")) {
//                    flagBloodOxygen01 = 1;
//                    netMaskIdTemp = equipmentList.get(i).getNetmaskId();
//                    deviceSerialTemp = equipmentList.get(i).getDeviceSerial();
//                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
//                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
//                    if(checkCalStr.length()==1)
//                        checkCalStr = "0"+checkCalStr;
//                    bloodOxygen01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
//                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
//                    netMaskIdBloodOxygen01 = netMaskIdTemp;
//                    System.out.println("UserInfoHallController: bloodOxygen01Order: "+bloodOxygen01Order);////
//                }
//                else if(equipmentList.get(i).getEqpType().equals("Mattress01")){
//                    flagMattress01 = 1;
//                }
//            }
//
//            System.out.println("UserInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+" flagBloodPressure01: "+flagBloodPressure01+" flagTemperature01: "+flagTemperature01);
//
//
//
//            List<Equipment> noEquipmentList = new ArrayList<>();
//            Equipment noNewEquipment = new Equipment();
//            //连接InfluxDB
//            influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
//            influxDBConnector.connectToDatabase();
//            if (flagTemperature01 == 1) {
//                sendMessage(netmask, order);
//
//                for (int i = 0;i < 10;i++) {
//                    bodyTempList.add(0.0);
//                    envTempList.add(0.0);
//                }
//                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
////                QueryResult temperatureResults =  influxDBConnector.queryData("select last(bodyTemp),(envTemp) from temperature where objectId = "
////                        +"'"+objectList.get(0).getObjectId()+"'"+" and time > "+timestamp10);
//
//                //QueryResult temperatureResults =  influxDBConnector.queryData("select last(bodyTemp),(envTemp) from temperature where objectId = 'hitwhob001' time > "+timestamp10);
//
//                System.out.println("<<UserInfoHallController:InfoHallOnTime>>:");
//
//                long timestamp = System.currentTimeMillis() / 1000 - 20;
//                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
//                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));
//
//
//                ArrayList<Temperature> temperatureArrayList = dataService.queryTemperature(objectSelectedIdS,1,limitTimeinformat);
//
//                if(temperatureArrayList == null || temperatureArrayList.size()==0){
//                    bodyTempList.add(0.0);
//                    envTempList.add(0.0);
//                }
//                else {
//                    double bodyTemp = temperatureArrayList.get(0).getBodyTemp();
//                    bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
//                    double envTemp = temperatureArrayList.get(0).getEnvTemp();
//                    envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
//                    System.out.println("<体温和环境温度UserInfoHallController>" + bodyTemp + " " +envTemp);
//
//                }
//
//
//
//
//
////                if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
//////                    System.out.println("UserInfoHallController: temperatureResults null 空");
////                    bodyTempList.add(0.0);
////                    envTempList.add(0.0);
////                }else {
////                    double bodyTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取体温，Object转为两位小数
////                    bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
////                    double envTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取环境温度，Object转为两位小数
////                    envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
////                }
//                System.out.println("UserInfoHallController: bodyTempList"+bodyTempList);
//                System.out.println("UserInfoHallController: envTempList"+envTempList);
//            } else {
//                noNewEquipment.setEqpType("Temperature01");
//                for (int i = 0;i < 11;i++) {
//                    bodyTempList.add(0.0);
//                    envTempList.add(0.0);
//                }
//                noEquipmentList.add(noNewEquipment);
//            }
//            if (flagBloodPressure01 == 1) {
//                sendMessage(netmask, order);//added0526
//                for (int i = 0;i < 10;i++) {
//                    highPressureList.add(0.0);
//                    lowPressureList.add(0.0);
//                    heartRateList.add(0.0);
//                }
//                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
//
//                long timestamp = System.currentTimeMillis() / 1000 - 10;
//                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
//                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));
//
//                ArrayList<BloodPressure> bloodPressureArrayList = dataService.queryBloodPressure(objectSelectedIdS,1,limitTimeinformat);
//
//                if(bloodPressureArrayList == null||bloodPressureArrayList.size()==0){//如果值为空,全部赋0
//                    highPressureList.add(0.0);
//                    lowPressureList.add(0.0);
//                    heartRateList.add(0.0);
//                }else {
//                    double lowPressure = bloodPressureArrayList.get(0).getLowPressure();
//                    lowPressureList.add(lowPressure);
//                    double heartRate = bloodPressureArrayList.get(0).getHeartRate();
//                    heartRateList.add(heartRate);
//                    double highPressure = bloodPressureArrayList.get(0).getHighPressure();
//                    highPressureList.add(highPressure);
//                }
//
//                System.out.println("UserInfoHallController: highPressureList"+highPressureList+" lowPressureList"+lowPressureList+" heartRateList"+heartRateList);
//            } else {
//                noNewEquipment.setEqpType("BloodPressure01");
//                for (int i = 0;i < 11;i++) {
//                    highPressureList.add(0.0);
//                    lowPressureList.add(0.0);
//                    heartRateList.add(0.0);
//                }
//                noEquipmentList.add(noNewEquipment);
//            }
//            if (flagBloodOxygen01 == 1) {
//                sendMessage(netmask, order);
//
//                for (int i = 0;i < 10;i++) {
//                    spo2List.add(0.0);
//                }
//                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
//                long timestamp = System.currentTimeMillis() / 1000 - 10;
//                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
//                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));
//
//
//                ArrayList<BloodOxygen> bloodOxygenArrayList = dataService.queryBloodOxygen(objectSelectedIdS,1,limitTimeinformat);
//
//                if(bloodOxygenArrayList == null || bloodOxygenArrayList.size()==0){//如果值为空,赋0
//                    spo2List.add(0.0);
//                }else {
//                    double spo2 = bloodOxygenArrayList.get(0).getSpo2();
//                    spo2List.add(spo2);
//                }
//
//                System.out.println("UserInfoHallController: spo2List: "+spo2List);
//            }else {
//                noNewEquipment.setEqpType("BloodOxygen01");
//                for (int i = 0;i < 11;i++) {
//                    spo2List.add(0.0);
//                }
//                noEquipmentList.add(noNewEquipment);
//            }
//            if (flagMattress01 == 1) {
//
//                for (int i = 0;i < 10;i++) {
//                    breathList.add(0.0);
//                    actList.add(0.0);
//                }
//                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
//                long timestamp = System.currentTimeMillis() / 1000 - 10;
//                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
//                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));
//
//
//                ArrayList<Mattress> mattressArrayList = dataService.queryMattress(objectSelectedIdS,1,limitTimeinformat);
//
//                if(mattressArrayList == null || mattressArrayList.size()==0){//如果值为空,赋0
//                    spo2List.add(0.0);
//                }else {
//                    double breath = mattressArrayList.get(0).getBreath();
//                    breathList.add(breath);
//                    double act = mattressArrayList.get(0).getAct();
//                    actList.add(act);
//                }
//
//                System.out.println("UserInfoHallController: breathList: "+breathList);
//                System.out.println("UserInfoHallController: actList: "+actList);
//            }else {
//                noNewEquipment.setEqpType("Mattress01");
//                for (int i = 0;i < 11;i++) {
//                    breathList.add(0.0);
//                    actList.add(0.0);
//                }
//                noEquipmentList.add(noNewEquipment);
//            }
//
//
//            //设值
//            request.setAttribute("bodyTempList",bodyTempList);
//            request.setAttribute("envTempList",envTempList);
//            request.setAttribute("highPressureList",highPressureList);
//            request.setAttribute("lowPressureList",lowPressureList);
//            request.setAttribute("heartRateList",heartRateList);
//            request.setAttribute("spo2List",spo2List);
//            request.setAttribute("breathList", breathList);
//            request.setAttribute("actList", actList);
//            request.setAttribute("noEquipmentList",noEquipmentList);
//        }
//
//        return "monitorCenter/infoHallOnTime";
//    }
    //监测中心-监测设备数据刷新//映射地址为要刷新的地址//AutoRefresh
    @RequestMapping("/infoHallOnTimeGetInfo/AutoRefresh")
    @ResponseBody
    public Map<String,ArrayList<Double>> monitorCenterAjaxTest(HttpServletRequest request,@RequestBody ArrayList<ArrayList<Double>> array) {

        System.out.println("<<<<<<<<<<AutoRefresh>>>>>>>>>>");
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        String netmaskIdStr=userNetmaskService.queryUserRelatedNetmask(user.getUserId());
        int netmask=Integer.parseInt(netmaskIdStr);

        String order = "FEFE020404AABB";
        sendMessage(netmask, order);
        System.out.println("sendMessage Have Sended Order FEFE020404AABB");
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(objectSelectedIdS);

        ArrayList<Double> bodyTempList = array.get(0);
        ArrayList<Double> envTempList = array.get(1);
        ArrayList<Double> highPressureList = array.get(2);
        ArrayList<Double> lowPressureList = array.get(3);
        ArrayList<Double> heartRateList = array.get(4);
        ArrayList<Double> spo2List = array.get(5);
        //
        ArrayList<Double> breathList = array.get(6);
        ArrayList<Double> actList = array.get(7);



        int flagTemperature01 = 0;
        String temperature01Order="";
        int netMaskIdTemperature01=0;
        int flagBloodPressure01 = 0;
        String bloodPressure01Order="";
        int netMaskIdBloodPressure01=0;
        int flagBloodOxygen01 = 0;
        String bloodOxygen01Order="";
        int netMaskIdBloodOxygen01=0;
        //
        int flagMattress01 = 0;
        String mattress01Order="";
        int netMaskIdMattress01=0;



        int netMaskIdTemp;
        int deviceSerialTemp;
        int checkCal;


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
                bloodPressure01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                        +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
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
                temperature01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                        +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                netMaskIdTemperature01 = netMaskIdTemp;
                System.out.println("UserInfoHallController: temperature01Order: "+temperature01Order);////
            }
            else if(equipmentList.get(i).getEqpType().equals("Mattress01")){
                flagMattress01 = 1;
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

            long timestamp = System.currentTimeMillis() / 1000 - 10;
            SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
            String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));


            ArrayList<Temperature> temperatureArrayList = dataService.queryTemperature(objectSelectedIdS,1,limitTimeinformat);

            if(temperatureArrayList == null || temperatureArrayList.size()==0){
                bodyTempList.add(0.0);
                envTempList.add(0.0);
            }
            else {
                double bodyTemp = temperatureArrayList.get(0).getBodyTemp();
                bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
                double envTemp = temperatureArrayList.get(0).getEnvTemp();
                envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
                System.out.println("<体温和环境温度UserInfoHallController>" + bodyTemp + " " +envTemp);

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

            long timestamp = System.currentTimeMillis() / 1000 - 10;
            SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
            String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));

            ArrayList<BloodPressure> bloodPressureArrayList = dataService.queryBloodPressure(objectSelectedIdS,1,limitTimeinformat);

            if(bloodPressureArrayList == null||bloodPressureArrayList.size()==0){//如果值为空,全部赋0
                highPressureList.add(0.0);
                lowPressureList.add(0.0);
                heartRateList.add(0.0);
            }else {
                double lowPressure = bloodPressureArrayList.get(0).getLowPressure();
                lowPressureList.add(lowPressure);
                double heartRate = bloodPressureArrayList.get(0).getHeartRate();
                heartRateList.add(heartRate);
                double highPressure = bloodPressureArrayList.get(0).getHighPressure();
                highPressureList.add(highPressure);
            }


            System.out.println("UserInfoHallController: highPressureList"+highPressureList);
            System.out.println("UserInfoHallController: lowPressureList"+lowPressureList);
            System.out.println("UserInfoHallController: heartRateList"+heartRateList);
        }
        if (flagBloodOxygen01 == 1) {
            sendMessage(1, order);

            spo2List.remove(0);
            long timestamp10 = (System.currentTimeMillis()-10000)*1000000;

            long timestamp = System.currentTimeMillis() / 1000 - 10;
            SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
            String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));

            ArrayList<BloodOxygen> bloodOxygenArrayList = dataService.queryBloodOxygen(objectSelectedIdS,1,limitTimeinformat);

            if(bloodOxygenArrayList == null || bloodOxygenArrayList.size()==0){//如果值为空,赋0
                spo2List.add(0.0);
            }else {
                double spo2 = bloodOxygenArrayList.get(0).getSpo2();
                spo2List.add(spo2);
            }

            System.out.println("UserInfoHallController: spo2List: "+spo2List);
        }
        if (flagMattress01 == 1) {

            for (int i = 0;i < 10;i++) {
                breathList.add(0.0);
                actList.add(0.0);
            }
            long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
            long timestamp = System.currentTimeMillis() / 1000 - 10;
            SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
            String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));


            ArrayList<Mattress> mattressArrayList = dataService.queryMattress(objectSelectedIdS,1,limitTimeinformat);

            if(mattressArrayList == null || mattressArrayList.size()==0){//如果值为空,赋0
                spo2List.add(0.0);
            }else {
                double breath = mattressArrayList.get(0).getBreath();
                breathList.add(breath);
                double act = mattressArrayList.get(0).getAct();
                actList.add(act);
            }

            System.out.println("UserInfoHallController: breathList: "+breathList);
            System.out.println("UserInfoHallController: actList: "+actList);
        }

        //准备传值给前台
        Map<String,ArrayList<Double>> map = new HashMap<>();
        map.put("bodyTempList",bodyTempList);
        map.put("envTempList",envTempList);
        map.put("highPressureList",highPressureList);
        map.put("lowPressureList",lowPressureList);
        map.put("heartRateList",heartRateList);
        map.put("spo2List",spo2List);
        map.put("breathList", breathList);
        map.put("actList", actList);

        return map;
    }
    //监测中心-切换实时监测对象
    @RequestMapping("/infoHallOnTimeGetInfo")
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
        System.out.println("objIdSelected"+objectIdSelected);
        request.setAttribute("equipmentList", equipmentList);
        String objectNameSelected = request.getParameter("objectName");
//        System.out.println(objectNameSelected);
        request.setAttribute("objectNameSelected", objectNameSelected);
//        return "monitorCenter/infoHallOnTime::objectTable";


        String netmaskIdStr=userNetmaskService.queryUserRelatedNetmask(user.getUserId());
        int netmask=Integer.parseInt(netmaskIdStr);


        String order = "FEFE020404AABB";
        sendMessage(netmask, order);

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
            //
            ArrayList<Double> breathList = new ArrayList<>();
            ArrayList<Double> actList = new ArrayList<>();


            int flagTemperature01 = 0;
            String temperature01Order="";
            int netMaskIdTemperature01=0;
            int flagBloodPressure01 = 0;
            String bloodPressure01Order="";
            int netMaskIdBloodPressure01=0;
            int flagBloodOxygen01 = 0;
            String bloodOxygen01Order="";
            int netMaskIdBloodOxygen01=0;
            //
            //
            int flagMattress01 = 0;
            String mattress01Order="";
            int netMaskIdMattress01=0;


            int netMaskIdTemp;
            int deviceSerialTemp;
            int checkCal;


            for (int i = 0;i < equipmentList.size();i++) {
                System.out.println(i);
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
                    bloodPressure01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdBloodPressure01 = netMaskIdTemp;
                    System.out.println("UserInfoHallController: bloodPressure01Order: "+bloodPressure01Order);
                }
                else if(equipmentList.get(i).getEqpType().equals("Mattress01")) {
                    flagMattress01 = 1;
                }
            }
            System.out.println("UserInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+" flagBloodPressure01: "+flagBloodPressure01+" flagTemperature01: "+flagTemperature01);
            List<Equipment> noEquipmentList = new ArrayList<>();
            Equipment noNewEquipment = new Equipment();
            //连接InfluxDB
            influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
            influxDBConnector.connectToDatabase();
            if (flagTemperature01 == 1) {
                sendMessage(netmask, order);//added0526
                for (int i = 0;i < 10;i++) {
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;

                long timestamp = System.currentTimeMillis() / 1000 - 10;
                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));


                ArrayList<Temperature> temperatureArrayList = dataService.queryTemperature(objectSelectedIdS,1,limitTimeinformat);

                if(temperatureArrayList == null || temperatureArrayList.size()==0){
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }
                else {
                    double bodyTemp = temperatureArrayList.get(0).getBodyTemp();
                    bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
                    double envTemp = temperatureArrayList.get(0).getEnvTemp();
                    envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
                    System.out.println("<体温和环境温度UserInfoHallController>" + bodyTemp + " " +envTemp);

                }



//                QueryResult temperatureResults =  influxDBConnector.queryData("select last(bodyTemp),(envTemp) from temperature where objectId = "+"'"
//                        +objectIdSelected+"'"+" and time > "+timestamp10);
////                System.out.println("UserInfoHallController: temperatureResults: "+temperatureResults);
//                if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
////                    System.out.println("UserInfoHallController: temperatureResults null");
//                    bodyTempList.add(0.0);
//                    envTempList.add(0.0);
//                }else {
//                    double bodyTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取体温，Object转为两位小数
//                    bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
//                    double envTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取环境温度，Object转为两位小数
//                    envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
//                }
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
                sendMessage(netmask, order);
                for (int i = 0;i < 10;i++) {
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;

                long timestamp = System.currentTimeMillis() / 1000 - 10;
                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));

                ArrayList<BloodPressure> bloodPressureArrayList = dataService.queryBloodPressure(objectSelectedIdS,1,limitTimeinformat);

                if(bloodPressureArrayList == null||bloodPressureArrayList.size()==0){//如果值为空,全部赋0
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }else {
                    double lowPressure = bloodPressureArrayList.get(0).getLowPressure();
                    lowPressureList.add(lowPressure);
                    double heartRate = bloodPressureArrayList.get(0).getHeartRate();
                    heartRateList.add(heartRate);
                    double highPressure = bloodPressureArrayList.get(0).getHighPressure();
                    highPressureList.add(highPressure);
                }


//                QueryResult bloodPressureResults =  influxDBConnector.queryData("select last(highPressure),(lowPressure),(heartRate) from bloodPressure where objectId = "+"'"
//                        +objectIdSelected+"'"+" and time > "+timestamp10);
////                System.out.println("UserInfoHallController: bloodPressureResults: "+bloodPressureResults);
//                if(bloodPressureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
////                    System.out.println("UserInfoHallController: bloodPressureResults null");
//                    highPressureList.add(0.0);
//                    lowPressureList.add(0.0);
//                    heartRateList.add(0.0);
//                }else {
//                    double lowPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取低压
//                    lowPressureList.add(lowPressure);
//                    double heartRate = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(3);//获取心率
//                    heartRateList.add(heartRate);
//                    double highPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取高压
//                    highPressureList.add(highPressure);
//                }
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
                sendMessage(netmask, order);
                for (int i = 0;i < 10;i++) {
                    spo2List.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                long timestamp = System.currentTimeMillis() / 1000 - 10;
                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));

                ArrayList<BloodOxygen> bloodOxygenArrayList = dataService.queryBloodOxygen(objectSelectedIdS,1,limitTimeinformat);

                if(bloodOxygenArrayList == null || bloodOxygenArrayList.size()==0){//如果值为空,赋0
                    spo2List.add(0.0);
                }else {
                    double spo2 = bloodOxygenArrayList.get(0).getSpo2();
                    spo2List.add(spo2);
                }


//                QueryResult spo2Results =  influxDBConnector.queryData("select last(spo2) from bloodOxygen where objectId = "+"'"
//                        +objectIdSelected+"'"+" and time > "+timestamp10);
////                System.out.println("UserInfoHallController: spo2Results: "+spo2Results);
//                if(spo2Results.getResults().get(0).getSeries() == null){//如果值为空,赋0
////                    System.out.println("UserInfoHallController: spo2Results null");
//                    spo2List.add(0.0);
//                }else {
//                    double spo2 = (double)spo2Results.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取血氧饱和度,get(0)是时间戳
//                    spo2List.add(spo2);
//                }
                System.out.println("UserInfoHallController: spo2List: "+spo2List);
            }else {
                noNewEquipment.setEqpType("BloodOxygen01");
                for (int i = 0;i < 11;i++) {
                    spo2List.add(0.0);
                }
                noEquipmentList.add(noNewEquipment);
            }
            if (flagMattress01 == 1) {
                for (int i = 0;i < 10;i++) {
                    breathList.add(0.0);
                    actList.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                long timestamp = System.currentTimeMillis() / 1000 - 10;
                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));


                ArrayList<Mattress> mattressArrayList = dataService.queryMattress(objectSelectedIdS,1,limitTimeinformat);

                if(mattressArrayList == null || mattressArrayList.size()==0){//如果值为空,赋0
                    spo2List.add(0.0);
                }else {
                    double breath = mattressArrayList.get(0).getBreath();
                    breathList.add(breath);
                    double act = mattressArrayList.get(0).getAct();
                    actList.add(act);
                }

                System.out.println("UserInfoHallController: breathList: "+breathList);
                System.out.println("UserInfoHallController: actList: "+actList);
            }else {
                noNewEquipment.setEqpType("Mattress01");
                for (int i = 0;i < 11;i++) {
                    breathList.add(0.0);
                    actList.add(0.0);
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
            request.setAttribute("breathList", breathList);
            request.setAttribute("actList", actList);
            request.setAttribute("noEquipmentList",noEquipmentList);
        }
        return "monitorCenter/infoHallOnTime";
    }

    //监测中心-历史信息
    @RequestMapping("/infoHallHistory")
    public String infoHallHistory(HttpServletRequest request, HttpServletResponse response) throws NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从数据库中获取所有该用户关联的监测对象并传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);
        //        System.out.println(objectList.get(0).getObjectId());
        //        System.out.println(objectList.get(0).getObjectName());

        String startTime = dateformat.format(System.currentTimeMillis()).substring(0,16);
        startTime = startTime.replace(" ","T");
        String endTime = dateformat.format(System.currentTimeMillis()).substring(0,16);
        endTime = endTime.replace(" ","T");
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        List<EquipmentType> eqpTypeList = equipmentTypeService.queryAllEquipmentType();
        Collections.reverse(eqpTypeList);//没什么意义，就是把四个类型换一下位置
        request.setAttribute("eqpTypeList", eqpTypeList);

        return "monitorCenter/infoHallHistory";
    }

    //监测中心-历史信息查询结果
    @RequestMapping("/infoHallHistorySearchResult")
    public String infoHallHistorySearchResult(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        // 从数据库中获取所有该用户关联的监测对象并传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);

        // 获取选中的监测对象的Id和其他条件，查询结果并显示在页面上
        String objectSelected = request.getParameter("objectSelected");
        request.setAttribute("objectSelected",objectSelected);
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        // System.out.println(objectSelect);
        // System.out.println(startTime);//2019-03-03T10:20
        // System.out.println(endTime);//2019-03-03T11:20
        // 取回的时间格式也许需要处理
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(objectSelected);
        request.setAttribute("equipmentList", equipmentList);
        String objectNameSelected = objectService.queryObjectByObjectId(objectSelected).getObjectName();
        request.setAttribute("objectNameSelected", objectNameSelected);
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        // System.out.println("startTime: "+startTime);
        // 时间字符串转LocalDateTime
        String timestampStart = dateTimeLocalToTimeStampString(startTime);
        String timestampEnd = dateTimeLocalToTimeStampString(endTime);
        // System.out.println("timestampStart: "+timestampStart);
        // System.out.println("timestampEnd: "+timestampEnd);
        // System.out.println("System.currentTimeMillis: "+System.currentTimeMillis());

        // 按照时间要求取数据
        // 判断监测对象有没有设备
        if (equipmentList.size() != 0) { // 如果有设备
            // 准备列表
            ArrayList<Double> bodyTempList = new ArrayList<>();
            ArrayList<Double> envTempList = new ArrayList<>();
            ArrayList<Double> highPressureList = new ArrayList<>();
            ArrayList<Double> lowPressureList = new ArrayList<>();
            ArrayList<Double> heartRateList = new ArrayList<>();
            ArrayList<Double> spo2List = new ArrayList<>();

            // ArrayList<String> temperature01TimeStampList = new ArrayList<>();
            // ArrayList<String> bloodPressure01TimeStampList = new ArrayList<>();
            // ArrayList<String> bloodOxygen01TimeStampList = new ArrayList<>();

            temperature01TimeStampList.clear();
            bloodPressure01TimeStampList.clear();
            bloodOxygen01TimeStampList.clear();
            int flagTemperature01 = 0;
            int flagBloodPressure01 = 0;
            int flagBloodOxygen01 = 0;
            for (int i = 0;i < equipmentList.size();i++) {
                if (equipmentList.get(i).getEqpType().equals("Temperature01")) {
                    flagTemperature01 = 1;
                }
                else if(equipmentList.get(i).getEqpType().equals("BloodOxygen01")) {
                    flagBloodOxygen01 = 1;
                }
                else if(equipmentList.get(i).getEqpType().equals("BloodPressure01")) {
                    flagBloodPressure01 = 1;
                }
            }
            System.out.println("UserInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+" flagBloodPressure01: "+flagBloodPressure01+" flagTemperature01: "+flagTemperature01);
            //连接InfluxDB
            influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
            influxDBConnector.connectToDatabase();
            if (flagTemperature01 == 1) {
                QueryResult temperatureResults =  influxDBConnector.queryData("select bodyTemp,envTemp from temperature where objectId = "
                        +"'"+objectSelected+"'"+" and time > "+timestampStart+" and time < "+timestampEnd);
//                System.out.println("UserInfoHallController: temperatureResults: "+temperatureResults);
                if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,赋0
//                    System.out.println("UserInfoHallController: temperatureResults null");
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }else {
                    String timestamp="";
                    for(int i = 0;i < temperatureResults.getResults().get(0).getSeries().get(0).getValues().size();i++) {
                        timestamp = String.valueOf(temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(i).get(0));//获取时间戳
                        temperature01TimeStampList.add(timestamp);
                        double bodyTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(i).get(1);//获取体温，Object转为两位小数
                        bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
                        double envTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(i).get(2);//获取环境温度，Object转为两位小数
                        envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
                    }
//                    System.out.println("timestamp586: "+timestamp);
                }
                System.out.println("UserInfoHallController: bodyTempListSize: "+bodyTempList.size());
                System.out.println("UserInfoHallController: temperature01TimeStampList: "+temperature01TimeStampList);
                System.out.println("UserInfoHallController: bodyTempList"+bodyTempList);
                System.out.println("UserInfoHallController: envTempList"+envTempList);
            }
            if (flagBloodPressure01 == 1) {
                QueryResult bloodPressureResults =  influxDBConnector.queryData("select highPressure,lowPressure,heartRate from bloodPressure where objectId = "
                        +"'"+objectSelected+"'"+" and time > "+timestampStart+" and time < "+timestampEnd);
                // System.out.println("UserInfoHallController: bloodPressureResults: "+bloodPressureResults);
                if(bloodPressureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
                    // System.out.println("UserInfoHallController: bloodPressureResults null");
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }else {
                    String timestamp="";
                    for(int i = 0;i<bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().size();i++) {
                        timestamp = String.valueOf(bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(i).get(0));//获取时间字符串
                        bloodPressure01TimeStampList.add(timestamp);
                        double highPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(i).get(1);//获取高压
                        highPressureList.add(highPressure);
                        double lowPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(i).get(2);//获取低压
                        lowPressureList.add(lowPressure);
                        double heartRate = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(i).get(3);//获取心率
                        heartRateList.add(heartRate);
                    }
                }
                System.out.println("UserInfoHallController: bloodPressure01TimeStampList"+bloodPressure01TimeStampList);
                System.out.println("UserInfoHallController: highPressureList"+highPressureList);
                System.out.println("UserInfoHallController: lowPressureList"+lowPressureList);
                System.out.println("UserInfoHallController: heartRateList"+heartRateList);
            }
            if (flagBloodOxygen01 == 1) {
                QueryResult spo2Results =  influxDBConnector.queryData("select spo2 from bloodOxygen where objectId = "
                        +"'"+objectSelected+"'"+" and time > "+timestampStart+" and time < "+timestampEnd);
                // System.out.println("UserInfoHallController: spo2Results: "+spo2Results);
                if(spo2Results.getResults().get(0).getSeries() == null){//如果值为空,赋0
                    // System.out.println("UserInfoHallController: spo2Results null");
                    spo2List.add(0.0);
                }else {
                    String timestamp = "";
                    for(int i = 0;i < spo2Results.getResults().get(0).getSeries().get(0).getValues().size();i++) {
                        timestamp = String.valueOf(spo2Results.getResults().get(0).getSeries().get(0).getValues().get(i).get(0));//获取时间字符串
                        bloodOxygen01TimeStampList.add(timestamp);
                        double spo2 = (double)spo2Results.getResults().get(0).getSeries().get(0).getValues().get(i).get(1);//获取血氧饱和度,get(0)是时间戳
                        spo2List.add(spo2);
                    }

                }
                System.out.println("UserInfoHallController: bloodOxygen01TimeStampList: "+bloodOxygen01TimeStampList);
                System.out.println("UserInfoHallController: spo2List: "+spo2List);
            }
            //设值
            request.setAttribute("bodyTempList",bodyTempList);
            request.setAttribute("envTempList",envTempList);
            request.setAttribute("highPressureList",highPressureList);
            request.setAttribute("lowPressureList",lowPressureList);
            request.setAttribute("heartRateList",heartRateList);
            request.setAttribute("spo2List",spo2List);
            request.setAttribute("temperature01TimeStampList",temperature01TimeStampList);
            request.setAttribute("bloodPressure01TimeStampList",bloodPressure01TimeStampList);
            request.setAttribute("bloodOxygen01TimeStampList",bloodOxygen01TimeStampList);

        }
        return "monitorCenter/infoHallHistory";
    }

    //监测中心-X轴值设置
    @RequestMapping("/infoHallHistorySearchResult/AutoRefresh")
    @ResponseBody
    public Map<String,ArrayList<String>> monitorCenterAjaxGetData(@RequestBody ArrayList<ArrayList<String>> array) {
        // System.out.println("array: "+array);
        System.out.println("ajaxTest");

        Map<String,ArrayList<String>> map = new HashMap<>();
        //        ArrayList<String> timeListTemperature01 = new ArrayList<>();
        //        ArrayList<String> timeListBloodPressure01 = new ArrayList<>();
        //        ArrayList<String> timeListBloodOxygen01 = new ArrayList<>();
        //        for (int i = 0;i<100;i++)
        //        {
        //            timeListTemperature01.add("2019-04-25T10:52:35.036Z"+String.valueOf(i));
        //            timeListBloodPressure01.add("2019-04-25T10:52:35.036Z"+String.valueOf(i));
        //            timeListBloodOxygen01.add("2019-04-25T10:52:35.036Z"+String.valueOf(i));
        //        }
        map.put("timeListTemperature01",temperature01TimeStampList);
        map.put("timeListBloodPressure01",bloodPressure01TimeStampList);
        map.put("timeListBloodOxygen01",bloodOxygen01TimeStampList);

        return map;
    }


    //返回时间戳字符串，单位纳秒（ns）,而非毫秒，请注意
    public String dateTimeLocalToTimeStampString(String dateTimeLocal) {
        dateTimeLocal = dateTimeLocal.replace("T"," ");
        dateTimeLocal = dateTimeLocal+":00";//加上秒值00
        LocalDateTime localDateTimeStart = LocalDateTime.parse(dateTimeLocal,df);
        long timestamp = localDateTimeStart.toInstant(ZoneOffset.of("+8")).toEpochMilli();//时区东8区
        return String.valueOf(timestamp*1000000);
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
