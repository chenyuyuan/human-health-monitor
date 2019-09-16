package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.MainPicMapper;
import com.humanhealthmonitor.pojo.MainPic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainPicService {
    @Autowired
    private MainPicMapper mainPicMapper;

    //插入主页图片信息
    public int insertMianPic(MainPic mainPic) {
        return mainPicMapper.insertMianPic(mainPic);
    }

    //更新主页图片新闻信息
    public int updateMainPicNewsInfo(MainPic mainPic) {
        return mainPicMapper.updateMainPicNewsInfo(mainPic);
    }

    //更新主页图片Url
    public int updateMainPicUrl(MainPic mainPic) {
        return mainPicMapper.updateMainPicUrl(mainPic);
    }

    //更新主页图片信息
    public int updateMainPic(MainPic mainPic) {
        return mainPicMapper.updateMainPic(mainPic);
    }

    //查询主页图片信息
    public List<MainPic> queryAllMainPic() {
        return mainPicMapper.queryAllMainPic();
    }

    //根据id查询主页图片信息
    public MainPic queryMainPicById(int picId) {
        return mainPicMapper.queryMainPicById(picId);
    }

}
