package com.humanhealthmonitor;

import java.util.Map;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.util.StringUtils;

public class InfluxDBConnector {

    private String userName;
    private String password;
    private String connectUrl;
    private String database;

    private InfluxDB influxDB;

    //构造函数
    public InfluxDBConnector(String userName, String password, String connectUrl, String database){
        this.userName = userName;
        this.password = password;
        this.connectUrl = connectUrl;
        this.database = database;
    }

    //连接到数据库
    public InfluxDB  connectToDatabase(){
        if(influxDB == null){
            influxDB = InfluxDBFactory.connect(connectUrl, userName, password);
            influxDB.createDatabase(database);
        }
        return influxDB;
    }

    //设置数据保留策略
    public void setRetentionPolicy(){
        String command = String.format("CREATE RETENTION POLICY \"%s\" ON \"%s\" DURATION %s REPLICATION %s DEFAULT",
                "default", database, "30d", 1);// 策略名，数据库名，数据保存时限30天，副本个数为1，command结尾的DEFAULT 表示设为默认策略
        this.queryData(command);
    }

    //查询操作，结果放入QueryResult
    public QueryResult queryData(String command){
        return influxDB.query(new Query(command, database));
    }

    //插入操作
    public void insertData(String measurement, Map<String, String> tags, Map<String, Object> fields){
        Builder builder = Point.measurement(measurement);
        builder.tag(tags);
        builder.fields(fields);
        influxDB.write(database, "", builder.build());//s1什么作用？
    }

    //删除操作，返回错误信息
    public String deleteData(String command){
        QueryResult result = influxDB.query(new Query(command, database));
        return result.getError();
    }

    //创建数据库
    public void createDB(String dbName){
        influxDB.createDatabase(dbName);
    }

    //删除数据库
    public void deleteDB(String dbName){
        influxDB.deleteDatabase(dbName);
    }

    //getter and setter
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConnectUrl() {
        return connectUrl;
    }

    public void setConnectUrl(String connectUrl) {
        this.connectUrl = connectUrl;
    }

    public String getDatabase()
    {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
