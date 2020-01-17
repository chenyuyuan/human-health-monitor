package com.humanhealthmonitor.controller.User.RestUser;

import com.alibaba.fastjson.JSONObject;
import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@RestController
@RequestMapping("/rest")
public class UserObjectRestController {


    @Autowired
    private ObjectService objectService;

    @CrossOrigin
    @RequestMapping(value = "/monitorCenter/deleteObject", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public HashMap deleteObject(@RequestBody JSONObject params, HttpServletRequest request, HttpServletResponse response)
            throws IOException, NullPointerException, InterruptedException {
        System.out.print("[UserObjectRestController]:");
        //String content = params.getString("content");
        HashMap res = new HashMap();

        User user = (User) request.getSession().getAttribute("user");
        request.setAttribute("user", user);


        String objectId = params.getString("objectId");

        //objectService.queryObjectAndUser(objectId,user.getUserId());
        objectService.deleteObjectAndEquipment(objectId,user.getUserId());

        //userNetmaskService.deleteUserRelateNetmask(user.getUserId());

        int flag = objectService.queryObjectAndUser(objectId,user.getUserId());
        if(flag == 1) {
            res.put("msg", "fail");
        }
        else if(flag == -1) {
            res.put("msg", "success");
        }

        res.putIfAbsent("msg", "fail");

        return res;
    }

}
