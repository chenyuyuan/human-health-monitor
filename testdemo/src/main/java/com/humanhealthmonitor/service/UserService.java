package com.humanhealthmonitor.service;

import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.mapper.UserMapper;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //用户登录
    public User userLogin(String userId, String password) {
        User sqlUser = userMapper.queryUserByUserId(userId);
        if (sqlUser == null) {
            return null;
        } else if (sqlUser.getUserId().equals(userId) && sqlUser.getPwd().equals(password)) {
            String writeTime = dateformat.format(System.currentTimeMillis());
            sqlUser.setLastLoginTime(java.sql.Timestamp.valueOf(writeTime));
            sqlUser.setLoginState(true);
            userMapper.updateUserLoginState(sqlUser);
            return sqlUser;
        } else {
            return null;
        }
    }

}
