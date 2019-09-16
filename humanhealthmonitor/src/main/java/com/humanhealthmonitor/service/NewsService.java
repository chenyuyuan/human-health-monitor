package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.NewsMapper;
import com.humanhealthmonitor.pojo.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    @Autowired
    private NewsMapper newsMapper;

    //插入一条新闻,newsId自增
    public int insertNews(News news) {
        return newsMapper.insertNews(news);
    }

    //根据新闻id查询一条新闻
    public News queryNewsByNewsId(int newsId) {
        return newsMapper.queryNewsByNewsId(newsId);
    }

    //根据新闻标题查询一条新闻
    public News queryNewsByNewsHead(String newsHead) {
        return newsMapper.queryNewsByNewsHead(newsHead);
    }

    //根据新闻标题关键字查询新闻
    public List<News> queryNewsByNewsHeadKey(String key) {
        return newsMapper.queryNewsByNewsHeadKey(key);
    }

    //查询某类型所有新闻标题
    public List<News> queryNewsByNewsType(String newsType) {
        return newsMapper.queryNewsByNewsType(newsType);
    }

    //查询某类型新闻前5条
    public List<News> queryNewsByNewsTypeTop5(String newsType) {
        return newsMapper.queryNewsByNewsTypeTop5(newsType);
    }

    //查询所有新闻基本信息
    public List<News> queryALlNewsBasicInfo() {
        return newsMapper.queryALlNewsBasicInfo();
    }

    //根据Id删除一条新闻
    public int deleteNewsByNewsId(int newsId) {
        return newsMapper.deleteNewsByNewsId(newsId);
    }

    //根据新闻id更新浏览量
    public int updateNewsViewCountByNewsId(int newsId) {
        return newsMapper.updateNewsViewCountByNewsId(newsId);
    }
}
