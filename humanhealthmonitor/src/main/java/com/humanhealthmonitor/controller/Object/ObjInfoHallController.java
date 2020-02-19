package com.humanhealthmonitor.controller.Object;

import com.humanhealthmonitor.InfluxDBConnector;
import com.humanhealthmonitor.mapper.DataMapper;
import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.service.*;
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

import static com.humanhealthmonitor.MsgQueue.protocolState;
import static com.humanhealthmonitor.MsgQueue.sendAMQPQueue;
import static com.humanhealthmonitor.MsgQueue.sendMsgQueue;

@Controller
public class ObjInfoHallController {
    @Autowired
    private ObjectService objectService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private UserNetmaskService userNetmaskService;
    @Autowired
    private DataService dataService;
    @Autowired
    private EquipmentTypeService equipmentTypeService;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private InfluxDBConnector influxDBConnector;//创建influxDB连接实例
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    private static ArrayList<String> temperature01TimeStampList = new ArrayList<>();
//    private static ArrayList<String> bloodPressure01TimeStampList = new ArrayList<>();
//    private static ArrayList<String> bloodOxygen01TimeStampList = new ArrayList<>();

    private ArrayList<String> temperature01TimeStampList = new ArrayList<>();
    private ArrayList<String> bloodPressure01TimeStampList = new ArrayList<>();
    private ArrayList<String> bloodOxygen01TimeStampList = new ArrayList<>();

    //健康中心-实时信息
    @RequestMapping("/objInfoHallOnTimeNo")
    public String objInfoHallOnTime(HttpServletRequest request, HttpServletResponse response) {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //从数据库中取出设备列表传到页面 //为啥要传到页面
        //查找对象绑定的所以有设备
        List<Equipment> objEquipmentList = equipmentService.queryAllEquipmentByObjectId(object.getObjectId());
        request.setAttribute("equipmentList", objEquipmentList);

        String userId = userNetmaskService.queryUserIdByObjectId(object.getObjectId());
        String netmaskStr = userNetmaskService.queryUserRelatedNetmask(userId);
        int netmask = Integer.parseInt(netmaskStr);


        String order = "FEFE020404AABB";
        sendMessage(netmask, order);
        //判断监测对象有没有设备
        if (objEquipmentList.size() != 0) {//如果有设备
//            sendMsgQueue.offer("FEFE0401040005AABB");
//            int netMaskId = objEquipmentList.get(0).getNetmaskId();//added0524
//            sendMsgQueue.get(netMaskId-1).offer("FEFE0401040005AABB");//added0524
            //查询指定设备的例子：比如FE FE 04 01 03 05 09 AA BB需要网关号01(由设备查找并且决定发送到的队列下标)、
            //指令码03（固定）、设备在该网关下的编号（由设备查找）
            //准备列表
            ArrayList<Double> highPressureList = new ArrayList<>();
            ArrayList<Double> lowPressureList = new ArrayList<>();
            ArrayList<Double> heartRateList = new ArrayList<>();
            ArrayList<Double> spo2List = new ArrayList<>();
            ArrayList<Double> bodyTempList = new ArrayList<>();
            ArrayList<Double> envTempList = new ArrayList<>();
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
            int flagMattress01 = 0;
            String mattress01Order="";
            int netMaskIdMattress01=0;


            int netMaskIdTemp;
            int deviceSerialTemp;
            int checkCal;
            for (int i = 0; i < objEquipmentList.size(); i++) {
                if (objEquipmentList.get(i).getEqpType().equals("Temperature01")) {
                    flagTemperature01 = 1;
                    netMaskIdTemp = objEquipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = objEquipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    temperature01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdTemperature01 = netMaskIdTemp;
                    System.out.println("ObjInfoHallController: temperature01Order: "+temperature01Order);
                }
                else if(objEquipmentList.get(i).getEqpType().equals("BloodOxygen01")) {
                    flagBloodOxygen01 = 1;
                    netMaskIdTemp = objEquipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = objEquipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    bloodOxygen01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdBloodOxygen01 = netMaskIdTemp;
                    System.out.println("ObjInfoHallController: bloodOxygen01Order: "+bloodOxygen01Order);////
                }
                else if(objEquipmentList.get(i).getEqpType().equals("BloodPressure01")) {
                    flagBloodPressure01 = 1;
                    netMaskIdTemp = objEquipmentList.get(i).getNetmaskId();
                    deviceSerialTemp = objEquipmentList.get(i).getDeviceSerial();
                    checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                    String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                    if(checkCalStr.length()==1)
                        checkCalStr = "0"+checkCalStr;
                    bloodPressure01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                            +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                    netMaskIdBloodPressure01 = netMaskIdTemp;
                    System.out.println("ObjInfoHallController: bloodPressure01Order: "+bloodPressure01Order);
                }
                else if(objEquipmentList.get(i).getEqpType().equals("Mattress01")){
                    flagMattress01 = 1;
                }
            }
            System.out.println("ObjInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+" flagBloodPressure01: "+flagBloodPressure01+" flagTemperature01: "+flagTemperature01);



            List<Equipment> noEquipmentList = new ArrayList<>();
            Equipment noNewEquipment = new Equipment();//zy

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
                long timestamp = System.currentTimeMillis() / 1000 - 20;
                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));


                ArrayList<Temperature> temperatureArrayList = dataService.queryTemperature(object.getObjectId(),1,limitTimeinformat);

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
//                        +object.getObjectId()+"'"+" and time > "+timestamp10);
////                System.out.println("ObjInfoHallController: temperatureResults: "+temperatureResults);
//                if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
////                    System.out.println("ObjInfoHallController: temperatureResults null...");
//                    bodyTempList.add(0.0);
//                    envTempList.add(0.0);
//                }else {
//                    double bodyTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取体温，Object转为两位小数
//                    bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
//                    double envTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取环境温度，Object转为两位小数
//                    envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
//                }
                System.out.println("ObjInfoHallController: bodyTempList"+bodyTempList);
                System.out.println("ObjInfoHallController: envTempList"+envTempList);
            } else {


                noNewEquipment.setEqpType("Temperature01");
                for (int i = 0;i < 11;i++) {
                    bodyTempList.add(0.0);
                    envTempList.add(0.0);
                }
                noEquipmentList.add(noNewEquipment);
            }

            if (flagBloodPressure01 == 1) {
                //                sendMsgQueue.get(netMaskIdBloodPressure01-1).offer(bloodPressure01Order);////added0524
                sendMessage(netmask,order);//added0526
                for (int i = 0;i < 10;i++) {
                    highPressureList.add(0.0);
                    lowPressureList.add(0.0);
                    heartRateList.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                long timestamp = System.currentTimeMillis() / 1000 - 10;
                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));

                ArrayList<BloodPressure> bloodPressureArrayList = dataService.queryBloodPressure(object.getObjectId(),1,limitTimeinformat);

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
//                        +object.getObjectId()+"'"+" and time > "+timestamp10);
////                System.out.println("ObjInfoHallController: bloodPressureResults: "+bloodPressureResults);
//                if(bloodPressureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
////                    System.out.println("ObjInfoHallController: bloodPressureResults null ...");
//                    highPressureList.add(0.0);
//                    lowPressureList.add(0.0);
//                    heartRateList.add(0.0);
//                }else {
//                    double highPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取高压,get(0)是时间戳
//                    highPressureList.add(highPressure);
//                    double lowPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取低压
//                    lowPressureList.add(lowPressure);
//                    double heartRate = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(3);//获取心率
//                    heartRateList.add(heartRate);
//                }
                System.out.println("ObjInfoHallController: highPressureList"+highPressureList);
                System.out.println("ObjInfoHallController: lowPressureList"+lowPressureList);
                System.out.println("ObjInfoHallController: heartRateList"+heartRateList);
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
//                sendMsgQueue.get(netMaskIdBloodOxygen01-1).offer(bloodOxygen01Order);////added0524
                sendMessage(netmask,order);
                for (int i = 0;i < 10;i++) {
                    spo2List.add(0.0);
                }
                long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
                long timestamp = System.currentTimeMillis() / 1000 - 10;
                SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
                String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));


                ArrayList<BloodOxygen> bloodOxygenArrayList = dataService.queryBloodOxygen(object.getObjectId(),1,limitTimeinformat);

                if(bloodOxygenArrayList == null || bloodOxygenArrayList.size()==0){//如果值为空,赋0
                    spo2List.add(0.0);
                }else {
                    double spo2 = bloodOxygenArrayList.get(0).getSpo2();
                    spo2List.add(spo2);
                }






//                QueryResult spo2Results =  influxDBConnector.queryData("select last(spo2) from bloodOxygen where objectId = "+"'"
//                        +object.getObjectId()+"'"+" and time > "+timestamp10);
////                System.out.println("ObjInfoHallController: spo2Results: "+spo2Results);
//                if(spo2Results.getResults().get(0).getSeries() == null){//如果值为空,赋0
////                    System.out.println("ObjInfoHallController: spo2Results null");
//                    spo2List.add(0.0);
//                }else {
//                    double spo2 = (double)spo2Results.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取血氧饱和度,get(0)是时间戳
//                    spo2List.add(spo2);
//                }
                System.out.println("ObjInfoHallController: spo2List: "+spo2List);
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


                ArrayList<Mattress> mattressArrayList = dataService.queryMattress(object.getObjectId(),1,limitTimeinformat);

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




//            if (flagBloodOxygen01 == 0)/////////////////////
//            {
//                for (int i = 0;i < 11;i++)
//                {
//                    spo2List.add(8.8);
//                }
//            }
//            System.out.println("ObjInfoHallController: spo2List: "+spo2List);

//            System.out.println("OutPrint ObjInfoHallController: highPressureList"+highPressureList);
//            System.out.println("OutPrint ObjInfoHallController: lowPressureList"+lowPressureList);
//            System.out.println("OutPrint ObjInfoHallController: heartRateList"+heartRateList);
//            System.out.println("OutPrint ObjInfoHallController: bodyTempList"+bodyTempList);
//            System.out.println("OutPrint ObjInfoHallController: envTempList"+envTempList);
//            System.out.println("OutPrint ObjInfoHallController: spo2List: "+spo2List);
//            System.out.println("OutPrint ObjInfoHallController: equipmentList: "+objEquipmentList);
//            System.out.println("OutPrint ObjInfoHallController: noEquipmentList: "+noEquipmentList);
            //设值
//            request.setAttribute("equipmentList", objEquipmentList);
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

        return "healthCenter/objInfoHallOnTime";
    }

    //健康中心-实时健康信息数据刷新
    @RequestMapping("/objInfoHallOnTime/AutoRefresh")
    @ResponseBody
    public Map<String,ArrayList<Double>> healthCenterAjaxTest(@RequestBody ArrayList<ArrayList<Double>> array,HttpServletRequest request) {
//        System.out.println("array: "+array);
        System.out.println("<<<<<<<objInfoHallOnTimeAutoRefresh>>>>>>>");
//        System.out.println("objectSelectedIdS"+objectSelectedIdS);

        Object object = (Object) request.getSession().getAttribute("object");
//        request.setAttribute("object", object);
        List<Equipment> objEquipmentList = equipmentService.queryAllEquipmentByObjectId(object.getObjectId());
//        sendMsgQueue.offer("FEFE0401040005AABB");
//        int netMaskId = objEquipmentList.get(0).getNetmaskId();//added0524
//        sendMsgQueue.get(netMaskId-1).offer("FEFE0401040005AABB");//added0524

        ArrayList<Double> bodyTempList = array.get(0);
        ArrayList<Double> envTempList = array.get(1);
        ArrayList<Double> highPressureList = array.get(2);
        ArrayList<Double> lowPressureList = array.get(3);
        ArrayList<Double> heartRateList = array.get(4);
        ArrayList<Double> spo2List = array.get(5);
        //
        ArrayList<Double> breathList = array.get(6);
        ArrayList<Double> actList = array.get(7);
//        System.out.println("ObjInfoHallController: bodyTempList"+bodyTempList);
//        System.out.println("envTempList"+envTempList);
//        System.out.println("highPressureList"+highPressureList);
//        System.out.println("lowPressureList"+lowPressureList);
//        System.out.println("heartRateList"+heartRateList);
//        System.out.println("spo2List: "+spo2List);

//        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(object.getObjectId());//"hitwhob001"//modified0526

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


        String userId = userNetmaskService.queryUserIdByObjectId(object.getObjectId());
        String netmaskStr = userNetmaskService.queryUserRelatedNetmask(userId);
        int netmask = Integer.parseInt(netmaskStr);

        String order = "FEFE020404AABB";
        sendMessage(netmask, order);

        for (int i = 0;i < objEquipmentList.size();i++) {
            if(objEquipmentList.get(i).getEqpType().equals("BloodOxygen01")) {
                flagBloodOxygen01 = 1;
                netMaskIdTemp = objEquipmentList.get(i).getNetmaskId();
                deviceSerialTemp = objEquipmentList.get(i).getDeviceSerial();
                checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                if(checkCalStr.length()==1)
                    checkCalStr = "0"+checkCalStr;
                bloodOxygen01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                        +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                netMaskIdBloodOxygen01 = netMaskIdTemp;
                System.out.println("ObjInfoHallController: bloodOxygen01Order: "+bloodOxygen01Order);////
            }
            else if(objEquipmentList.get(i).getEqpType().equals("BloodPressure01")) {
                flagBloodPressure01 = 1;
                netMaskIdTemp = objEquipmentList.get(i).getNetmaskId();
                deviceSerialTemp = objEquipmentList.get(i).getDeviceSerial();
                checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                if(checkCalStr.length()==1)
                    checkCalStr = "0"+checkCalStr;
                bloodPressure01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                        +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                netMaskIdBloodPressure01 = netMaskIdTemp;
                System.out.println("ObjInfoHallController: bloodPressure01Order: "+bloodPressure01Order);
            }
            else if (objEquipmentList.get(i).getEqpType().equals("Temperature01")) {
                flagTemperature01 = 1;
                netMaskIdTemp = objEquipmentList.get(i).getNetmaskId();
                deviceSerialTemp = objEquipmentList.get(i).getDeviceSerial();
                checkCal = (netMaskIdTemp+3+deviceSerialTemp)%64;
                String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                if(checkCalStr.length()==1)
                    checkCalStr = "0"+checkCalStr;
                temperature01Order = "FEFE04"+String.format("%02d",netMaskIdTemp)+"03"
                        +String.format("%02d",deviceSerialTemp)+checkCalStr+"AABB";
                netMaskIdTemperature01 = netMaskIdTemp;
                System.out.println("ObjInfoHallController: temperature01Order: "+temperature01Order);////
            }
            else if(objEquipmentList.get(i).getEqpType().equals("Mattress01")){
                flagMattress01 = 1;
            }
        }
        System.out.println("ObjInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+" flagBloodPressure01: "+flagBloodPressure01+" flagTemperature01: "+flagTemperature01);
        //连接InfluxDB
        influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
        influxDBConnector.connectToDatabase();
        if (flagTemperature01 == 1) {
//            sendMsgQueue.get(netMaskIdTemperature01-1).offer(temperature01Order);////
            sendMessage(netmask,order);//added0526

            bodyTempList.remove(0);//去掉开头
            envTempList.remove(0);
            long timestamp10 = (System.currentTimeMillis()-10000)*1000000;


            long timestamp = System.currentTimeMillis() / 1000 - 10;
            SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
            String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));


            ArrayList<Temperature> temperatureArrayList = dataService.queryTemperature(object.getObjectId(),1,limitTimeinformat);

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



//            QueryResult temperatureResults =  influxDBConnector.queryData("select last(bodyTemp),(envTemp) from temperature where objectId = "
//                    +"'"+object.getObjectId()+"'"+" and time > "+timestamp10);
////            System.out.println("ObjInfoHallController: temperatureResults: "+temperatureResults);
//            if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
////                System.out.println("ObjInfoHallController: temperatureResults null 254");
//                bodyTempList.add(0.0);
//                envTempList.add(0.0);
//            }else {
//                double bodyTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取体温，Object转为两位小数
//                bodyTempList.add(Double.valueOf(String.format("%.2f",bodyTemp)));
//                double envTemp = (double)temperatureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取环境温度，Object转为两位小数
//                envTempList.add(Double.valueOf(String.format("%.2f",envTemp)));
//            }
            System.out.println("ObjInfoHallController: bodyTempList"+bodyTempList);
            System.out.println("ObjInfoHallController: envTempList"+envTempList);
        }
        if (flagBloodPressure01 == 1) {
//            sendMsgQueue.get(netMaskIdBloodPressure01-1).offer(bloodPressure01Order);////
            sendMessage(netmask,order);//added0526

            highPressureList.remove(0);
            lowPressureList.remove(0);
            heartRateList.remove(0);
            long timestamp10 = (System.currentTimeMillis()-10000)*1000000;
            long timestamp = System.currentTimeMillis() / 1000 - 10;
            SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
            String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));

            ArrayList<BloodPressure> bloodPressureArrayList = dataService.queryBloodPressure(object.getObjectId(),1,limitTimeinformat);

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




//            QueryResult bloodPressureResults =  influxDBConnector.queryData("select last(highPressure),(lowPressure),(heartRate) from bloodPressure where objectId = "+"'"
//                    +object.getObjectId()+"'"+" and time > "+timestamp10);
////            System.out.println("ObjInfoHallController: bloodPressureResults: "+bloodPressureResults);
//            if(bloodPressureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
////                System.out.println("ObjInfoHallController: bloodPressureResults null 277");
//                highPressureList.add(0.0);
//                lowPressureList.add(0.0);
//                heartRateList.add(0.0);
//            }else {
//                double lowPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(2);//获取低压
//                lowPressureList.add(lowPressure);
//                double heartRate = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(3);//获取心率
//                heartRateList.add(heartRate);
//                double highPressure = (double)bloodPressureResults.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取高压
//                highPressureList.add(highPressure);
////                System.out.println("ObjInfoHallController: get new pressure");
//            }
            System.out.println("ObjInfoHallController: highPressureList"+highPressureList);
            System.out.println("ObjInfoHallController: lowPressureList"+lowPressureList);
            System.out.println("ObjInfoHallController: heartRateList"+heartRateList);
        }
        if (flagBloodOxygen01 == 1) {
//            sendMsgQueue.get(netMaskIdBloodOxygen01-1).offer(bloodOxygen01Order);////
            sendMessage(netmask,order);//added0526
            spo2List.remove(0);
            long timestamp10 = (System.currentTimeMillis()-10000)*1000000;

            long timestamp = System.currentTimeMillis() / 1000 - 10;
            SimpleDateFormat format =  new SimpleDateFormat("yyyyMMddHHmmss"); //设置格式
            String limitTimeinformat = format.format(Long.parseLong(timestamp + "000"));

            ArrayList<BloodOxygen> bloodOxygenArrayList = dataService.queryBloodOxygen(object.getObjectId(),1,limitTimeinformat);

            if(bloodOxygenArrayList == null || bloodOxygenArrayList.size()==0){//如果值为空,赋0
                spo2List.add(0.0);
            }else {
                double spo2 = bloodOxygenArrayList.get(0).getSpo2();
                spo2List.add(spo2);
            }



//            QueryResult spo2Results =  influxDBConnector.queryData("select last(spo2) from bloodOxygen where objectId = "
//                    +"'"+object.getObjectId()+"'"+" and time > "+timestamp10);
////            System.out.println("ObjInfoHallController: spo2Results: "+spo2Results);
//            if(spo2Results.getResults().get(0).getSeries() == null){//如果值为空,赋0
////                System.out.println("ObjInfoHallController: spo2Results null 301");
//                spo2List.add(0.0);
//            }else {
//                double spo2 = (double)spo2Results.getResults().get(0).getSeries().get(0).getValues().get(0).get(1);//获取血氧饱和度,get(0)是时间戳
//                spo2List.add(spo2);
//            }
            System.out.println("ObjInfoHallController: spo2List: "+spo2List);
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


            ArrayList<Mattress> mattressArrayList = dataService.queryMattress(object.getObjectId(),1,limitTimeinformat);

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





//        else///////////////////
//        {
//            spo2List.remove(0);
//            spo2List.add(0.0);
//            System.out.println("ObjInfoHallController: spo2List else: "+spo2List);
//        }
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
    //健康中心-历史信息
    @RequestMapping("/objInfoHallHistory")
    public String objInfoHallHistory(HttpServletRequest request, HttpServletResponse response) {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //从数据库中取出设备列表传到页面
//        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(object.getObjectId());
        List<Equipment> equipmentList = new ArrayList<>();
        request.setAttribute("equipmentList", equipmentList);

        String startTime = dateformat.format(System.currentTimeMillis()).substring(0,16);
        startTime = startTime.replace(" ","T");
        String endTime = dateformat.format(System.currentTimeMillis()).substring(0,16);
        endTime = endTime.replace(" ","T");
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        List<EquipmentType> eqpTypeList = equipmentTypeService.queryAllEquipmentType();
        Collections.reverse(eqpTypeList);
        request.setAttribute("eqpTypeList", eqpTypeList);


        return "healthCenter/objInfoHallHistory";
    }

    //健康中心-历史信息-查询结果
    @RequestMapping("/objInfoHallHistoryResult")
    public String objInfoHallHistoryResult(HttpServletRequest request, HttpServletResponse response) {
        Object object = (Object) request.getSession().getAttribute("object");
        request.setAttribute("object", object);

        //从数据库中取出设备列表传到页面
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(object.getObjectId());
        request.setAttribute("equipmentList", equipmentList);

        //获取用户选择的时间信息，然后查询数据，生成图像
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        request.setAttribute("startTime",startTime);
        request.setAttribute("endTime",endTime);
        String timestampStart = dateTimeLocalToTimeStampString(startTime);
        String timestampEnd = dateTimeLocalToTimeStampString(endTime);

        //判断监测对象有没有设备
        if (equipmentList.size() != 0) {//如果有设备
            //准备列表
            ArrayList<Double> bodyTempList = new ArrayList<>();
            ArrayList<Double> envTempList = new ArrayList<>();
            ArrayList<Double> highPressureList = new ArrayList<>();
            ArrayList<Double> lowPressureList = new ArrayList<>();
            ArrayList<Double> heartRateList = new ArrayList<>();
            ArrayList<Double> spo2List = new ArrayList<>();

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
            System.out.println("ObjInfoHallController: flagBloodOxygen01: "+flagBloodOxygen01+"flagBloodPressure01: "+flagBloodPressure01+"flagTemperature01: "+flagTemperature01);
            //连接InfluxDB
            influxDBConnector = new InfluxDBConnector("Andy","123456","http://140.143.232.52:8086","health_data");
            influxDBConnector.connectToDatabase();
            if (flagTemperature01 == 1) {
                QueryResult temperatureResults =  influxDBConnector.queryData("select bodyTemp,envTemp from temperature where objectId = "
                        +"'"+object.getObjectId()+"'"+" and time > "+timestampStart+" and time < "+timestampEnd);
//                System.out.println("ObjInfoHallController: temperatureResults: "+temperatureResults);
                if(temperatureResults.getResults().get(0).getSeries() == null){//如果值为空,赋0
//                    System.out.println("ObjInfoHallController: temperatureResults null");
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
                System.out.println("ObjInfoHallController: bodyTempListSize: "+bodyTempList.size());
                System.out.println("ObjInfoHallController: temperature01TimeStampList: "+temperature01TimeStampList);
                System.out.println("ObjInfoHallController: bodyTempList"+bodyTempList);
                System.out.println("ObjInfoHallController: envTempList"+envTempList);
            }
            if (flagBloodPressure01 == 1) {
                QueryResult bloodPressureResults =  influxDBConnector.queryData("select highPressure,lowPressure,heartRate from bloodPressure where objectId = "
                        +"'"+object.getObjectId()+"'"+" and time > "+timestampStart+" and time < "+timestampEnd);
//                System.out.println("ObjInfoHallController: bloodPressureResults: "+bloodPressureResults);
                if(bloodPressureResults.getResults().get(0).getSeries() == null){//如果值为空,全部赋0
//                    System.out.println("ObjInfoHallController: bloodPressureResults null");
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
                System.out.println("ObjInfoHallController: bloodPressure01TimeStampList"+bloodPressure01TimeStampList);
                System.out.println("ObjInfoHallController: highPressureList"+highPressureList);
                System.out.println("ObjInfoHallController: lowPressureList"+lowPressureList);
                System.out.println("ObjInfoHallController: heartRateList"+heartRateList);
            }
            if (flagBloodOxygen01 == 1) {
                QueryResult spo2Results =  influxDBConnector.queryData("select spo2 from bloodOxygen where objectId = "
                        +"'"+object.getObjectId()+"'"+" and time > "+timestampStart+" and time < "+timestampEnd);
//                System.out.println("ObjInfoHallController: spo2Results: "+spo2Results);
                if(spo2Results.getResults().get(0).getSeries() == null){//如果值为空,赋0
//                    System.out.println("ObjInfoHallController: spo2Results null");
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
                System.out.println("ObjInfoHallController: bloodOxygen01TimeStampList: "+bloodOxygen01TimeStampList);
                System.out.println("ObjInfoHallController: spo2List: "+spo2List);
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


        return "healthCenter/objInfoHallHistory";
    }

    //健康中心-X轴值设置
    @RequestMapping("/objInfoHallHistoryResult/AutoRefresh")
    @ResponseBody
    public Map<String,ArrayList<String>> monitorCenterAjaxGetData(@RequestBody ArrayList<ArrayList<String>> array) {
//        System.out.println("array: "+array);
//        System.out.println("ajaxTest");
        Map<String,ArrayList<String>> map = new HashMap<>();
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
        if (protocolState[netMaskId-1] == 1 ) {
            sendMsgQueue.get(netMaskId-1).offer(order);////added0524
        }
        else if(protocolState[netMaskId-1] == 2) {
            sendAMQPQueue.offer(netMaskId + order);
        }
        else {
            System.out.println("ObjInfoHallController: Cannot get info, the netMask owing the equipment is offline...");
        }
    }
}
