package com.humanhealthmonitor.controller.User;

import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.service.*;
import com.humanhealthmonitor.MsgQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.text.SimpleDateFormat;

import static com.humanhealthmonitor.util.ByteUtils.*;

@Controller
public class UserEquipmentController {
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

    //监测中心-添加设备
    @RequestMapping("/monitorCenter/equipmentAdd")
    public String equipmentAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //如果没有添加监测对象，提示先添加监测对象
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        if (objectList == null) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('没有监测对象可供设备绑定，请先去添加监测对象！');</script>");
            return "indexUser";
        } else {
            request.setAttribute("objectList", objectList);
            List<EquipmentType> eqpTypeList = equipmentTypeService.queryAllEquipmentType();
            request.setAttribute("eqpTypeList", eqpTypeList);
            return "monitorCenter/equipmentAdd";
        }
    }

    //监测中心-添加设备
    @RequestMapping("/monitorCenter/newEquipmentAdd")
    public String newEquipmentAdd(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //如果没有添加监测对象，提示先添加监测对象
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        if (objectList == null) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('没有监测对象可供设备绑定，请先去添加监测对象！');</script>");
            return "indexUser";
        } else {
            request.setAttribute("objectList", objectList);
            List<EquipmentType> eqpTypeList = equipmentTypeService.queryAllEquipmentType();
            request.setAttribute("eqpTypeList", eqpTypeList);
            return "monitorCenter/newEquipmentAdd";
        }
    }

    //监测中心-添加设备结果
    @RequestMapping("/monitorCenter/equipmentAddResult/StopUsingThis")
    public String equipmentAddResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException, InterruptedException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //获取页面输入值
        String eqpType = request.getParameter("eqpTypeSelected");
        String eqpId = request.getParameter("eqpId");
        String eqpName = request.getParameter("eqpName");
        String objectId = request.getParameter("objectSelected");

        //如果查找设备号为空则尝试添加，不为空且objectId不为null则提示设备已绑定，不为空但objectId为null说明设备已经注册但被解除了绑定，需要更新绑定的监测对象
        Equipment eqp = equipmentService.queryEquipmentByEqpId(eqpId);
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~got parameter");
        if (eqp == null) {
            int flagAdd;//标识设备添加是否成功,0失败，1成功
            //验证该设备类型统一模式的匹配字符串
            String typeMatchString = equipmentTypeService.queryEquipmentTypeByEqpType(eqpType).getStringMatch();
            System.out.println("UserEquipmentController: typeMatchString: "+typeMatchString+" length: "+typeMatchString.length());
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
                        if(netMaskIdStr.length() % 2 == 1) {
                            netMaskIdStr = "0"+netMaskIdStr;
                        }
                        int checkCal = 2 + Integer.parseInt("0"+eqpId.charAt(0),16) +
                                Integer.parseInt(eqpId.substring(1,3),16) + Integer.parseInt(eqpId.substring(3,5),16) +
                                Integer.parseInt(eqpId.substring(5,7),16) + Integer.parseInt(eqpId.substring(7,9),16);
                        checkCal = Math.abs(checkCal)%64;//计算校验和
                        String checkCalStr = Integer.toHexString(checkCal).toUpperCase();
                        if(checkCalStr.length()==1) {
                            checkCalStr = "0" + checkCalStr;
                        }
                        byte[] netMaskIdLengthChar = new byte[1];
                        netMaskIdLengthChar[0] = (byte)netMaskIdStr.length();
                        String netMaskIdLength = byteArrayToString(netMaskIdLengthChar, 16);
                        String deviceRegisterOrder = startStr + dataLengthStr + netMaskIdLength + netMaskIdStr + orderTypeStr +
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
                    PrintWriter out = response.getWriter();
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
        return "monitorCenter/equipmentAdd";
    }

    //监测中心-设备信息管理
    @RequestMapping("/monitorCenter/equipmentInfoManage")
    public String equipmentInfoManage(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //取出用户关联的监测对象的所有设备信息
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByUserId(user.getUserId());

        // System.out.println("129");
        if (equipmentList.size() == 0) {//如果没有设备，提示先添加设备
        // System.out.println("132");
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('没有设备可管理，请先添加设备！');</script>");
            return "indexUser";
        } else {
            request.setAttribute("equipmentList", equipmentList);
            Equipment equipment = equipmentList.get(0);
            request.setAttribute("equipment", equipment);

            //判断设备是否有特殊警报值，如果没有就取默认警报值
            judgeEqpAlarmValue(request, equipment);

            return "monitorCenter/equipmentInfoManage";
        }
    }

    //监测中心-设备信息管理-设备选择
    @RequestMapping("/monitorCenter/equipmentInfoManageSelect")
    public String equipmentInfoManageSelect(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //取出用户关联的监测对象的所有设备信息
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByUserId(user.getUserId());
        request.setAttribute("equipmentList", equipmentList);
        //取出用户点选的设备并返回设备信息
        String eqpIdSelected = request.getParameter("eqpId");
        Equipment equipment = equipmentService.queryEquipmentByEqpId(eqpIdSelected);
        request.setAttribute("equipment", equipment);

        //判断设备是否有特殊警报值，如果没有就取默认警报值
        judgeEqpAlarmValue(request, equipment);

        return "monitorCenter/equipmentInfoManage";
    }

    //监测中心-设备信息管理-信息修改
    @RequestMapping("/monitorCenter/equipmentInfoModify")
    public String equipmentInfoModify(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //取回页面修改后的设备名称并更新数据库
        String newEqpName = request.getParameter("eqpName");
        String eqpIdShow = request.getParameter("eqpIdShow");
        // System.out.println(newEqpName);
        // System.out.println(eqpIdShow);
        Equipment equipmentModify = new Equipment();
        equipmentModify.setEqpName(newEqpName);
        equipmentModify.setEqpId(eqpIdShow);

        Equipment equipment = equipmentService.queryEquipmentByEqpId(eqpIdShow);
        if (newEqpName.equals(equipment.getEqpName())) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('设备名称未作改动！');</script>");
        } else {
            //修改设备名称
            equipmentService.updateEquipmentName(equipmentModify);
            //提示用户修改成功
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('设备名称修改成功！');</script>");
        }

        //取出用户关联的监测对象的所有设备信息
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByUserId(user.getUserId());
        request.setAttribute("equipmentList", equipmentList);
        //取出设备信息并返回页面
        equipment = equipmentService.queryEquipmentByEqpId(eqpIdShow);
        request.setAttribute("equipment", equipment);

        //判断设备是否有特殊警报值，如果没有就取默认警报值
        judgeEqpAlarmValue(request, equipment);

        return "monitorCenter/equipmentInfoManage";
    }

    //监测中心-修改绑定
    @RequestMapping("/monitorCenter/modifyBanding")
    public String modifyBanding(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //把对象表和设备信息传到前台
        List<Object> objectList = objectService.queryAllObjectByUserId(user.getUserId());
        request.setAttribute("objectList", objectList);

        //从equipmentInfoManage页面获取设备Id再传到本页面
        String eqpId = request.getParameter("eqpIdToModify");
//        System.out.println(eqpId);
//        Equipment equipment = equipmentService.queryEquipmentByEqpId(eqpId);
        request.setAttribute("eqpId", eqpId);

        return "monitorCenter/modifyBanding";
    }

    //监测中心-修改绑定-结果
    @RequestMapping("/monitorCenter/modifyBandingResult")
    public String unBandingResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);


        //本页面还有一个返回信息管理页面的按钮和一个仅解除绑定按钮（对应一个新的映射，然后返回设备管理，提示用户这样做会需要重新注册设备，注意判断如果没有设备，仍然提示先添加设备），
        //取回页面设备Id和下拉列表选中的对象Id
        String eqpId = request.getParameter("eqpIdInModifyBanding");
        String newObjectId = request.getParameter("objectSelected");
        // System.out.println(eqpId);
        // System.out.println(newObjectId);
        if (newObjectId.equals(equipmentService.queryEquipmentByEqpId(eqpId).getObjectId())) {//对象没变，提示
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('绑定对象未作改动！');</script>");
        } else {//对象变了，数据库操作完成后提示修改成功然后返回设备信息管理页面
            Equipment equipment = new Equipment();
            equipment.setEqpId(eqpId);
            equipment.setObjectId(newObjectId);
            equipmentService.updateEquipmentOnlyObject(equipment);

            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('更改绑定成功！');</script>");
        }

        //取出用户关联的监测对象的所有设备信息
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByUserId(user.getUserId());

        request.setAttribute("equipmentList", equipmentList);
        Equipment equipment = equipmentList.get(0);
        request.setAttribute("equipment", equipment);

        //判断设备是否有特殊警报值，如果没有就取默认警报值
        judgeEqpAlarmValue(request, equipment);

        return "monitorCenter/equipmentInfoManage";

    }

    //监测中心-修改绑定-高级选项-仅解除绑定
    @RequestMapping("/monitorCenter/onlyUnBanding")
    public String onlyUnBanding(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //取回页面设备Id
        String eqpId = request.getParameter("eqpIdInModifyBanding");

        // 👇modify by yuan
        // // 设置objectId为null以解除绑定
        Equipment equipment = new Equipment();
        equipment.setEqpId(eqpId);
        equipmentService.updateEquipmentOnlyObject(equipment);
        // 改为删除
        // equipmentService.deleteEquipmentOnlyObject(epqId);




        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('解除绑定成功！');</script>");

        // judgeIfHasEquipmentAndSetPage(request,response,user);
        // 取出用户关联的监测对象的所有设备信息
        List<Equipment> equipmentList = equipmentService.queryAllEquipmentByUserId(user.getUserId());

        if (equipmentList.size() == 0) {//如果没有设备，提示先添加设备
            response.setContentType("text/html;charset=utf-8");
//            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('没有设备可管理，请先添加设备！');</script>");
            return "indexUser";
        } else {
            request.setAttribute("equipmentList", equipmentList);
//            Equipment equipment = equipmentList.get(0);
            equipment = equipmentList.get(0);
            request.setAttribute("equipment", equipment);

        }

        //判断设备是否有特殊警报值，如果没有就取默认警报值
        judgeEqpAlarmValue(request, equipment);

        return "monitorCenter/equipmentInfoManage";
    }


    //监测中心-修改警戒值
    @RequestMapping("/monitorCenter/modifyAlarmValue")
    public String modifyAlarmValue(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从上一页面取出设备号
        String eqpId = request.getParameter("eqpIdToModifyAlarm");
        request.setAttribute("eqpId", eqpId);
//        System.out.println(eqpId+"UserEquipmentController Line331");

        Equipment equipment = equipmentService.queryEquipmentByEqpId(eqpId);
        //判断设备是否有特殊警报值，如果没有就取默认警报值
        judgeEqpAlarmValue(request, equipment);

        return "monitorCenter/modifyAlarmValue";
    }

    //监测中心-修改警戒值-结果
    @RequestMapping("/monitorCenter/modifyAlarmValueResult")
    public String modifyAlarmValueResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从页面取出设备号，警戒值顺序号和新的警戒值
        String eqpId = request.getParameter("eqpIdInModifyAlarmValue");
        int serial = Integer.parseInt(request.getParameter("alarmValueSelected"));
        float newValue = Float.parseFloat(request.getParameter("newValue"));

        Equipment equipment = equipmentService.queryEquipmentByEqpId(eqpId);
        if (equipment.getSpecial()) {//如果设备的special值本来就是1，那么直接查找并修改特殊值表
            AlarmSpecialValue alarmSpecialValue = new AlarmSpecialValue();
            alarmSpecialValue.setEqpId(eqpId);
            alarmSpecialValue.setSerial(serial);
            alarmSpecialValue.setValue(newValue);
            alarmSpecialValueService.updateAlarmSpecialValue(alarmSpecialValue);
        } else {//否则，把设备的special值置1，插入一组该设备的警戒值到特殊警报值数据库
            equipment.setSpecial(true);
            equipmentService.updateEquipmentSpecial(equipment);
            List<AlarmNormalValue> alarmNormalValueList = alarmNormalValueService.queryAlarmNormalValueByEqpType(equipment.getEqpType());
            AlarmSpecialValue alarmSpecialValue = new AlarmSpecialValue();
            for (int i = 0; i < alarmNormalValueList.size(); i++) {//拷贝默认值到特殊值表
                alarmSpecialValue.setValueName(alarmNormalValueList.get(i).getValueName());
                alarmSpecialValue.setEqpId(eqpId);
                alarmSpecialValue.setIndexSeq(alarmNormalValueList.get(i).getIndexSeq());//added in 16/03/2019
                alarmSpecialValue.setSerial(alarmNormalValueList.get(i).getSerial());
                alarmSpecialValue.setValueType(alarmNormalValueList.get(i).getValueType());
                alarmSpecialValue.setValue(alarmNormalValueList.get(i).getValue());
                alarmSpecialValue.setMeasurementUnit(alarmNormalValueList.get(i).getMeasurementUnit());
                alarmSpecialValueService.insertAlarmSpecialValue(alarmSpecialValue);
            }
            //设置用户更改的默认值
            alarmSpecialValue.setEqpId(eqpId);
            alarmSpecialValue.setSerial(serial);
            alarmSpecialValue.setValue(newValue);
            alarmSpecialValueService.updateAlarmSpecialValue(alarmSpecialValue);
        }

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('更新警报界限信息成功！');</script>");

        //准备返回页面
        request.setAttribute("eqpId", eqpId);
        List<AlarmSpecialValue> alarmSpecialValueList = alarmSpecialValueService.queryAlarmSpecialValueByEqpId(equipment.getEqpId());
        request.setAttribute("alarmValueList", alarmSpecialValueList);

        return "monitorCenter/modifyAlarmValue";
    }

    //监测中心-修改警戒值-警戒值全部重置为默认值
    @RequestMapping("/monitorCenter/modifyAlarmValueReset")
    public String modifyAlarmValueReset(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);

        //从页面取出设备号
        String eqpId = request.getParameter("eqpIdInModifyAlarmValueReset");

        Equipment equipment = equipmentService.queryEquipmentByEqpId(eqpId);
        if (!equipment.getSpecial()) {//如果special值为0，提示该设备未设置特殊警报值，不需要重置。
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('该设备未设置特殊警报值，不需要重置。');</script>");
        } else {//如果special值为1，删除special数据库中相应设备的特殊警报值,special置0
            alarmSpecialValueService.deleteAlarmSpecialValueByEqpId(eqpId);
            equipment.setSpecial(false);
            equipmentService.updateEquipmentSpecial(equipment);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('警报界限值重置成功！');</script>");
        }
        //准备返回页面
        request.setAttribute("eqpId", eqpId);
        List<AlarmNormalValue> alarmNormalValueList = alarmNormalValueService.queryAlarmNormalValueByEqpType(equipment.getEqpType());
        request.setAttribute("alarmValueList", alarmNormalValueList);
        return "monitorCenter/modifyAlarmValue";
    }


    //判断设备是否有特殊警报值，如果没有就取默认警报值
    private void judgeEqpAlarmValue(HttpServletRequest request, Equipment equipment) {
        // 取出设备警戒值，首先判断special字段，为0则从mormal表中取值，为1则从special表里取值，alarmSpecialValue作为暂存实例
        // AlarmSpecialValue alarmValue = new AlarmSpecialValue();
        if (!equipment.getSpecial()) { // 取默认值
            List<AlarmNormalValue> alarmNormalValueList = alarmNormalValueService.queryAlarmNormalValueByEqpType(equipment.getEqpType());
            request.setAttribute("alarmValueList", alarmNormalValueList);
        } else {
            List<AlarmSpecialValue> alarmSpecialValueList = alarmSpecialValueService.queryAlarmSpecialValueByEqpId(equipment.getEqpId());
            request.setAttribute("alarmValueList", alarmSpecialValueList);
        }
    }

    //向网关发送获取数据的命令
    public void sendMessage(int netMaskId, String order) {
        if (MsgQueue.protocolState[netMaskId - 1] == 1 ) {
            MsgQueue.sendMsgQueue.get(netMaskId - 1).offer(order);////added0524
        } else if (MsgQueue.protocolState[netMaskId - 1] == 2) {
            MsgQueue.sendAMQPQueue.offer(netMaskId + order);
        } else {
            System.out.println("ObjInfoHallController: Cannot get info, the netMask owing the equipment is offline...");
        }
    }
}
