package com.humanhealthmonitor.mapper;

import com.humanhealthmonitor.pojo.BloodOxygen;
import com.humanhealthmonitor.pojo.BloodPressure;
import com.humanhealthmonitor.pojo.Mattress;
import com.humanhealthmonitor.pojo.Temperature;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

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
    @Select("SELECT * FROM temperature WHERE objectId=#{objectId} and sendTime>#{limitTime} ORDER BY time DESC LIMIT #{dataCount} ")
    ArrayList<Temperature> queryLastSeveralTemperature(@Param("objectId") String objectId, @Param("limitTime") String limitTime,@Param("dataCount") int dataCount);

    @Select("SELECT * FROM mattress WHERE objectId=#{objectId} and sendTime>#{limitTime} ORDER BY time DESC LIMIT #{dataCount} ")
    ArrayList<Mattress> queryLastSeveralMattress(@Param("objectId") String objectId,@Param("limitTime") String limitTime, @Param("dataCount") int dataCount);

    @Select("SELECT * FROM bloodOxygen WHERE objectId=#{objectId} and sendTime>#{limitTime} ORDER BY time DESC LIMIT #{dataCount} ")
    ArrayList<BloodOxygen> queryLastSeveralBloodOxygen(@Param("objectId") String objectId,@Param("limitTime") String limitTime, @Param("dataCount") int dataCount);

    @Select("SELECT * FROM bloodPressure WHERE objectId=#{objectId} and sendTime>#{limitTime} ORDER BY time DESC LIMIT #{dataCount} ")
    ArrayList<BloodPressure> queryLastSeveralBloodPressure(@Param("objectId") String objectId,@Param("limitTime") String limitTime, @Param("dataCount") int dataCount);



    @Select("SELECT * FROM temperature WHERE objectId=#{objectId} and time>#{startTime} and time<#{endTime}")
    ArrayList<Temperature> queryTemperatureLimitTime(@Param("objectId") String objectId,@Param("startTime") String startTime,@Param("endTime") String endTime);
    @Select("SELECT * FROM bloodOxygen WHERE objectId=#{objectId} and time>#{startTime} and time<#{endTime}")
    ArrayList<BloodOxygen> queryBloodOxygenLimitTime(@Param("objectId") String objectId,@Param("startTime") String startTime,@Param("endTime") String endTime);
    @Select("SELECT * FROM bloodPressure WHERE objectId=#{objectId} and time>#{startTime} and time<#{endTime}")
    ArrayList<BloodPressure> queryBloodPressureLimitTime(@Param("objectId") String objectId,@Param("startTime") String startTime,@Param("endTime") String endTime);
    @Select("SELECT * FROM mattress WHERE objectId=#{objectId} and time>#{startTime} and time<#{endTime}")
    ArrayList<Mattress> queryMattressLimitTime(@Param("objectId") String objectId,@Param("startTime") String startTime,@Param("endTime") String endTime);

}
