package com.humanhealthmonitor.controller.User.RestUser;

import com.alibaba.fastjson.JSONObject;
import com.humanhealthmonitor.MsgQueue;
import com.humanhealthmonitor.pojo.Equipment;
import com.humanhealthmonitor.pojo.EquipmentType;
import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.service.UserNetmaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import static com.humanhealthmonitor.util.ByteUtils.byteArrayToString;
import static com.humanhealthmonitor.util.ByteUtils.stringToByteArray;

@RestController
@RequestMapping("/rest")
public class UserNetmaskRestController {

    @Autowired
    private UserNetmaskService userNetmaskService;

    @CrossOrigin
    @RequestMapping(value = "/monitorCenter/relateNetmask", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap relateNetmask(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response)
            throws IOException, NullPointerException, InterruptedException {
        System.out.print("[UserNetmaskRestController]:");
        //String content = params.getString("content");
        HashMap res = new HashMap();

        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);


        String netmask_idStr = params.getString("netmask_id");

        int netmask_id = Integer.parseInt(netmask_idStr);

        userNetmaskService.updateUserRelateNetmask(netmask_id, user.getUserId());


        res.putIfAbsent("msg", "success");

        return res;
    }

    @CrossOrigin
    @RequestMapping(value = "/monitorCenter/deleteNetmask", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap deleteNetmask(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response)
            throws IOException, NullPointerException, InterruptedException {
        System.out.print("[UserNetmaskRestController]:");
        //String content = params.getString("content");
        HashMap res = new HashMap();

        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);


        String netmask_idStr = params.getString("netmask_id");

        int netmask_id = Integer.parseInt(netmask_idStr);

        userNetmaskService.deleteUserRelateNetmask(user.getUserId());


        res.putIfAbsent("msg", "success");

        return res;
    }
}
