package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.InfluxDBConnector;
import com.humanhealthmonitor.pojo.EquipmentType;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.service.*;
import com.humanhealthmonitor.MsgQueue;
import com.humanhealthmonitor.pojo.Equipment;
import com.humanhealthmonitor.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

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
    @GetMapping(value = "/infoHallOnTime")
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
        List<EquipmentType> eqpTypeList = equipmentTypeService.queryAllEquipmentType();
        Collections.reverse(eqpTypeList);//没什么意义，就是把四个类型换一下位置
        request.setAttribute("eqpTypeList", eqpTypeList);


        return "monitorCenter/infoHallOnTime";
    }
    @GetMapping(value = "/infoHallOnTime/{objectId}")
    public String infoHallOnTimeChoose(HttpServletRequest request, HttpServletResponse response, @PathVariable String objectId) {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        System.out.println("<<<<infoHallOnTimeNew>>>>:objectId " + objectId);

        //从数据库中获取所有该用户关联的监测对象并传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);

        //获取第一个对象的id并把他所绑定的监测设备信息传到前台
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByObjectId(objectList.get(0).getObjectId());
        request.setAttribute("equipmentList", equipmentList);
        request.setAttribute("objectNameSelected", objectList.get(0).getObjectName());
        List<EquipmentType> eqpTypeList = equipmentTypeService.queryAllEquipmentType();
        Collections.reverse(eqpTypeList);//没什么意义，就是把四个类型换一下位置
        request.setAttribute("eqpTypeList", eqpTypeList);


        return "monitorCenter/infoHallOnTime";
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
