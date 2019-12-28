package com.humanhealthmonitor.service;


import com.humanhealthmonitor.mapper.UserNetmaskMapper;
import com.humanhealthmonitor.pojo.Netmask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserNetmaskService {

    @Autowired
    private UserNetmaskMapper userNetmaskMapper;

    public ArrayList<Netmask> queryAllNetmask() {
        return userNetmaskMapper.queryAllNetmask();
    }

    public void updateUserRelateNetmask(int netmask_id, String user_id) {
        userNetmaskMapper.clearUserRelatedNetmask(user_id);

        userNetmaskMapper.updateNetmaskRelateUser(netmask_id, user_id);

    }


    public String queryUserRelatedNetmask(String user_id) {

        return userNetmaskMapper.queryUserRelatedNetmask(user_id);
    }

    public String queryNetmaskRelatedUser(int netmask_id) {

        return userNetmaskMapper.queryNetmaskRelatedUser(netmask_id);
    }

}
