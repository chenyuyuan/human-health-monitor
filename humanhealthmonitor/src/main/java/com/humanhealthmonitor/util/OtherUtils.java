package com.humanhealthmonitor.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OtherUtils {
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    //返回时间戳字符串，单位纳秒（ns）,而非毫秒，请注意
    public String dateTimeLocalToTimeStampString(String dateTimeLocal) {
        dateTimeLocal = dateTimeLocal.replace("T"," ");
        dateTimeLocal = dateTimeLocal+":00";//加上秒值00
        LocalDateTime localDateTimeStart = LocalDateTime.parse(dateTimeLocal, df);
        long timestamp = localDateTimeStart.toInstant(ZoneOffset.of("+8")).toEpochMilli();//时区东8区
        return String.valueOf(timestamp*1000000);
    }
}
