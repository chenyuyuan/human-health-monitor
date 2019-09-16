package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.ObjectMapper;
import com.humanhealthmonitor.pojo.User;
import com.humanhealthmonitor.mapper.UserMapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
//import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ObjectMapper objectMapper;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 新增用户
     */
    public int insertUser(User user) {
        return userMapper.insertUser(user);
    }

    //按照账号检索用户
    public User queryUserByUserId(String userId) {
        return userMapper.queryUserByUserId(userId);
    }

    //用户注册
    public int userRegister(String userId, String userName, String password,
                            String userTel, String birthDate, String sex) {
        ////从数据库检索账号，如果账号存在，返回-1，提示用户账号已被注册
        User sqlUser = userMapper.queryUserByUserId(userId);
        if (sqlUser != null) {
//            System.out.println("账号已被注册");
            return -1;
        } else if (objectMapper.queryObjectByObjectId(userId) != null) {//检验不能有同id的监测对象，便于后续自动创建用户同id的监测的对象供自己使用
            //System.out.println("该账号已被注册");
            return -2;
        } else {
            User user = new User();
            user.setUserId(userId);
            user.setUserName(userName);
            user.setPwd(password);
            user.setUserTel(userTel);
//            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2005-06-09");
//            Date date=Date.valueOf(birthDate);
            user.setBirthDate(java.sql.Date.valueOf(birthDate));
            Calendar calendar = Calendar.getInstance();
            String registerDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                    String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" +
                    String.valueOf(calendar.get(Calendar.DATE) + 1);
            user.setRegisterDate(java.sql.Date.valueOf(registerDate));
            user.setSex(sex);
            user.setUserGroup("个人");
            user.setLoginState(false);
//            System.out.println("注册成功");
            return userMapper.insertUser(user);
        }
    }

    //用户登录
    public User userLogin(String userId, String password) {
        User sqlUser = userMapper.queryUserByUserId(userId);
        if (sqlUser == null) {
//            System.out.println("不存在此用户账号");
            return null;
        } else if (sqlUser.getUserId().equals(userId) && sqlUser.getPwd().equals(password)) {
//            System.out.println("账号密码正确");
            //把用户状态设置成在线并更新最后登录时间
//            String writeTime = dateformat.format((System.currentTimeMillis() + 50400000));//CST时间加上14小时即50400000毫秒为北京时间,后来改了时区为上海就不用了
            String writeTime = dateformat.format(System.currentTimeMillis());
            sqlUser.setLastLoginTime(java.sql.Timestamp.valueOf(writeTime));
            sqlUser.setLoginState(true);
            userMapper.updateUserLoginState(sqlUser);
            return sqlUser;
        } else {
//            System.out.println("用户密码错误");
            return null;
        }
    }

    //按照账号或用户名关键字模糊查找
    public List<User> queryUserByKey(String key1, String key2) {
        return userMapper.queryUserByKey(key1, key2);
    }

    //查找所有用户
    public List<User> queryAllUser() {
        return userMapper.queryAllUser();
    }

    //查询在线的用户人数
    public int queryAllOnlineUsersCount()
    {
        return userMapper.queryAllOnlineUsersCount();
    }

//    //按照账号关键字模糊查找
//    public List<User> queryUserByUserIdKey(String key)
//    {
//        return userMapper.queryUserByUserIdKey(key);
//    }

    //用户信息更新（设置全部项）
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    //用户信息更新（设置用户名、性别、出生日期、手机号码）
    public int updateUserBasicInfo(User user) {
        return userMapper.updateUserBasicInfo(user);
    }

    //用户密码修改
    public int updateUserPassword(User user) {
        return userMapper.updateUserPassword(user);
    }

    //更新用户最后登录时间和登录状态
    public int updateUserLoginState(User user) {
        return userMapper.updateUserLoginState(user);
    }

    //更新用户最后离线时间和登录状态
    public int updateUserLogoutState(User user) {
        return userMapper.updateUserLogoutState(user);
    }

    //删除用户，用于多步骤操作信息错误回溯处理
    public int deleteUserByUserId(String userId) {
        return userMapper.deleteUserByUserId(userId);
    }
}
