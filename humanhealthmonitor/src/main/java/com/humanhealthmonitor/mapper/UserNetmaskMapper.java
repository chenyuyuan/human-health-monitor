package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.Netmask;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface UserNetmaskMapper {

    @Select("select * from netmask")
    ArrayList<Netmask> queryAllNetmask();


    @Update("UPDATE netmask SET related_user_id = NULL where related_user_id = #{user_id}")
    void clearUserRelatedNetmask(@Param("user_id") String user_id);

    @Update("UPDATE netmask SET related_user_id = #{user_id} where id = #{netmask_id}")
    void updateNetmaskRelateUser(@Param("netmask_id") int netmask_id, @Param("user_id") String user_id);

    @Select("SELECT id from netmask WHERE related_user_id = #{user_id}")
    String queryUserRelatedNetmask(@Param("user_id") String user_id);

    @Select("SELECT related_user_id from netmask WHERE id = #{netmask_id}")
    String queryNetmaskRelatedUser(@Param("netmask_id") int netmask_id);

}
