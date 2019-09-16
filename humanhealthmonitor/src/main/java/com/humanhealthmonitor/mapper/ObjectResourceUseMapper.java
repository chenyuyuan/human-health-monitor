package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.ObjectResourceUse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectResourceUseMapper {

    //插入一条对象资源用量记录
    @Insert("insert into objectResourceUse(objectId,yearMonth,beginDate,endDate,msgCount,onlineTimeLength,dataCount) " +
            "values(#{objectId},#{yearMonth},#{beginDate},#{endDate},#{msgCount},#{onlineTimeLength},#{dataCount})")
    int insertObjectResourceUse(ObjectResourceUse objectResourceUse);

    //更新对象资源用量
    @Update("update objectResourceUse set msgCount=#{msgCount},onlineTimeLength=#{onlineTimeLength},dataCount=#{dataCount} " +
            "where objectId=#{objectId} and yearMonth=#{yearMonth}")
    int updateObjectResourceUse(ObjectResourceUse objectResourceUse);

    //只更新短信用量
    @Update("update objectResourceUse set msgCount=#{msgCount} " +
            "where objectId=#{objectId} and yearMonth=#{yearMonth}")
    int updateObjectResourceUseOnlyMsgCount(ObjectResourceUse objectResourceUse);

    //只更新在线时长
    @Update("update objectResourceUse set onlineTimeLength=#{onlineTimeLength} " +
            "where objectId=#{objectId} and yearMonth=#{yearMonth}")
    int updateObjectResourceUseOnlyOnlineTimeLength(ObjectResourceUse objectResourceUse);

    //只更新数据存储条数
    @Update("update objectResourceUse set dataCount=#{dataCount} " +
            "where objectId=#{objectId} and yearMonth=#{yearMonth}")
    int updateObjectResourceUseOnlyDataCount(ObjectResourceUse objectResourceUse);

    //查询某个对象每月资源用量
    @Select("select * from objectResourceUse where objectId=#{objectId}")
    List<ObjectResourceUse> queryObjectResourceUseByObjectId(String objectId);

    //查询某个对象某月资源用量
    @Select("select * from objectResourceUse where objectId=#{objectId} and yearMonth=#{yearMonth}")
    ObjectResourceUse queryObjectResourceUseByObjectIdYearMonth(@Param("objectId")String objectId, @Param("yearMonth")String yearMonth);


}
