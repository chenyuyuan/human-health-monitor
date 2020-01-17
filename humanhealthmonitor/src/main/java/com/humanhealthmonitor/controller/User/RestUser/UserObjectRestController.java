package com.humanhealthmonitor.controller.User.RestUser;

import com.alibaba.fastjson.JSONObject;
import com.humanhealthmonitor.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@Controller
public class UserObjectRestController {



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

        //userNetmaskService.deleteUserRelateNetmask(user.getUserId());


        res.putIfAbsent("msg", "success");

        return res;
    }

}
