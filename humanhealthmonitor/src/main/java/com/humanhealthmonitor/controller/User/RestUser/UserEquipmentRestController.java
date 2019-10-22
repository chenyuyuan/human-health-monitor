package com.humanhealthmonitor.controller.User.RestUser;

import com.alibaba.fastjson.JSONObject;
import com.humanhealthmonitor.MsgQueue;
import com.humanhealthmonitor.pojo.Equipment;
import com.humanhealthmonitor.pojo.EquipmentType;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class UserEquipmentRestController {
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

    @CrossOrigin
    @RequestMapping(value = "/monitorCenter/equipmentAddResult", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap receive(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response)
            throws IOException, NullPointerException, InterruptedException {
        System.out.println("[UserEquipmentRestController]:");
        //String content = params.getString("content");

        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);


        String eqpType = params.getString("eqpTypeSelected");
        String eqpId = params.getString("eqpId");
        String eqpName = params.getString("eqpName");
        String objectId = params.getString("objectSelected");
        System.out.print(eqpType + " " + eqpId + " " + eqpName + " " + objectId + "\n");

        //如果查找设备号为空则尝试添加，不为空且objectId不为null则提示设备已绑定，不为空但objectId为null说明设备已经注册但被解除了绑定，需要更新绑定的监测对象
        Equipment eqp = equipmentService.queryEquipmentByEqpId(eqpId);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~got parameter");
        if (eqp == null) {
            int flagAdd;//标识设备添加是否成功,0失败，1成功
            //验证该设备类型统一模式的匹配字符串
            String typeMatchString = equipmentTypeService.queryEquipmentTypeByEqpType(eqpType).getStringMatch();
            System.out.println("[UserEquipmentRestController]: typeMatchString: "+typeMatchString+" length: "+typeMatchString.length());
            if(eqpId.length() == typeMatchString.length() && eqpId.charAt(0) == typeMatchString.charAt(0) &&
                    eqpId.charAt(5) == typeMatchString.charAt(5) && eqpId.charAt(6) == typeMatchString.charAt(6)) {
                //验证通过，开始向网关发送命令查询设备
                //组装查询命令
                String startStr = "FEFE";
                String dataLengthStr = "07";//有效数据字节数
                // String repairString = "0";//补齐字节
                String orderTypeStr = "02";
                String endStr = "AABB";
                for(int i = 0;i <32;i++) {
                    if(MsgQueue.protocolState[i] == 1 || MsgQueue.protocolState[i] == 2) {
                        String netMaskIdStr = Integer.toHexString(i+1);
                        if(netMaskIdStr.length() == 1) {
                            netMaskIdStr = "0"+netMaskIdStr;
                        }
                        int checkCal = ( i + 1 ) + 2 + Integer.parseInt("0"+eqpId.charAt(0),16) +
                                Integer.parseInt(eqpId.substring(1,3),16) + Integer.parseInt(eqpId.substring(3,5),16) +
                                Integer.parseInt(eqpId.substring(5,7),16) + Integer.parseInt(eqpId.substring(7,9),16);
                        checkCal = Math.abs(checkCal)%64;//计算校验和
                        String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                        if(checkCalStr.length()==1) {
                            checkCalStr = "0" + checkCalStr;
                        }
                        String deviceRegisterOrder = startStr + dataLengthStr + netMaskIdStr + orderTypeStr +
                                eqpId + checkCalStr + endStr;
                        //根据该网关使用的协议发送查询命令
                        sendMessage(i+1,deviceRegisterOrder);//网关验证注册时使用此语句，否则注释掉
                        System.out.println("UserEquipmentController: deviceRegisterOrder"+(i+1)+": "+deviceRegisterOrder);
                    }
                    else {
                        // System.out.println("NetMask["+(i+1)+"] is unregistered or offline...");
                    }
                }
                //等待返回设备添加结果，sleep一段时间后查找数据库，如果查到了就返回注册成功//网关验证注册时使用此块语句
                System.out.println("UserEquipmentController: start waiting for the result of adding equipment...");
                Thread.sleep(15000);//等待15秒
                System.out.println("UserEquipmentController: the result of adding equipment is ready to get...");
                //查询是否能在数据库中找到，找到了flagAdd置1，找不到置0
                Equipment newAddEquipment = equipmentService.queryEquipmentByEqpId(eqpId);
                if(newAddEquipment == null) {
                    flagAdd = 0;
                }
                else {
                    flagAdd = 1;
                }

                //flagAdd=1;//非网关验证注册，而是直接注册时使用此语句

                //查数据库决定成败
                if(flagAdd == 1) {
                    Equipment newEquipment = new Equipment();
                    newEquipment.setEqpId(eqpId);
                    newEquipment.setEqpName(eqpName);
                    newEquipment.setObjectId(objectId);
                    newEquipment.setEqpType(eqpType);
                    newEquipment.setSpecial(false);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String registerDate = dateFormat.format(System.currentTimeMillis()).substring(0, 10);
                    newEquipment.setRegisterDate(java.sql.Date.valueOf(registerDate));
                    newEquipment.setNetmaskId(1);
                    newEquipment.setDeviceSerial(-1);

                    //equipmentService.insertEquipment(newEquipment);//非网关验证注册，而是直接注册时使用此语句

                    //更新设备名称、绑定人和设备类型，因为网关传来的只有eqpId，设备类型可以根据eqpId分析得出，可在解析下位机命令后添加
                    equipmentService.updateEquipmentName(newEquipment);//added0601
                    equipmentService.updateEquipmentObject(newEquipment);
                    equipmentService.updateEquipmentType(newEquipment);//comment0601

                    response.setContentType("text/html;charset=utf-8");
                    System.out.println("[UserEquipmentRestController]: before getWriter?????");
                    PrintWriter out = response.getWriter();
                    System.out.println("[UserEquipmentRestController]: after getWriter?????");
                    out.print("<script language=\"javascript\">alert('设备添加成功！');</script>");
                }
                else {
                    response.setContentType("text/html;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    out.print("<script language=\"javascript\">alert('设备添加失败！请检查网关号是否正确、网络连通是否畅通后重新尝试添加');</script>");
                }
            }else{
                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
                out.print("<script language=\"javascript\">alert('您所输入的设备号与设备类型不匹配！请输入正确的设备号或更改设备类型！');</script>");
            }
        } else if (eqp.getObjectId() != null) {
            //eqp不为null且有绑定的监测对象
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('该设备已被绑定，请解绑此设备后再进行绑定操作');</script>");
        } else {
            //eqp不为null且没有绑定的监测对象
            eqp.setObjectId(objectId);
            eqp.setEqpName(eqpName);
            equipmentService.updateEquipmentObject(eqp);

            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('已添加设备，已为您重新绑定设备到该监测对象');</script>");
        }
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);
        List<EquipmentType> eqpTypeList = equipmentTypeService.queryAllEquipmentType();
        request.setAttribute("eqpTypeList", eqpTypeList);





        HashMap res = new HashMap();
        res.put("msg","success");

        return res;
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
