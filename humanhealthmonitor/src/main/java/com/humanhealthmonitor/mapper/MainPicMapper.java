package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.MainPic;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MainPicMapper {

    //插入主页图片信息
    @Insert("insert into mainPic(picId,picUrl,newsId,newsHead,publishDate) values(#{picId},#{picUrl},#{newsId},#{newsHead},#{publishDate})")
    int insertMianPic(MainPic mainPic);

    //更新主页图片新闻信息
    @Update("update mainPic set newsId=#{newsId},newsHead=#{newsHead} where picId=#{picId}")
    int updateMainPicNewsInfo(MainPic mainPic);

    //更新主页图片Url
    @Update("update mainPic set picUrl=#{picUrl} where picId=#{picId}")
    int updateMainPicUrl(MainPic mainPic);

    //更新主页图片信息
    @Update("update mainPic set picUrl=#{picUrl},newsId=#{newsId},newsHead=#{newsHead},publishDate=#{publishDate} where picId=#{picId}")
    int updateMainPic(MainPic mainPic);

    //查询主页图片信息
    @Select("select * from mainPic")
    List<MainPic> queryAllMainPic();

    //根据id查询主页图片信息
    @Select("select * from mainPic where picId=#{picId}")
    MainPic queryMainPicById(int picId);

}
