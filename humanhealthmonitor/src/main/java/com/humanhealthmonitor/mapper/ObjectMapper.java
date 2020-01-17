package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.Object;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectMapper {

    // 插入一个监测对象
    @Insert("insert into object (objectId,userId,objectName,pwd,sex,birthDate,registerDate,objectTel," +
            "lastLoginTime,lastLogoutTime,loginState) " +
            "values(#{objectId},#{userId},#{objectName},#{pwd},#{sex},#{birthDate},#{registerDate},#{objectTel}," +
            "#{lastLoginTime},#{lastLogoutTime},#{loginState})")
    int insertObject(Object object);

    //按照账号查找监测对象所有信息
    @Select("select * from object where objectId =#{objectId}")
    Object queryObjectByObjectId(String objectId);

    //按照账号或监测对象名关键字模糊查找
    @Select("select * from object where objectId like '%#{key}%' or objectName like '%#{key}%'")
    List<Object> queryObjectByKey(String key);

    //查询某个用户下所有的监测对象
    @Select("select * from object where userId = #{userId}")
    List<Object> queryAllObjectByUserId(String userId);

    //查询在线的监测对象人数
    @Select("select count(*) from object where loginState = true")
    int queryAllOnlineObjectCount();

    //监测对象信息更新（设置全部项）
    @Update("update object set objectName=#{objectName},pwd=#{pwd},sex=#{sex},birthDate=#{birthDate},registerDate=#{registerDate}," +
            "objectTel=#{objectTel},lastLoginTime=#{lastLoginTime},lastLogoutTime=#{lastLogoutTime},loginState=#{loginState} " +
            "where objectId =#{objectId}")
    int updateObject(Object object);

    //用户更新监测对象信息(可变更用户昵称、性别、生日、手机号)
    @Update("update object set objectName=#{objectName},sex=#{sex},birthDate=#{birthDate},objectTel=#{objectTel} where objectId =#{objectId}")
    int updateObjectByUser(Object object);

//    //用户更新监测对象信息(可变更用户昵称、性别、生日、手机号)
//    @Update("update object set objectName=#{objectName},sex=#{sex},birthDate=#{birthDate},objectTel=#{objectTel} where objectId =#{objectId}")
//    int updateObjectByUser(String objectName,String sex,Date birthDate,String objectTel,String objectId);

    //更新手机号码
    @Update("update object set objectTel=#{objectTel} where objectId=#{objectId}")
    int updateObjectTel(Object object);

    //更新密码
    @Update("update object set pwd=#{pwd} where objectId=#{objectId}")
    int updateObjectPassword(Object object);

    //监测对象登陆后改变登录状态并写入最后登录时间
    @Update("update object set lastLoginTime=#{lastLoginTime},loginState=#{loginState} where objectId=#{objectId}")
    int updateLoginState(Object object);

    //监测对象退出登录修改登录状态并写入最后离线时间
    @Update("update object set lastLogoutTime=#{lastLogoutTime},loginState=#{loginState} where objectId=#{objectId}")
    int updateLogoutState(Object object);

    @Select("SELECT * FROM object WHERE objectId = #{objectId} and userId=#{userId}")
    Object queryObjectAndUser(@Param("objectId") String objectId,@Param("userId") String userId);



    @Delete("DELETE FROM object where objectId = #{objectId} and userId=#{userId}")
    int deleteObject(@Param("objectId") String objectId, @Param("userId") String userId);

    @Delete("DELETE FROM equipment where objectId = #{objectId}")
    int deleteEquipment(@Param("objectId") String objectId);
}
