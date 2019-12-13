package com.humanhealthmonitor.controller.RestController;

import com.alibaba.fastjson.JSONObject;
import com.humanhealthmonitor.MsgQueue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Hashtable;

import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;

@RestController
public class TestRestController {
    @CrossOrigin
    @RequestMapping(value = "/testapi", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap receive(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("<TestRestController>:");
        //String content = params.getString("content");

        String eqpType = params.getString("eqpTypeSelected");
        String eqpId = params.getString("eqpId");
        String eqpName = params.getString("eqpName");
        String objectId = params.getString("objectSelected");
        System.out.print(eqpType + " " + eqpId + " " + eqpName + " " + objectId + "\n");

        HashMap res = new HashMap();
        res.put("msg","success");

        return res;
    }

    @CrossOrigin
    @RequestMapping(value = "/rest/sendorder7", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap sendOrder7(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        System.out.println("<TestRestController:sendorder7>:");
        //String content = params.getString("content");

        String deviceName = params.getString("deviceName");
        String bindObject = params.getString("bindObject");
        System.out.print(deviceName + " " + bindObject + "\n");

        byte[] deviceNameByteArray = deviceName.getBytes("utf-8");
        byte[] bindObjectByteArray = bindObject.getBytes("utf-8");
        String deviceNameHex = byteArrayToString(deviceNameByteArray,16);
        deviceNameHex = deviceNameHex.length() % 2 == 1? "0" + deviceNameHex : deviceNameHex;
        String deviceNameHexLength = deviceNameHex.length()/2 < 10?"0"+deviceNameHex.length()/2: Integer.toHexString(deviceNameHex.length()/2);
        String bindObjectHex = byteArrayToString(bindObjectByteArray,16);
        bindObjectHex = bindObjectHex.length() % 2 == 1? "0" + bindObjectHex : deviceNameHex;
        String bindObjectHexLength = bindObjectHex.length()/2 < 10?"0"+bindObjectHex.length()/2: Integer.toHexString(bindObjectHex.length()/2);

        String orderLength = Integer.toHexString(6+1+Integer.parseInt(deviceNameHexLength)/2+1+Integer.parseInt(bindObjectHexLength)/2 +5+1);

        int check = 4 + 10 + 4 + 3 + Integer.parseInt(deviceNameHexLength) + Integer.parseInt(bindObjectHexLength);
        check = check + 4 + 0x5D + 0x62 + 0x44 + 0x6A;

        for (byte b: deviceNameByteArray) {
            check = check + b;
        }
        for (byte b: bindObjectByteArray) {
            check = check + b;
        }
        check = (check% 256 + 256)%256;

        String order = "FEFE"+orderLength+"07"+"040a000403"+deviceNameHexLength+deviceNameHex+bindObjectHexLength+bindObjectHex+"045D62446A"+Integer.toHexString(check)+"AABB";

        order = order.toUpperCase();

        System.out.println("发送的指令：" + order);
        sendMessage(1, order);
        HashMap res = new HashMap();
        res.put("msg","success");

        return res;
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
