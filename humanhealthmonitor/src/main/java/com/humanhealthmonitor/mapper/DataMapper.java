package com.humanhealthmonitor.mapper;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository

public interface DataMapper {


    @Select("select * from netmask")
    void queryAllNetmask();
}
