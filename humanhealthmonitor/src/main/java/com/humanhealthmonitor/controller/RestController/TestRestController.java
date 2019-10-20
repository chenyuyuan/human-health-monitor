package com.humanhealthmonitor.controller.RestController;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Hashtable;

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
}
