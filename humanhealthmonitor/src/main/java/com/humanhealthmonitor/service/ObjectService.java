package com.humanhealthmonitor.service;

import com.humanhealthmonitor.pojo.Object;
import com.humanhealthmonitor.mapper.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class ObjectService {
    @Autowired
    private ObjectMapper objectMapper;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //按照账号检索监测对象
    public Object queryObjectByObjectId(String objectId) {
        return objectMapper.queryObjectByObjectId(objectId);
    }

    //按照账号或监测对象名关键字模糊查找
    public List<Object> queryObjectByKey(String key) {
        return objectMapper.queryObjectByKey(key);
    }

    //查询在线的监测对象人数
    public int queryAllOnlineObjectCount()
    {
        return objectMapper.queryAllOnlineObjectCount();
    }

    //插入一个新的监测对象
    public int insertObject(Object object) {
        return objectMapper.insertObject(object);
    }

    //用户注册
    public int objectRegister(String objectId, String userId, String objectName, String password,
                              String objectTel, String birthDate, String sex) {
        ////从数据库检索账号，如果账号存在，返回-1，提示用户账号已被注册
        Object sqlObject = objectMapper.queryObjectByObjectId(objectId);
        if (sqlObject != null) {
//            System.out.println("账号已被注册");
            return -1;
        } else {
            Object object = new Object();
            object.setObjectId(objectId);
            object.setUserId(userId);
            object.setObjectName(objectName);
            object.setPwd(password);
            object.setObjectTel(objectTel);
//            Date date = new SimpleDateFormat("yyyy-MM-dd").parse("2005-06-09");
//            Date date=Date.valueOf(birthDate);
            object.setBirthDate(java.sql.Date.valueOf(birthDate));
            Calendar calendar = Calendar.getInstance();
            String registerDate = String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                    String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" +
                    String.valueOf(calendar.get(Calendar.DATE) + 1);
            object.setRegisterDate(java.sql.Date.valueOf(registerDate));
            object.setSex(sex);
            object.setLoginState(false);
//            System.out.println("注册成功");
            return objectMapper.insertObject(object);
        }
    }

    //监测对象登录
    public Object objectLogin(String objectId, String pwd) {
        Object sqlObject = objectMapper.queryObjectByObjectId(objectId);
        if (sqlObject == null) {
//            System.out.println("不存在此监测对象账号");
            return null;
        } else if (sqlObject.getObjectId().equals(objectId) && sqlObject.getPwd().equals(pwd)) {
//            System.out.println("账号密码正确");
            //把监测对象状态设置成在线并更新最后登录时间
            sqlObject.setLoginState(true);
            //把用户状态设置成在线并更新最后登录时间
//            String writeTime = dateformat.format((System.currentTimeMillis() + 50400000));//CST时间加上14小时即50400000毫秒为北京时间
            String writeTime = dateformat.format(System.currentTimeMillis());
            sqlObject.setLastLoginTime(java.sql.Timestamp.valueOf(writeTime));
            sqlObject.setLoginState(true);
            objectMapper.updateLoginState(sqlObject);
//            objectMapper.updateUser(sqlObject);
            return sqlObject;
        } else {
//            System.out.println("监测对象密码错误");
            return null;
        }
    }

    //监测对象信息更新（设置全部项）
    public int updateObject(Object object) {
        return objectMapper.updateObject(object);
    }

    //用户更新监测对象信息(可变更用户昵称、性别、生日、手机号)
    public int updateObjectByUser(Object object) {
//        System.out.println(object.getBirthDate()+"service");
        return objectMapper.updateObjectByUser(object);
    }

//    //用户更新监测对象信息(可变更用户昵称、性别、生日、手机号)
//    public int updateObjectByUser(String objectName, String sex, Date birthDate, String objectTel, String objectId)
//    {
//        return objectMapper.updateObjectByUser(objectName,sex,birthDate,objectTel,objectId);
//    }

    //更新手机号码
    public int updateObjectTel(Object object) {
        return objectMapper.updateObjectTel(object);
    }

    //更新密码
    public int updateObjectPassword(Object object) {
        return objectMapper.updateObjectPassword(object);
    }

    //监测对象登陆后改变登录状态并写入最后登录时间
    public int updateLoginState(Object object) {
        return objectMapper.updateLoginState(object);
    }

    //根据userId查询所有关联的监测对象
    public List<Object> queryAllObjectByUserId(String userId) {
        return objectMapper.queryAllObjectByUserId(userId);
    }

    //监测对象退出登录修改登录状态并写入最后离线时间
    public int updateLogoutState(Object object) {
        return objectMapper.updateLogoutState(object);
    }




    public int queryObjectAndUser(String objectId, String userId) {
        if (objectMapper.queryObjectAndUser(objectId, userId)==null){
            return -1;
        }
        else {
            return 1;
        }
    }




    public void deleteObjectAndEquipment(String objectId, String userId) {
        objectMapper.deleteObject(objectId,userId);
        objectMapper.deleteEquipment(objectId);
    }

}
