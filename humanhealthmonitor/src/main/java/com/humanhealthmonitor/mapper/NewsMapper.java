package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsMapper {

    //插入一条新闻,newsId自增
    @Insert("insert into news(newsType,newsHead,newsContent,publishDate) values(#{newsType},#{newsHead},#{newsContent},#{publishDate})")
    int insertNews(News news);

    //根据新闻id查询一条新闻
    @Select("select * from news where newsId=#{newsId}")
    News queryNewsByNewsId(int newsId);

    //根据新闻标题查询一条新闻
    @Select("select * from news where newsHead=#{newsHead}")
    News queryNewsByNewsHead(String newsHead);

    //根据新闻标题关键字查询新闻基本信息
    @Select("select newsId,newsType,newsHead,publishDate from news where newsHead like concat('%', #{key}, '%')")
    List<News> queryNewsByNewsHeadKey(String key);

    //查询某类型所有新闻基本信息
    @Select("select * from news where newsType=#{newsType} order by newsId desc")
    List<News> queryNewsByNewsType(String newsType);

    //查询某类型新闻前5条
    @Select("select * from news where newsType=#{newsType} order by newsId desc limit 5")
    List<News> queryNewsByNewsTypeTop5(String newsType);

    //查询所有新闻基本信息
    @Select("select newsId,newsType,newsHead,publishDate,viewCount from news")
    List<News> queryALlNewsBasicInfo();

    //根据Id删除一条新闻
    @Delete("delete from news where newsId=#{newsId}")
    int deleteNewsByNewsId(int newsId);

    //根据新闻id更新浏览量
    @Update("update news set viewCount=viewCount+1 where newsId=#{newsId}")
    int updateNewsViewCountByNewsId(int newsId);

}
