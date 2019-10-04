package com.example.humanhealthmonitor;

import com.example.humanhealthmonitor.pojo.*;
import com.example.humanhealthmonitor.service.*;
import com.github.qcloudsms.httpclient.HTTPException;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.Object;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static com.example.humanhealthmonitor.MsgQueue.protocolState;

@Component///////
public class RabbitReceiver implements Runnable{

    @Autowired
    private EquipmentService equipmentService;/////////added0521
    @Autowired
    private AlarmNormalValueService alarmNormalValueService;
    @Autowired
    private AlarmSpecialValueService alarmSpecialValueService;
    @Autowired
    private ObjectService objectService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectResouceUseService objectResouceUseService;
    @Autowired
    private AlarmLogService alarmLogService;

    private static RabbitReceiver rabbitReceiver;/////////added0521

    private final static String EXCHANGE_NAME = "amq.direct";
    private final static String QUEUE_NAME = "health_queue";
    private InfluxDBConnector influxDBConnector;//创建influxDB连接实例
    private CloudMsgUtil cloudMsgUtil = new CloudMsgUtil();//云短信工具
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @PostConstruct
    public void init() {/////////////added0521
        rabbitReceiver= this;
        rabbitReceiver.equipmentService= this.equipmentService;

        rabbitReceiver.alarmNormalValueService=this.alarmNormalValueService;
        rabbitReceiver.alarmSpecialValueService=this.alarmSpecialValueService;
        rabbitReceiver.objectService=this.objectService;
        rabbitReceiver.userService=this.userService;
        rabbitReceiver.objectResouceUseService = this.objectResouceUseService;
        rabbitReceiver.alarmLogService=this.alarmLogService;
    }

    public void run() {
        try {
//            System.out.println("RabbitReceiver: rabbit receiver start...");
            handleRabbitReceived();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRabbitReceived() throws IOException,TimeoutException {

//        System.out.println("create ConnectionFactory...");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("140.143.232.52");
        factory.setUsername("Andy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //        //消费者指定要订阅的队列
//        //第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
//        //第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、第四个参数为当所有消费者客户端连接断开时是否自动删除队列、
//        //第五个参数为队列的其他参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String routingKey = "health";
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);
        System.out.println("RabbitReceiver: Waiting for messages...");
//        channel.basicQos(1);//告诉消费一次只获取一条消息，处理完再获取下一条
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)//body是字节数组类型
                    throws IOException {
//                String message = new String(body, "UTF-8");
//                System.out.println(" [x] Received '" + message + "'");
                List<Byte> byteArrayList = new ArrayList<>();//字节列表
//                System.out.println("body.length: "+body.length);
                for(int i = 0;i < body.length;i++)
                {
                    byteArrayList.add(body[i]);
                }
                System.out.println("RabbitReceiver: byteArrayList: "+byteArrayList);
                socketInfoProcess(byteArrayList);
            }
        };

        /**订阅消息
         * autoAck: 应答模式，true：自动应答，即消费者获取到消息，该消息就会从队列中删除掉，
         * false：手动应答，当从队列中取出消息后，需要程序员手动调用方法应答，
         * 如果没有应答，该消息还会再放进队列中，就会出现该消息一直没有被消费掉的现象
         */
        channel.basicConsume(QUEUE_NAME, true, consumer);//basicConsume订阅模式可以从队列中一直持续的自动的接收消息,basicGet一次只能获取一条消息，如果还想再获取下一条还要再次调用
    }

    //字节转为16进制字符串，如“FE”
    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public byte[] toByteArray(String hexString) {
        if (hexString.equals("")) {
            System.out.println("RabbitReceiver: toByteArray(): this hexString is empty");
            throw new IllegalArgumentException("this hexString must not be empty");
        }
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

    //将1个字节的8个位解析成无符号0-255的值
    public int byteToUsignedValue(Byte b) {
        int bInt = (int) b;
        if (bInt >= 0) {
            return bInt;
        } else {
            return (bInt + 256);
        }
    }

    //int转为两位16进制字符串
    public String byteToHexStringSocketTask(Byte b)
    {
        int bInt = byteToUsignedValue(b);
        String str = Integer.toHexString(bInt);
        if(str.length()==1)
        {
            str = "0" + str;
        }
        return str;
    }

    //处理Socket收到的信息
    public void socketInfoProcess(List<Byte> byteArrayList) {
        String date = dateformat.format(System.currentTimeMillis());
        String yearMonth = date.substring(0,7);//如2019-03
        AlarmLog alarmLog = new AlarmLog();
        Equipment equipmentData;//added0521
        while (byteArrayList.size() >= 10) {//while (byteArrayList.size() >= 22)
            if (byteArrayList.get(0) == (byte) 0xFE && byteArrayList.get(1) == (byte) 0xFE) {
                int dataLength = byteToUsignedValue(byteArrayList.get(4)) * 256 + byteToUsignedValue(byteArrayList.get(5));//获取传感器数据长度
                if (byteArrayList.size() >= (dataLength + 6 + 3))//字节总长度达不到，证明数据损坏，这里的9是数据前6后3附加字节总长
                {
                    if (byteArrayList.get(dataLength + 9 - 2) == (byte) 0xAA && byteArrayList.get(dataLength + 9 - 1) == (byte) 0xBB)//验证结尾格式AABB
                    {
                        //校验和计算
                        int check = 0;
                        for (int i = 0; i < dataLength; i++) {
                            check += byteArrayList.get(i + 6);
                        }
                        check = Math.abs(check) % 64;
//                        System.out.println("check result: "+check);
                        if (check == byteArrayList.get(dataLength + 9 - 3))//比对数据发送前后的校验和，一致则继续，不一致说明数据传输错误//这里需要判断包长会否大于大dataLength+9-3，防止出错
                        {
                            System.out.println("RabbitReceiver: check pass...");
                            int netMaskId = byteToUsignedValue(byteArrayList.get(2));//这里要注意，网关id是按0x11字面16转10进制的值17来与上面对应
                            int orderType = byteToUsignedValue(byteArrayList.get(3));//04是全部设备信息，03是指定设备信息,05指示更换协议为AMQP
                            System.out.println("netMaskId: " + netMaskId + "  orderType: " + orderType);
                            if(orderType == 5)//更换协议为AMQP//added0527
                            {
                                //修改或维持发来消息的网关状态为AMQP
                                if(protocolState[netMaskId-1] != 2)
                                {
                                    protocolState[netMaskId-1] = 2;
                                    System.out.println("RabbitReceiver: netMask order to change protocolState of netMask"+netMaskId + " to 2(AMQP)...");
                                }
                                break;
                            }

                            //修改或维持发来消息的网关状态为AMQP
                            if(protocolState[netMaskId-1] != 2)
                            {
                                protocolState[netMaskId-1] = 2;
                                System.out.println("RabbitReceiver: change protocolState of netMask "+netMaskId + " to 2(AMQP)...");
                            }

                            List<Byte> dataList = byteArrayList.subList(6, 6 + dataLength);//取出校验成功的数据区数据，放到dataList中

                            if(orderType == 6)//设备号与网关顺序号对应关系修正
                            {
                                int deviceNum = dataList.size()/6;
                                List<Byte> miniDataList = new ArrayList<>();
                                for (int j = 0;j < deviceNum;j++)
                                {
                                    miniDataList = dataList.subList(6*j,6*j+6);//不包括最后一个值
                                    int deviceSerialNew = byteToUsignedValue(miniDataList.get(5));//顺序号
                                    String eqpId = "";
                                    for(int i = 0;i < 5;i++)
                                    {
                                        eqpId += byteToHexStringSocketTask(miniDataList.get(i));
                                    }
                                    eqpId = eqpId.substring(1,10).toUpperCase();//左闭右开，不包括10位置，一共10-1字符
                                    System.out.println("RabbitReceiver:"+netMaskId+": order06 eqpId: "+eqpId);

                                    Equipment equipment1 = rabbitReceiver.equipmentService.queryEquipmentByEqpId(eqpId);
                                    if (equipment1 == null)
                                    {
                                        System.out.println("RabbitReceiver:"+netMaskId+": order06 equipment1 null");
                                    }else{
                                        System.out.println("RabbitReceiver:"+netMaskId+": order06 equipment1 not null");
                                        int deviceNetMaskId = equipment1.getNetmaskId();
                                        if(deviceNetMaskId != netMaskId)
                                        {
                                            equipment1.setNetmaskId(netMaskId);
                                            rabbitReceiver.equipmentService.updateEquipmentNetMaskId(equipment1);
                                            System.out.println("RabbitReceiver:"+netMaskId+": order06 a netMaskId updated,,,deviceNum="+deviceNum);
                                        }
                                        int deviceSerialOrigin = equipment1.getDeviceSerial();
                                        if (deviceSerialOrigin != deviceSerialNew)
                                        {
                                            equipment1.setDeviceSerial(deviceSerialNew);
                                            rabbitReceiver.equipmentService.updateEquipmentDeviceSerial(equipment1);
                                            System.out.println("RabbitReceiver:"+netMaskId+": order06 a deviceSerial updated,,,deviceNum="+deviceNum);
                                        }
                                    }

                                }
                                break;//退出while循环，此方法结束
                            }

                            if(orderType == 2)//云平台操作添加设备的回传消息//6字节
                            {
                                int deviceSerialNew = byteToUsignedValue(dataList.get(5));//顺序号
                                String eqpId = "";
                                //逐个字节转为HexString再连起来得到eqpId（前面多一个0）
                                for(int i = 0;i < 5;i++)
                                {
                                    eqpId = eqpId + byteToHexStringSocketTask(dataList.get(i));
                                }
                                eqpId = eqpId.substring(1,10).toUpperCase();//去掉多余的0，彻底解析出网关号//左闭右开，不包括10位置，一共10-1字符
                                System.out.println("RabbitReceiver:"+netMaskId+": eqpId add success: "+eqpId);

                                //这块转移到接收解析那块
                                Equipment newEquipment = new Equipment();
                                newEquipment.setEqpId(eqpId);
                                newEquipment.setEqpName(eqpId);//设备名暂时设置为eqpId一样，等待网页后台添加设备部分获取用户输入自动修改
//                                newEquipment.setObjectId(objectId);//NULL，暂时不设置，等待网页后台添加设备部分获取用户输入自动修改
//                                newEquipment.setEqpType(eqpType);//NULL，暂时不设置，等待网页后台添加设备部分获取用户输入自动修改
                                newEquipment.setSpecial(false);//该设备默认使用默认警报值而非特殊警报值，有需要单独去设置
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String registerDate = dateFormat.format(System.currentTimeMillis()).substring(0, 10);
                                newEquipment.setRegisterDate(java.sql.Date.valueOf(registerDate));
                                newEquipment.setNetmaskId(netMaskId);
                                newEquipment.setDeviceSerial(deviceSerialNew);
                                rabbitReceiver.equipmentService.insertEquipment(newEquipment);
                                break;//退出while循环，此方法结束
                            }

                            int validLength = 0;//存储有效数据长度
                            //存储时间
                            byte[] timeByte = new byte[7];
                            String timeString = "";

                            //连接InfluxDB
                            influxDBConnector = new InfluxDBConnector("Andy","123456",
                                    "http://140.143.232.52:8086","health_data");
                            influxDBConnector.connectToDatabase();
                            influxDBConnector.setRetentionPolicy();
                            Map<String, String> tags = new HashMap<>();
                            Map<String, Object> fields = new HashMap<>();

                            while (dataList.size() >= 13) {
                                int deviceSerial = byteToUsignedValue(dataList.get(0));//数据对应的在网关数据区的序号
                                validLength = byteToUsignedValue(dataList.get(1));//获取有效数据长度
                                System.out.println("RabbitReceiver: deviceSerial: " + deviceSerial + "  validLength: " + validLength);
                                //根据deviceSerial查询数据库，获知设备类型，根据设备类型case解码
                                equipmentData = rabbitReceiver.equipmentService .queryEquipmentByNetSerial(netMaskId,deviceSerial);/////////added0521/////////
                                int typeNum = -1;

                                if (equipmentData != null)
                                {
                                    System.out.println("RabbitReceiver"+netMaskId+": EqpId: "+equipmentData.getEqpId());////////////////////////added0521
                                    //更新设备对应的object的dataCount数据加1
                                    ObjectResourceUse objectResourceUse = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                    if(objectResourceUse != null)
                                    {
                                        objectResourceUse.setDataCount(objectResourceUse.getDataCount()+1);
                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyDataCount(objectResourceUse);
                                    }else {
//                                        String date = dateformat.format(System.currentTimeMillis());
//                                        String yearMonth = date.substring(0,7);//如2019-03
                                        String year = date.substring(0,4);
                                        String month = date.substring(5,7);
                                        ObjectResourceUse newObjectResourceUse = new ObjectResourceUse();
                                        newObjectResourceUse.setObjectId(equipmentData.getObjectId());
                                        newObjectResourceUse.setYearMonth(yearMonth);
                                        java.sql.Date beginDate=java.sql.Date.valueOf(yearMonth+"-01");
                                        newObjectResourceUse.setBeginDate(beginDate);
                                        java.sql.Date endDate = beginDate;
                                        if(month.equals("01")||month.equals("03")||month.equals("05")||month.equals("07")||
                                                month.equals("08")||month.equals("10")||month.equals("12"))
                                        {
                                            endDate = java.sql.Date.valueOf(yearMonth+"-31");
                                        }else if(month.equals("04")||month.equals("06")||month.equals("09")||month.equals("11"))
                                        {
                                            endDate = java.sql.Date.valueOf(yearMonth+"-30");
                                        }else if(month.equals("02"))
                                        {
                                            if(Integer.valueOf(year)%100 == 0)
                                            {
                                                if (Integer.valueOf(year)%400 == 0)
                                                {
                                                    endDate = java.sql.Date.valueOf(yearMonth+"-29");
                                                }
                                                else
                                                {
                                                    endDate = java.sql.Date.valueOf(yearMonth+"-28");
                                                }
                                            }else {
                                                if (Integer.valueOf(year)%4 == 0)
                                                {
                                                    endDate = java.sql.Date.valueOf(yearMonth+"-29");
                                                }else {
                                                    endDate = java.sql.Date.valueOf(yearMonth+"-28");
                                                }
                                            }
                                        }else
                                        {
                                            System.out.println("RabbitReceiver: resourceUse endDate month error...");
                                        }
                                        newObjectResourceUse.setEndDate(endDate);
                                        newObjectResourceUse.setMsgCount(0);
                                        newObjectResourceUse.setOnlineTimeLength(0);
                                        newObjectResourceUse.setDataCount(0);
                                        objectResouceUseService.insertObjectResourceUse(newObjectResourceUse);

                                        newObjectResourceUse.setDataCount(1);
                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyDataCount(newObjectResourceUse);
                                    }


                                    //判断设备类型
                                    if (equipmentData.getEqpType().equals("BloodPressure01"))
                                    {
                                        typeNum = 0;
                                    }else if (equipmentData.getEqpType().equals("Temperature01"))
                                    {
                                        typeNum = 1;
                                    }else if (equipmentData.getEqpType().equals("BloodOxygen01"))
                                    {
                                        typeNum = 2;
                                    }
                                }

                                switch (typeNum) {
                                    case 0:
                                        int highBloodPressure = byteToUsignedValue(dataList.get(2));
                                        int lowBloodPressure = byteToUsignedValue(dataList.get(3));
                                        int heartRate = byteToUsignedValue(dataList.get(4));
                                        for (int i = 0; i < 7; i++) {
                                            timeByte[i] = dataList.get(i + 5);
                                        }
                                        timeString = bytesToHexString(timeByte);
                                        System.out.println("rabbitReceiver"+netMaskId+": BloodPressure... " + "highBloodPressure: " + highBloodPressure + "  lowBloodPressure: " +
                                                lowBloodPressure + "  heartBeat: " + heartRate + " bloodPressureTimeString: " + timeString);

                                        //数据过滤
                                        if (highBloodPressure == 255 && lowBloodPressure == 255 && heartRate==255)
                                        {
                                            System.out.println("rabbitReceiver"+netMaskId+": invalid BloodPressure01 255 data...");
                                        }else{
//                                            equipmentData = rabbitReceiver.equipmentService .queryEquipmentByNetSerial(netMaskId,deviceSerial);/////////added0521/////////
//                                            System.out.println("rabbitReceiver"+netMaskId+": EqpId: "+equipmentData.getEqpId());////////////////////////added0521

                                            //插入新数据到influxDB
                                            tags.clear();
                                            fields.clear();
                                            tags.put("netmaskId", String.valueOf(netMaskId));
                                            tags.put("eqpId", equipmentData.getEqpId());
                                            tags.put("objectId",equipmentData.getObjectId());
                                            tags.put("sendTime",timeString);
//                                            java.lang.Object highBloodPressureObj = highBloodPressure;////
                                            fields.put("highPressure",highBloodPressure);////
//                                            java.lang.Object lowBloodPressureObj = lowBloodPressure;////
                                            fields.put("lowPressure", lowBloodPressure);////
                                            fields.put("heartRate",heartRate);
                                            influxDBConnector.insertData("bloodPressure", tags, fields);

                                            //报警短信发送
                                            if (!equipmentData.getSpecial())//如果没设置特殊警报值
                                            {
                                                List<AlarmNormalValue> alarmNormalValueList = rabbitReceiver.alarmNormalValueService.queryAlarmNormalValueByEqpType(equipmentData.getEqpType());
                                                if(highBloodPressure != 255 && highBloodPressure > alarmNormalValueList.get(0).getValue())//血压高压高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "高压"+String.valueOf(highBloodPressure)+"超出正常范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血压高压");//////////
                                                    alarmLog.setAlarmValue(highBloodPressure);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血压高压超出最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(highBloodPressure != 0 && highBloodPressure < alarmNormalValueList.get(1).getValue())//血压高压低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "高压"+String.valueOf(highBloodPressure)+"低于正常范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血压高压");//////////
                                                    alarmLog.setAlarmValue(highBloodPressure);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血压高压低于警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(lowBloodPressure != 255 && lowBloodPressure > alarmNormalValueList.get(2).getValue())//血压低压高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "低压"+String.valueOf(lowBloodPressure)+"高于正常范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血压低压");//////////
                                                    alarmLog.setAlarmValue(lowBloodPressure);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血压低压超出最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(lowBloodPressure != 0 && lowBloodPressure < alarmNormalValueList.get(3).getValue())//血压低压低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "低压"+String.valueOf(lowBloodPressure)+"低于正常范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血压低压");//////////
                                                    alarmLog.setAlarmValue(lowBloodPressure);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血压低压低于最低警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(heartRate != 255 && heartRate > alarmNormalValueList.get(4).getValue())//心率高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "心率"+String.valueOf(heartRate)+"高于正常范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("心率");//////////
                                                    alarmLog.setAlarmValue(heartRate);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("心率高于最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(heartRate != 0 && heartRate < alarmNormalValueList.get(5).getValue())//心率低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "心率"+String.valueOf(heartRate)+"低于正常范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("心率");//////////
                                                    alarmLog.setAlarmValue(heartRate);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("心率低于最低警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                            }else
                                            {
                                                //设置了特殊警报值
                                                List<AlarmSpecialValue> alarmSpecialValueList = rabbitReceiver.alarmSpecialValueService.queryAlarmSpecialValueByEqpId(equipmentData.getEqpId());
                                                if(highBloodPressure != 255 && highBloodPressure > alarmSpecialValueList.get(0).getValue())//血压高压高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "高压"+String.valueOf(highBloodPressure)+"超出设定范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血压高压");//////////
                                                    alarmLog.setAlarmValue(highBloodPressure);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血压高压高于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(highBloodPressure != 0 && highBloodPressure < alarmSpecialValueList.get(1).getValue())//血压高压低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "高压"+String.valueOf(highBloodPressure)+"低于设定范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血压高压");//////////
                                                    alarmLog.setAlarmValue(highBloodPressure);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血压高压低于设定最低警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(lowBloodPressure != 255 && lowBloodPressure > alarmSpecialValueList.get(2).getValue())//血压低压高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "低压"+String.valueOf(lowBloodPressure)+"高于设定范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血压低压");//////////
                                                    alarmLog.setAlarmValue(lowBloodPressure);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血压低压高于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(lowBloodPressure != 0 && lowBloodPressure < alarmSpecialValueList.get(3).getValue())//血压低压低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "低压"+String.valueOf(lowBloodPressure)+"低于设定范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血压低压");//////////
                                                    alarmLog.setAlarmValue(lowBloodPressure);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血压低压低于设定最低警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(heartRate != 255 && heartRate > alarmSpecialValueList.get(4).getValue())//心率高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "心率"+String.valueOf(heartRate)+"高于设定范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("心率");//////////
                                                    alarmLog.setAlarmValue(heartRate);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("心率高于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if(heartRate != 0 && heartRate < alarmSpecialValueList.get(5).getValue())//心率低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver"+netMaskId+": objectPhoneNumber: "+objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "心率"+String.valueOf(heartRate)+"低于设定范围";
                                                    if(userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    else
                                                    {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber,userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers,equipmentData.getEqpId(),message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("心率");//////////
                                                    alarmLog.setAlarmValue(heartRate);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("心率低于设定最低警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                            }
//                                            //插入新数据到influxDB
//                                            tags.clear();
//                                            fields.clear();
//                                            tags.put("netmaskId", String.valueOf(netMaskId));
//                                            tags.put("eqpId", equipmentData.getEqpId());
//                                            tags.put("objectId",equipmentData.getObjectId());
//                                            tags.put("sendTime",timeString);
////                                            java.lang.Object highBloodPressureObj = highBloodPressure;////
//                                            fields.put("highPressure",highBloodPressure);////
////                                            java.lang.Object lowBloodPressureObj = lowBloodPressure;////
//                                            fields.put("lowPressure", lowBloodPressure);////
//                                            fields.put("heartRate",heartRate);
//                                            influxDBConnector.insertData("bloodPressure", tags, fields);
                                        }
                                        break;
                                    case 1:
                                        int bodyTemperatureInt = byteToUsignedValue(dataList.get(2)) * 256 + byteToUsignedValue(dataList.get(3));
                                        float bodyTemperature = (float) bodyTemperatureInt / 100;
                                        int envTemperatureInt = byteToUsignedValue(dataList.get(4)) * 256 + byteToUsignedValue(dataList.get(5));
                                        float envTemperature = (float) envTemperatureInt / 100;
                                        for (int i = 0; i < 7; i++) {
                                            timeByte[i] = dataList.get(i + 6);
                                        }
                                        timeString = bytesToHexString(timeByte);
                                        System.out.println("rabbitReceiver"+netMaskId+": Temperature... " + "bodyTemperature: " + bodyTemperature + "  envTemperature: " +
                                                envTemperature + " temperatureTimeString: " + timeString);
                                        //数据过滤
                                        if (bodyTemperature == 0 && envTemperature == 0)
                                        {
                                            System.out.println("rabbitReceiver"+netMaskId+": invalid Temperature01 0 data...");
                                        }else{
                                            equipmentData = rabbitReceiver.equipmentService .queryEquipmentByNetSerial(netMaskId,deviceSerial);/////////added0521/////////
                                            System.out.println("rabbitReceiver"+netMaskId+": EqpId: "+equipmentData.getEqpId());////////////////////////added0521

                                            //报警短信发送
                                            if (!equipmentData.getSpecial())//如果没设置特殊警报值
                                            {
                                                List<AlarmNormalValue> alarmNormalValueList = rabbitReceiver.alarmNormalValueService.queryAlarmNormalValueByEqpType(equipmentData.getEqpType());
                                                if (bodyTemperature < 41.6 &&bodyTemperature > alarmNormalValueList.get(0).getValue())//体温高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "体温" + String.valueOf(bodyTemperature) + "超出正常范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("体温");//////////
                                                    alarmLog.setAlarmValue(bodyTemperature);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("心率高于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if (bodyTemperature > 30.0 && bodyTemperature < alarmNormalValueList.get(1).getValue())//体温低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "体温" + String.valueOf(bodyTemperature) + "低于正常范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("体温");//////////
                                                    alarmLog.setAlarmValue(bodyTemperature);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("体温低于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if (envTemperature < 60 && envTemperature > alarmNormalValueList.get(2).getValue())//环温高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "环温" + String.valueOf(envTemperature) + "高于正常范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("环境温度");//////////
                                                    alarmLog.setAlarmValue(envTemperature);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("环境温度高于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if (envTemperature > -50 && envTemperature < alarmNormalValueList.get(3).getValue())//环温低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "环温" + String.valueOf(envTemperature) + "低于正常范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("环境温度");//////////
                                                    alarmLog.setAlarmValue(envTemperature);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("环境温度低于设定最低警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                            }else
                                            {
                                                //设置了特殊值
                                                List<AlarmSpecialValue> alarmSpecialValueList = rabbitReceiver.alarmSpecialValueService.queryAlarmSpecialValueByEqpId(equipmentData.getEqpId());
                                                if (bodyTemperature < 41.6 &&bodyTemperature > alarmSpecialValueList.get(0).getValue())//体温高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "体温" + String.valueOf(bodyTemperature) + "超出设定范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("体温");//////////
                                                    alarmLog.setAlarmValue(bodyTemperature);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("体温高于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if (bodyTemperature > 34.0 && bodyTemperature < alarmSpecialValueList.get(1).getValue())//体温低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "体温" + String.valueOf(bodyTemperature) + "低于设定范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("体温");//////////
                                                    alarmLog.setAlarmValue(bodyTemperature);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("体温低于设定最低警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if (envTemperature < 60 && envTemperature > alarmSpecialValueList.get(2).getValue())//环温高于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "环温" + String.valueOf(envTemperature) + "高于设定范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("环境温度");//////////
                                                    alarmLog.setAlarmValue(envTemperature);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("环境温度高于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                                if (envTemperature > -50 && envTemperature < alarmSpecialValueList.get(3).getValue())//环温低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "环温" + String.valueOf(envTemperature) + "低于设定范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("环境温度");//////////
                                                    alarmLog.setAlarmValue(envTemperature);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("环境温度低于设定最低警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                            }
                                            //插入新数据到influxDB
                                            tags.clear();
                                            fields.clear();
                                            tags.put("netmaskId", String.valueOf(netMaskId));
                                            tags.put("eqpId", equipmentData.getEqpId());
                                            tags.put("objectId",equipmentData.getObjectId());
//                                        tags.put("eqpId", "A00030101");
//                                        tags.put("objectId","hitwhob001");
                                            tags.put("sendTime",timeString);
                                            fields.put("bodyTemp",bodyTemperature);
                                            fields.put("envTemp",envTemperature);
                                            influxDBConnector.insertData("temperature", tags, fields);

                                        }
                                        break;
                                    case 2:
                                        int bloodOxygenDegree = byteToUsignedValue(dataList.get(2));
                                        for (int i = 0; i < 7; i++) {
                                            timeByte[i] = dataList.get(i + 3);
                                        }
                                        timeString = bytesToHexString(timeByte);
                                        System.out.println("rabbitReceiver"+netMaskId+": BloodOxygen... " + "bodyOxygenDegree: " + bloodOxygenDegree + " timeString: " +
                                                timeString);
                                        //数据过滤
                                        if (bloodOxygenDegree == 0)
                                        {
                                            System.out.println("rabbitReceiver"+netMaskId+": invalid BloodOxygen01 0 data...");
                                        }else {
                                            equipmentData = rabbitReceiver.equipmentService .queryEquipmentByNetSerial(netMaskId,deviceSerial);/////////added0521/////////
                                            System.out.println("rabbitReceiver"+netMaskId+": EqpId: "+equipmentData.getEqpId());////////////////////////added0521
                                            //插入新数据到influxDB
                                            tags.clear();
                                            fields.clear();
                                            tags.put("netmaskId", String.valueOf(netMaskId));
                                            tags.put("eqpId", equipmentData.getEqpId());
                                            tags.put("objectId",equipmentData.getObjectId());
//                                        tags.put("eqpId", "A00060302");
//                                        tags.put("objectId","hitwhob001");
                                            tags.put("sendTime",timeString);
                                            fields.put("spo2",bloodOxygenDegree);
                                            influxDBConnector.insertData("bloodOxygen", tags, fields);
                                            //报警短信发送
                                            if (!equipmentData.getSpecial())//如果没设置特殊警报值
                                            {
                                                List<AlarmNormalValue> alarmNormalValueList = rabbitReceiver.alarmNormalValueService.queryAlarmNormalValueByEqpType(equipmentData.getEqpType());
                                                if (bloodOxygenDegree < alarmNormalValueList.get(0).getValue())//血氧浓度低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "血氧" + String.valueOf(bloodOxygenDegree) + "低于正常范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 =rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血氧浓度");//////////
                                                    alarmLog.setAlarmValue(bloodOxygenDegree);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血氧浓度高于最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                            }else
                                            {
                                                //特殊警报值
                                                List<AlarmSpecialValue> alarmSpecialValueList = rabbitReceiver.alarmSpecialValueService.queryAlarmSpecialValueByEqpId(equipmentData.getEqpId());
                                                if (bloodOxygenDegree < alarmSpecialValueList.get(0).getValue())//血氧浓度低于警戒值
                                                {
                                                    String objectId = equipmentData.getObjectId();
                                                    com.example.humanhealthmonitor.pojo.Object object = rabbitReceiver.objectService.queryObjectByObjectId(objectId);
                                                    String objectPhoneNumber = object.getObjectTel();
                                                    System.out.println("rabbitReceiver" + netMaskId + ": objectPhoneNumber: " + objectPhoneNumber);
                                                    String userId = object.getUserId();
                                                    String message = "血氧" + String.valueOf(bloodOxygenDegree) + "低于设定范围";
                                                    if (userId.equals(objectId))//说明是用户自己，只需要单发短信
                                                    {
                                                        try {
                                                            cloudMsgUtil.sendSingleCloudMsg(objectPhoneNumber, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加1
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+1);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    } else {
                                                        String userPhoneNumber = rabbitReceiver.userService.queryUserByUserId(userId).getUserTel();
                                                        String[] phoneNumbers = {objectPhoneNumber, userPhoneNumber};
                                                        try {
                                                            cloudMsgUtil.sendMultiCloudMsg(phoneNumbers, equipmentData.getEqpId(), message);
                                                        } catch (HTTPException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        //更新设备对应的object的msgCount数据加2
                                                        ObjectResourceUse objectResourceUse2 = rabbitReceiver.objectResouceUseService.queryObjectResourceUseByObjectIdYearMonth(equipmentData.getObjectId(),yearMonth);
                                                        objectResourceUse2.setMsgCount(objectResourceUse2.getMsgCount()+2);
                                                        rabbitReceiver.objectResouceUseService.updateObjectResourceUseOnlyMsgCount(objectResourceUse2);
                                                    }
                                                    //插入一条AlarmLog
                                                    alarmLog.setObjectId(objectId);
                                                    alarmLog.setEqpId(equipmentData.getEqpId());
                                                    alarmLog.setAlarmType("血氧浓度");//////////
                                                    alarmLog.setAlarmValue(bloodOxygenDegree);//////////
                                                    String writeTime = dateformat.format(System.currentTimeMillis());
                                                    alarmLog.setWriteTime(java.sql.Timestamp.valueOf(writeTime));
                                                    alarmLog.setDetail("血氧浓度高于设定最高警戒值");/////////
                                                    rabbitReceiver.alarmLogService.insertAlarmLog(alarmLog);
                                                }
                                            }

                                        }

                                        break;
                                    default:
                                        System.out.println("rabbitReceiver"+netMaskId+": Exception: unknown device type...");
                                        break;
                                }
                                dataList = dataList.subList(13, dataList.size());//去掉已经处理的13个字节，进入下一个循环
                            }
                            //内圈while处理完之后
                            System.out.println("RabbitReceiver: Info: data section analyze completed...");
                            //可能存在dataLength写的值大于实际值，出现dataLength + 9 < byteArrayList.size()的情况，于是加了一个判断来避免异常
                            if(dataLength + 9 < byteArrayList.size())//如果小于，则截取然后继续，针对的是一个数据包内多个回复帧的情况
                            {
                                byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
                            }
                            else//如果数据分析完毕，直接跳出外圈while循环
                            {
                                System.out.println("RabbitReceiver: Info: package analyze completed....");
                                break;
                            }
                            //校验和验证，验证成功继续，然后是判断设备类型，这里先简易地写死，00是血压，01是温度。02是血氧，
                            //然后把数据库里面的设备号改成网关一样的，或者设备上加字段，设备号之外再加网关和网关内设备序号
                            //取出数据后存入influxdb

                        } else {//校验和错误，去掉最前面的一个字节，进入下一个循环
                            System.out.println("RabbitReceiver: data check error...");
                            byteArrayList.remove(0);
                        }
                    } else {//结尾格式不是AABB，证明数据损坏，去掉最前面的一个字节，进入下一个循环
                        System.out.println("RabbitReceiver: package tail is broken...");
                        byteArrayList.remove(0);
                    }

                } else {//字节总长度达不到，证明数据可能损坏，去掉最前面的一个字节，进入下一个循环
                    System.out.println("RabbitReceiver: package length error compared to dataLength, maybe package is broken...");
                    byteArrayList.remove(0);
//                    break;
                }
//                byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
                //可能存在dataLength写的值大于实际值，出现dataLength + 9 < byteArrayList.size()的情况，于是加了一个判断来避免异常
//                if(dataLength + 9 < byteArrayList.size())//如果小于，则截取然后继续
//                {
//                    byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
//                }
//                else//否则直接跳出while循环
//                {
//                    System.out.println("Info: package is deserted...no valuable data...");
//                    break;
//                }
            } else {//如果数据头不是FEFE，去掉前面的一个字节，进入下一个循环
                byteArrayList.remove(0);
            }
            //外圈while循环每圈一定会执行的位置
        }
    }

}
