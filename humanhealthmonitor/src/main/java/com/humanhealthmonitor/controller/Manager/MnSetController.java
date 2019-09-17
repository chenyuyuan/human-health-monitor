package com.humanhealthmonitor.controller.Manager;

import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.service.AlarmNormalValueService;
import com.humanhealthmonitor.service.EquipmentIndexService;
import com.humanhealthmonitor.service.EquipmentTypeService;
import com.humanhealthmonitor.service.NetmaskInfoService;
import com.humanhealthmonitor.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MnSetController {
    @Autowired
    private NetmaskInfoService netmaskInfoService;
    @Autowired
    private EquipmentTypeService equipmentTypeService;
    @Autowired
    private AlarmNormalValueService alarmNormalValueService;
    @Autowired
    private EquipmentIndexService equipmentIndexService;

    //管理中心-网关设置
    @RequestMapping("/manageCenter/setNetMaskOverview")
    public String setNetMaskOverview(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //获取网关信息并传到页面
        List<NetmaskInfo> netMaskInfoList = netmaskInfoService.queryAllNetMaskInfo();
        request.setAttribute("netMaskInfoList", netMaskInfoList);

        return "manageCenter/setNetMaskOverview";
    }

    //管理中心-网关配置修改
    @RequestMapping("/manageCenter/setNetMaskModify")
    public String setNetMaskModify(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从表单中获取点击的netmaskId,查找相关信息并传到下一个页面
        int netMaskId = Integer.valueOf(request.getParameter("netMaskId"));
//        System.out.println(netMaskId);
        NetmaskInfo netmaskInfo = netmaskInfoService.queryNetMaskInfoByNetMaskId(netMaskId);
        request.setAttribute("netMask", netmaskInfo);
        //将ipAddress分割为4部分
//        System.out.println(netmaskInfo.getIpAddress());
        String ipAddress[] = netmaskInfo.getIpAddress().split("\\.");//分隔符是转义字符时必须在分隔符前加“\\”
//        for(int i = 0;i < ipAddress.length;i++) {
//            System.out.println(ipAddress[i]);
//        }
        request.setAttribute("ipAddress", ipAddress);
        if (netmaskInfo.getComProtocol().equals("AMQP")) {
            request.setAttribute("protocolOne", "AMQP");
            request.setAttribute("protocolTwo", "MODBUS");
        } else {
            request.setAttribute("protocolOne", "MODBUS");
            request.setAttribute("protocolTwo", "AMQP");
        }

        return "manageCenter/setNetMaskModify";
    }

    //管理中心-网关配置修改-结果
    @RequestMapping("/manageCenter/setNetMaskModifyResult")
    public String setNetMaskModifyResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从表单中获取更改后的网关信息
        int netMaskId = Integer.valueOf(request.getParameter("netMaskId"));
        String netMaskName = request.getParameter("netMaskName");
        String ipAddressPart1 = request.getParameter("ipAddressPart1");
        String ipAddressPart2 = request.getParameter("ipAddressPart2");
        String ipAddressPart3 = request.getParameter("ipAddressPart3");
        String ipAddressPart4 = request.getParameter("ipAddressPart4");
        String ipAddress = ipAddressPart1 + "." + ipAddressPart2 + "." + ipAddressPart3 + "." + ipAddressPart4;
        String comProtocol = request.getParameter("protocolSelected");
//        System.out.println(netMaskId);
//        System.out.println(netMaskName);
//        System.out.println(ipAddress);
//        System.out.println(comProtocol);

        //如果信息有更改，更新网关信息到数据库
        NetmaskInfo netmaskInfo = netmaskInfoService.queryNetMaskInfoByNetMaskId(netMaskId);
//        System.out.println(netmaskInfo.getNetmaskId()+"95");
//        System.out.println(netmaskInfo.getIpAddress()+"95");
//        System.out.println(netmaskInfo.getComProtocol()+"95");
        if (netmaskInfo.getNetmaskName().equals(netMaskName) && netmaskInfo.getIpAddress().equals(ipAddress)
                && netmaskInfo.getComProtocol().equals(comProtocol))//信息没有更改，提示未更改
        {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('您没有更改该网关任何配置')</script>");
//            return "manageCenter/setNetMaskModify";
        } else {
            netmaskInfo.setNetmaskName(netMaskName);
            netmaskInfo.setIpAddress(ipAddress);
            netmaskInfo.setComProtocol(comProtocol);

//            System.out.println(netmaskInfo.getNetmaskId()+"111");
//            System.out.println(netmaskInfo.getIpAddress()+"111");
//            System.out.println(netmaskInfo.getComProtocol()+"111");

            netmaskInfoService.updateNetMaskInfo(netmaskInfo);

            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('网关配置修改成功')</script>");
        }
        //设置并返回网关信息总览页面
        List<NetmaskInfo> netMaskInfoList = netmaskInfoService.queryAllNetMaskInfo();
        request.setAttribute("netMaskInfoList", netMaskInfoList);
        return "manageCenter/setNetMaskOverview";
    }

    //管理中心-添加网关
    @RequestMapping("/manageCenter/setNetMaskAdd")
    public String setNetMaskAdd(HttpServletRequest request, HttpServletResponse response) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        return "manageCenter/setNetMaskAdd";
    }

    //管理中心-添加网关-结果
    @RequestMapping("/manageCenter/setNetMaskAddResult")
    public String setNetMaskAddResult(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面获取要添加的网关信息
        String netMaskName = request.getParameter("netMaskName");
        String ipAddressPart1 = request.getParameter("ipAddressPart1");
        String ipAddressPart2 = request.getParameter("ipAddressPart2");
        String ipAddressPart3 = request.getParameter("ipAddressPart3");
        String ipAddressPart4 = request.getParameter("ipAddressPart4");
        String ipAddress = ipAddressPart1 + "." + ipAddressPart2 + "." + ipAddressPart3 + "." + ipAddressPart4;
        String comProtocol = request.getParameter("protocolSelected");
//        System.out.println(netMaskName);
//        System.out.println(ipAddress);
//        System.out.println(comProtocol);

        //如果网关ip不与原来的冲突，就在数据库中添加网关
        if (netmaskInfoService.queryNetMaskInfoByIpAddress(ipAddress) != null) {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('网关地址与已有网关地址冲突，请重新设置')</script>");

            return "manageCenter/setNetMaskAdd";
        } else {
            NetmaskInfo netmaskInfo = new NetmaskInfo();
            netmaskInfo.setNetmaskName(netMaskName);
            netmaskInfo.setIpAddress(ipAddress);
            netmaskInfo.setComProtocol(comProtocol);
            netmaskInfo.setState("正常");
//            System.out.println("Line 172");
            netmaskInfoService.insertNetmaskInfo(netmaskInfo);
//            System.out.println("Line 174");
            //网关添加成功提示
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('网关添加成功！')</script>");
//            System.out.println("Line 179");
            //获取网关信息并传到页面
            List<NetmaskInfo> netMaskInfoList = netmaskInfoService.queryAllNetMaskInfo();
            request.setAttribute("netMaskInfoList", netMaskInfoList);

            return "manageCenter/setNetMaskOverview";
        }

    }


    //管理中心-设备与默认警报值-总览
    @RequestMapping("/manageCenter/setEquipmentOverview")
    public String setEquipmentOverview(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //获取设备类型信息并传到页面
        List<EquipmentType> equipmentTypeList = equipmentTypeService.queryAllEquipmentType();
        request.setAttribute("equipmentTypeList", equipmentTypeList);

        return "manageCenter/setEquipmentOverview";
    }

    //管理中心-设备与默认警报值-修改信息
    @RequestMapping("/manageCenter/setEquipmentModifyInfo")
    public String setEquipmentModifyInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //获取设备类型，查询设备类型及警报值信息并传到页面
        String eqpType = request.getParameter("eqpType");
        EquipmentType equipmentType = equipmentTypeService.queryEquipmentTypeByEqpType(eqpType);
        request.setAttribute("equipmentType", equipmentType);
        List<AlarmNormalValue> alarmValueList = alarmNormalValueService.queryAlarmNormalValueByEqpType(eqpType);
        request.setAttribute("alarmValueList", alarmValueList);

        return "manageCenter/setEquipmentModifyInfo";
    }

    //管理中心-设备与默认警报值-保存设备简介
    @RequestMapping("/manageCenter/setEquipmentModifyInfoSave")
    public String setEquipmentModifyInfoSave(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //获取设备类型和简介，更新数据库
        String eqpType = request.getParameter("eqpTypeShow");
        String introduction = request.getParameter("introduction");
        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setEqpType(eqpType);
        equipmentType.setIntroduction(introduction);
        equipmentTypeService.updateEquipmentTypeIntroduction(equipmentType);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('更新简介成功！')</script>");

        equipmentType = equipmentTypeService.queryEquipmentTypeByEqpType(eqpType);
        request.setAttribute("equipmentType", equipmentType);
        List<AlarmNormalValue> alarmValueList = alarmNormalValueService.queryAlarmNormalValueByEqpType(eqpType);
        request.setAttribute("alarmValueList", alarmValueList);

        return "manageCenter/setEquipmentModifyInfo";
    }

    //管理中心-设备与默认警报值-修改默认警报值
    @RequestMapping("/manageCenter/setEquipmentModifyDefaultValue")
    public String setEquipmentModifyDefaultValue(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //根据获取的eqpType查询默认警报值并传到页面
        String eqpType = request.getParameter("eqpTypeToModifyAlarm");
        request.setAttribute("eqpType", eqpType);
        List<AlarmNormalValue> alarmValueList = alarmNormalValueService.queryAlarmNormalValueByEqpType(eqpType);
        request.setAttribute("alarmValueList", alarmValueList);
        return "manageCenter/setEquipmentModifyDefaultValue";
    }

    //管理中心-设备与默认警报值-修改默认警报值
    @RequestMapping("/manageCenter/setEquipmentModifyDefaultValueSave")
    public String setEquipmentModifyDefaultValueSave(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //获取设备种类默认警戒值顺序号，更新该默认警戒值
        String eqpType = request.getParameter("eqpTypeInModifyAlarmValue");
//        System.out.println(eqpType);
        int serial = Integer.valueOf(request.getParameter("alarmValueSelected"));
//        System.out.println(serial);
        float value = Float.valueOf(request.getParameter("newValue"));
//        System.out.println(value);
        AlarmNormalValue alarmNormalValue = new AlarmNormalValue();
        alarmNormalValue.setEqpType(eqpType);
        alarmNormalValue.setSerial(serial);
        alarmNormalValue.setValue(value);
        alarmNormalValueService.updateAlarmNormalValueDefault(alarmNormalValue);

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print("<script language=\"javascript\">alert('修改成功！')</script>");

        request.setAttribute("eqpType", eqpType);
        List<AlarmNormalValue> alarmValueList = alarmNormalValueService.queryAlarmNormalValueByEqpType(eqpType);
        request.setAttribute("alarmValueList", alarmValueList);

        return "manageCenter/setEquipmentModifyDefaultValue";
    }

    //管理中心-设备与默认警报值-添加设备种类
    @RequestMapping("/manageCenter/setEquipmentAddNewType")
    public String setEquipmentAddNewType(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        return "manageCenter/setEquipmentAddNewType";
    }

    //管理中心-设备与默认警报值-添加设备种类-保存类型信息-指标设置页面
    @RequestMapping("/manageCenter/setEquipmentAddEqpTypeIndex")
    public String setEquipmentAddEqpTypeIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从setEquipmentAddNewType页面获取信息，添加设备类型并引导默认警报值的设置
        String eqpType = request.getParameter("eqpType");
        String stringMatch = request.getParameter("stringMatch");
        int indexTotal = Integer.valueOf(request.getParameter("indexTotal"));
        int alarmSerialMax = Integer.valueOf(request.getParameter("alarmSerialMax"));
        String introduction = request.getParameter("introduction");
//        System.out.println(eqpType);
//        System.out.println(stringMatch);
//        System.out.println(indexTotal);
//        System.out.println(alarmSerialMax);
//        System.out.println(introduction);
        //查询设备类型、匹配字符串是否与已有的冲突
        if (equipmentTypeService.queryEquipmentTypeByEqpType(eqpType) != null)//设备类型冲突
        {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('添加失败，该设备类型已存在！');javascript:history.back(-1);</script>");
        } else if (equipmentTypeService.queryEquipmentTypeByStringMatch(stringMatch) != null)//匹配字符串冲突
        {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('添加失败，该匹配字符串已存在，请重新设定！');javascript:history.back(-1);</script>");
        } else {
            EquipmentType equipmentType = new EquipmentType();
            equipmentType.setEqpType(eqpType);
            equipmentType.setStringMatch(stringMatch);
            equipmentType.setIndexTotal(indexTotal);
            equipmentType.setAlarmSerialMax(alarmSerialMax);
            equipmentType.setIntroduction(introduction);
            equipmentTypeService.insertEquipmentType(equipmentType);
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('添加成功！');</script>");

            /////////////////////////////后面涉及到创建新的influxDB表格的问题////////////////////////////////////////

            //根据指标总数引导用户设置指标
            request.setAttribute("equipmentType", equipmentType);
            request.setAttribute("indexSeq", 1);//这里设置第一个指标，一定会执行，因为一个设备至少一个指标
        }
        return "manageCenter/setEquipmentAddEqpTypeIndex";
    }

    //管理中心-设备与默认警报值-添加设备种类-保存类型信息-指标设置页面-下一步
    @RequestMapping("/manageCenter/setEquipmentAddEqpTypeIndexNext")
    public String setEquipmentAddEqpTypeIndexNext(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面取值
        String eqpType = request.getParameter("eqpTypeHidden");
        int indexSeq = Integer.valueOf(request.getParameter("indexSeqHidden"));
        int indexTotal = Integer.valueOf(request.getParameter("indexTotalHidden"));
        String indexName = request.getParameter("indexName");
        String measurementUnit = request.getParameter("measurementUnit");

        //插入新指标到设备指标表中
        EquipmentIndex equipmentIndex = new EquipmentIndex();
        equipmentIndex.setEqpType(eqpType);
        equipmentIndex.setIndexSeq(indexSeq);
        equipmentIndex.setIndexName(indexName);
        equipmentIndex.setMeasurementUnit(measurementUnit);
        equipmentIndexService.insertEquipmentIndex(equipmentIndex);

        //判断是不是最后一个指标从而进入不同页面
        if (indexSeq < indexTotal) {
            request.setAttribute("equipmentType", equipmentTypeService.queryEquipmentTypeByEqpType(eqpType));
            request.setAttribute("indexSeq", (indexSeq + 1));

            return "manageCenter/setEquipmentAddEqpTypeIndex";
        } else {
            //进入默认警戒值设置页面
            request.setAttribute("eqpType", eqpType);
            request.setAttribute("alarmSerialMax", equipmentTypeService.queryAlarmSerialMaxByEqpType(eqpType));
            request.setAttribute("serial", 1);//这里设置第一个警报界限，一定会执行，因为一个设备至少一个警报界限
            request.setAttribute("equipmentIndexList", equipmentIndexService.queryAllEquipmentIndexByEqpType(eqpType));
            return "manageCenter/setEquipmentAddDefaultValue";
        }

    }

    //管理中心-设备与默认警报值-添加设备种类-保存类型信息-默认警报值设置页面
    @RequestMapping("/manageCenter/setEquipmentAddDefaultValue")
    public String setEquipmentAddDefaultValue(HttpServletRequest request, HttpServletResponse response) throws IOException, NullPointerException {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        request.setAttribute("admin", admin);

        //从页面取值
        String valueName = request.getParameter("valueName");
        String eqpType = request.getParameter("eqpTypeHidden");
        int indexSeq = Integer.valueOf(request.getParameter("indexSelected"));
//        System.out.println(eqpType);
        int serial = Integer.valueOf(request.getParameter("serialHidden"));
        int alarmSerialMax = Integer.valueOf(request.getParameter("alarmSerialMaxHidden"));
        String valueTypeFlag = request.getParameter("valueTypeSelected");
        float value = Float.valueOf(request.getParameter("value"));
        boolean valueType;
        if (valueTypeFlag.equals("true")) {
            valueType = true;
        } else {
            valueType = false;
        }
        AlarmNormalValue alarmNormalValue = new AlarmNormalValue();
        alarmNormalValue.setValueName(valueName);
        alarmNormalValue.setEqpType(eqpType);
        alarmNormalValue.setIndexSeq(indexSeq);
        alarmNormalValue.setSerial(serial);
        alarmNormalValue.setValueType(valueType);
        alarmNormalValue.setValue(value);
        EquipmentIndex equipmentIndex = equipmentIndexService.queryEquipmentIndexByEqpTypeAndIndexSeq(eqpType, indexSeq);
        alarmNormalValue.setMeasurementUnit(equipmentIndex.getMeasurementUnit());
        alarmNormalValueService.insertAlarmNormalValue(alarmNormalValue);

        //根据serial和alarmSerialMax的大小关系返回不同页面
        if (serial < alarmSerialMax) {
            //进入默认警戒值设置页面设置下一个
            request.setAttribute("eqpType", eqpType);
            request.setAttribute("alarmSerialMax", equipmentTypeService.queryAlarmSerialMaxByEqpType(eqpType));
            request.setAttribute("serial", (serial + 1));
            request.setAttribute("equipmentIndexList", equipmentIndexService.queryAllEquipmentIndexByEqpType(eqpType));
            return "manageCenter/setEquipmentAddDefaultValue";
        } else//所有警报界限设置完毕，返回设备类型总览页
        {
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.print("<script language=\"javascript\">alert('恭喜您！新的设备类型已完成所有设置');</script>");
            //获取设备类型信息并传到页面
            List<EquipmentType> equipmentTypeList = equipmentTypeService.queryAllEquipmentType();
            request.setAttribute("equipmentTypeList", equipmentTypeList);
            return "manageCenter/setEquipmentOverview";
        }
    }
}
