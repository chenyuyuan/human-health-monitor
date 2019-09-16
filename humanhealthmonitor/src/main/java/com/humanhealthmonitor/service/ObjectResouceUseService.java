package com.humanhealthmonitor.service;

import com.humanhealthmonitor.mapper.ObjectResourceUseMapper;
import com.humanhealthmonitor.pojo.ObjectResourceUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ObjectResouceUseService {
    @Autowired
    private ObjectResourceUseMapper objectResourceUseMapper;


    //插入一条对象资源用量记录
    public int insertObjectResourceUse(ObjectResourceUse objectResourceUse) {
        return objectResourceUseMapper.insertObjectResourceUse(objectResourceUse);
    }

    //更新对象资源用量
    public int updateObjectResourceUse(ObjectResourceUse objectResourceUse) {
        return objectResourceUseMapper.updateObjectResourceUse(objectResourceUse);
    }

    //只更新短信用量
    public int updateObjectResourceUseOnlyMsgCount(ObjectResourceUse objectResourceUse) {
        return objectResourceUseMapper.updateObjectResourceUseOnlyMsgCount(objectResourceUse);
    }

    //只更新在线时长
    public int updateObjectResourceUseOnlyOnlineTimeLength(ObjectResourceUse objectResourceUse) {
        return objectResourceUseMapper.updateObjectResourceUseOnlyOnlineTimeLength(objectResourceUse);
    }

    //只更新数据存储条数
    public int updateObjectResourceUseOnlyDataCount(ObjectResourceUse objectResourceUse) {
        return objectResourceUseMapper.updateObjectResourceUseOnlyDataCount(objectResourceUse);
    }

    //查询某个对象每月资源用量
    public List<ObjectResourceUse> queryObjectResourceUseByObjectId(String objectId) {
        return objectResourceUseMapper.queryObjectResourceUseByObjectId(objectId);
    }

    //查询某个对象某月资源用量
    public ObjectResourceUse queryObjectResourceUseByObjectIdYearMonth(String objectId, String yearMonth) {
        return objectResourceUseMapper.queryObjectResourceUseByObjectIdYearMonth(objectId, yearMonth);
    }
}
