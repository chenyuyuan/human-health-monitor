package com.humanhealthmonitor.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository

public interface DataMapper {


    //insert data
    @Insert("insert into temperature (eqpId,netmaskId,objectId,sendTime,bodyTemp,envTemp) VALUE (#{eqpId},#{netmaskId},#{objectId},#{sendTime},#{bodyTemp},${envTemp})")
    void insertTemperatureData(@Param("eqpId") String eqpId,@Param("netmaskId") int netmaskId,@Param("objectId") String objectId,@Param("sendTime") String sendTime,@Param("bodyTemp") double bodyTemp,@Param("envTemp") double envTemp);

    @Insert("insert into mattress (eqpId,netmaskId,objectId,sendTime,breath,act) VALUE (#{eqpId},#{netmaskId},#{objectId},#{sendTime},#{breath},${act})")
    void insertMattressData(@Param("eqpId") String eqpId,@Param("netmaskId") int netmaskId,@Param("objectId") String objectId,@Param("sendTime") String sendTime,@Param("breath") double breath,@Param("act") double act);

    @Insert("insert into bloodOxygen (eqpId,netmaskId,objectId,sendTime,spo2) VALUE (#{eqpId},#{netmaskId},#{objectId},#{sendTime},#{heartRate})")
    void insertBloodOxygenData(@Param("eqpId") String eqpId,@Param("netmaskId") int netmaskId,@Param("objectId") String objectId,@Param("sendTime") String sendTime,@Param("heartRate") double heartRate);

    @Insert("insert into bloodPressure (eqpId,netmaskId,objectId,sendTime,heartRate,highPressure,lowPressure) VALUE (#{eqpId},#{netmaskId},#{objectId},#{sendTime},#{heartRate},#{highPressure},#{lowPressure})")
    void insertBloodPressureData(@Param("eqpId") String eqpId,@Param("netmaskId") int netmaskId,@Param("objectId") String objectId,@Param("sendTime") String sendTime,@Param("heartRate") double heartRate,@Param("highPressure") double highPressure,@Param("lowPressure") double lowPressure);


    //query data
    @Select("select * from netmask")
    void queryAllNetmask();

}
