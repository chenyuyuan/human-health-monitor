package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    // 插入一个用户
    @Insert("insert into user (userId,userName,pwd,sex,registerDate,birthDate,userTel,userGroup," +
            "lastLoginTime,lastLogoutTime,loginState) " +
            "values(#{userId},#{userName},#{pwd},#{sex},#{registerDate},#{birthDate},#{userTel},#{userGroup}," +
            "#{lastLoginTime},#{lastLogoutTime},#{loginState})")
    int insertUser(User user);

    //按照账号查找用户
    @Select("select * from user where userId =#{userId}")
    User queryUserByUserId(String userId);

    //按照账号或用户名关键字模糊查找
    @Select("select * from user where userId like concat('%', #{key1}, '%') or userName like concat('%', #{key2}, '%')")
    List<User> queryUserByKey(@Param("key1") String key1, @Param("key2") String key2);

    //查找所有用户
    @Select("select * from user")
    List<User> queryAllUser();

    //查询在线的用户人数
    @Select("select count(*) from user where loginState = true")
    int queryAllOnlineUsersCount();

//    //按照账号关键字模糊查找
//    @Select("select * from user where userId like concat('%', #{key}, '%')")
//    List<User> queryUserByUserIdKey(String key);

    //用户信息更新（设置全部项）
    @Update("update user set userName=#{userName},pwd=#{pwd},sex=#{sex},registerDate=#{registerDate},birthDate=#{birthDate}," +
            "userTel=#{userTel},userGroup=#{userGroup},lastLoginTime=#{lastLoginTime}," +
            "lastLogoutTime=#{lastLogoutTime},loginState=#{loginState} " +
            "where userId =#{userId}")
    int updateUser(User user);

    //用户信息更新（设置用户名、性别、出生日期、手机号码）
    @Update("update user set userName=#{userName},sex=#{sex},birthDate=#{birthDate}," +
            "userTel=#{userTel} where userId =#{userId}")
    int updateUserBasicInfo(User user);

    //用户密码修改
    @Update("update user set pwd=#{pwd} where userId=#{userId}")
    int updateUserPassword(User user);

    //更新用户最后登录时间和登录状态
    @Update("update user set lastLoginTime=#{lastLoginTime},loginState=#{loginState} where userId=#{userId}")
    int updateUserLoginState(User user);

    //更新用户最后离线时间和登录状态
    @Update("update user set lastLogoutTime=#{lastLogoutTime},loginState=#{loginState} where userId=#{userId}")
    int updateUserLogoutState(User user);

    //删除用户，用于多步骤操作信息错误回溯处理
    @Delete("delete from user where userId=#{userId}")
    int deleteUserByUserId(String userId);
}
