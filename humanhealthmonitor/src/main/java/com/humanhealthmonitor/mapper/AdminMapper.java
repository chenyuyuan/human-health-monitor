package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.Admin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {

    //按照账号查找管理员信息
    @Select("select * from admin where adminId =#{adminId}")
    Admin queryAdminByAdminId(String adminId);

    //查询所有管理员信息
    @Select("select * from admin")
    List<Admin> queryAllAdmin();

    //更新管理员登录状态,登录或离线使用
    @Update("update admin set loginState=#{loginState} where adminId=#{adminId}")
    int updateAdminLoginState(Admin admin);

    //更新管理员密码
    @Update("update admin set pwd=#{pwd}")
    int updateAdminPassword(Admin admin);

}
