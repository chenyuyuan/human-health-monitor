package com.humanhealthmonitor;

import com.humanhealthmonitor.pojo.*;
import com.humanhealthmonitor.service.*;
import com.humanhealthmonitor.pojo.Equipment;
import com.humanhealthmonitor.service.*;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static com.humanhealthmonitor.util.ByteUtils.byteToUnsignedValue;

@Component
public class RabbitReceiver implements Runnable{

    @Autowired
    private EquipmentService equipmentService; // added0521
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

    private static RabbitReceiver rabbitReceiver; // added0521

    private final static String EXCHANGE_NAME = "amq.direct";
    private final static String QUEUE_NAME = "health_queue";
    private InfluxDBConnector influxDBConnector; // 创建influxDB连接实例
    private CloudMsgUtil cloudMsgUtil = new CloudMsgUtil(); // 云短信工具
    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @PostConstruct
    public void init() { // added0521
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
            // System.out.println("RabbitReceiver: rabbit receiver start...");
            handleRabbitReceived();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleRabbitReceived() throws IOException,TimeoutException {

        // System.out.println("create ConnectionFactory...");
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("140.143.232.52");
        factory.setUsername("Andy");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // 消费者指定要订阅的队列
        // 第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
        // 第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、第四个参数为当所有消费者客户端连接断开时是否自动删除队列、
        // 第五个参数为队列的其他参数
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        String routingKey = "health";
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, routingKey);
        System.out.println("RabbitReceiver: Waiting for messages...");
        // channel.basicQos(1);//告诉消费一次只获取一条消息，处理完再获取下一条
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)//body是字节数组类型
                    throws IOException {
                List<Byte> byteArrayList = new ArrayList<>();//字节列表



                // TODO : 直接传byte类型的body给socketInfoProcess



                for(int i = 0;i < body.length;i++)
                {
                    byteArrayList.add(body[i]);
                }
                System.out.println("RabbitReceiver: byteArrayList: "+byteArrayList);
                socketInfoProcess(byteArrayList);
            }
        };

        /**订阅消息
         * autoAck: 应答模式，true:自动应答，即消费者获取到消息，该消息就会从队列中删除掉，
         * false:手动应答，当从队列中取出消息后，需要程序员手动调用方法应答，
         * 如果没有应答，该消息还会再放进队列中，就会出现该消息一直没有被消费掉的现象
         */
        channel.basicConsume(QUEUE_NAME, true, consumer);//basicConsume订阅模式可以从队列中一直持续的自动的接收消息,basicGet一次只能获取一条消息，如果还想再获取下一条还要再次调用
    }

    //处理Socket收到的信息
    public void socketInfoProcess(List<Byte> byteArrayList) {
        String date = dateformat.format(System.currentTimeMillis());
        String yearMonth = date.substring(0,7);//如2019-03
        Equipment equipmentData;//added0521
        while (byteArrayList.size() >= 8) {
            int orderType = byteToUnsignedValue(byteArrayList.get(2)); // 指令码
            int responseLength = byteToUnsignedValue(byteArrayList.get(3)); // 回复内容长度
            byte[] responseContent = new byte[responseLength - 1];  // 不包括校验和(扣掉1位校验和)
            int checkSum = byteToUnsignedValue(byteArrayList.get(byteArrayList.size()-3)); // 校验和

            if (byteArrayList.get(0) != (byte) 0xFE || byteArrayList.get(1) != (byte) 0xFE) {
                break;
            }
            if (byteArrayList.size() != responseLength + 5) {
                break;
            }
            if (byteArrayList.get(byteArrayList.size() - 2) != (byte) 0xAA || byteArrayList.get(byteArrayList.size() - 1) != (byte) 0xBB) {
                break;
            }

            // 修改通信协议protocolState
            // TODO



            // 检查校验和
            int check = 0;
            for (byte b : responseContent) {
                check = check + b;
                if(check > 256) {
                    check = check % 256;
                }
            }
            if (check != checkSum) {
                break;
            }

            // 将回复信息放到responseContent
            for (int i = 0; i < responseLength - 1;++i) {
                responseContent[i] = byteArrayList.get(i + 3);
            }

            if (orderType == 1) {
                handleOrder1Response(responseContent);  // responseContent包括1位通信类型和n位网关号
            }
            else if (orderType == 2) {
                handleOrder2Response(responseContent);  // responseContent包括5位设备ID和1位成功失败标识
            }
            else if (orderType == 3) {
                handleOrder3and4Response(responseContent);
            }
            else if (orderType == 4) {
                handleOrder3and4Response(responseContent);
            }
            else if (orderType == 5) {
                handleOrder5Response(responseContent);
            }
            else if (orderType == 6) {
                handleOrder6Response(responseContent);
            }
            else if (orderType == 7) {
                handleOrder7Response(responseContent);
            }

        }
    }


    public int byteArrayToInt (byte[] byteArray, int start, int end) {
        if(byteArray == null || byteArray.length == 0 || start > end || start < 0 || end >= byteArray.length) return -1;
        int res = 0;
        byte[] a = new byte[4];
        int i = a.length - 1, j = byteArray.length - 1;
        for (; i >= 0; --i, --j) {
            if(j >= 0)
                a[i] = byteArray[j];
            else
                a[i] = 0;
        }
        int v0 = (a[0] & 0xff) << 24;
        int v1 = (a[1] & 0xff) << 16;
        int v2 = (a[2] & 0xff) << 8;
        int v3 = (a[3] & 0xff) << 0;

        return v0 + v1 + v2 + v3;
    }

    /**
     * 将byte[]转为各种进制的字符串
     * @param radix 基数可以转换进制的范围(2-36)，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public String byteArrayToString (byte[] byteArray, int radix) {
        return new BigInteger(1, byteArray).toString(radix);
    }
    // 人体红外线传感器： 2字节环境温度 + 2字节体温
    public void processDataType1(byte[] byteArrayData) {
        double ambientTemp ,bodyTemp;

    }
    // 血压设备： 1字节心率 + 1字节收缩压(systolic pressure) + 1字节舒张压(diastolic pressure)
    public void processDataType2(byte[] byteArrayData) {
        int heartRate = byteToUnsignedValue(byteArrayData[0]);
        int systolicPressure = byteToUnsignedValue(byteArrayData[1]);
        int diastolicPressure = byteToUnsignedValue(byteArrayData[2]);

    }
    // 血氧设备： 血氧饱和度(简写SpO2)
    public void processDataType3(byte[] byteArrayData) {
        double SpO2;

    }
    // 床垫： 2字节心跳 + 2字节呼吸 + 2字节温度 + 1字节动作
    public void processDataType4(byte[] byteArrayData){
        int heartRate, breathFrequency, temp, action;

    }

    // 1位通信类型 + n位网关号
    public void handleOrder1Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int communicationMethod = responseContent[0];  // 通信类型
        //int netMaskID = byteArrayToInt(responseContent, 1, responseContent.length - 1);  // 网关ID

        // 将网关号所在的字节拷贝到字节数组charArrayNetmaskID上
        byte[] charArrayNetmaskID = new byte[responseContent.length - 1];
        System.arraycopy(responseContent, 1, charArrayNetmaskID, 0, charArrayNetmaskID.length);
        String netMaskID = byteArrayToString(charArrayNetmaskID,10);

    }
    // n位设备ID + 1位标识
    public void handleOrder2Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int flag = byteToUnsignedValue(responseContent[responseContent.length - 1]);
        byte[] charArrayDeviceID = new byte[responseContent.length - 1];
        System.arraycopy(responseContent, 0, charArrayDeviceID, 0, charArrayDeviceID.length);
        String deviceID = byteArrayToString(charArrayDeviceID, 16);


    }
    // 1位ID长度（n） + n位设备ID + 1位时间戳长度（m） + m位时间戳 + 1位传感器数据长度（p） + p位传感器数据
    public void handleOrder3and4Response(byte[] responseContent) {
        if(responseContent.length == 0) return;

        int deviceIDLength = byteToUnsignedValue(responseContent[0]);
        int timestampLength = 4;
        int sensorDataLength = byteToUnsignedValue(responseContent[1 + deviceIDLength + 1 + timestampLength + 1 - 1]);

        byte[] byteArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, 1, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);

        byte[] byteArrayTimestamp = new byte[timestampLength];
        System.arraycopy(responseContent, deviceIDLength + 2, byteArrayTimestamp, 0, byteArrayTimestamp.length);
        String timestamp = byteArrayToString(byteArrayTimestamp, 10);

        byte[] byteArraySensorData = new byte[sensorDataLength];
        System.arraycopy(responseContent, deviceIDLength + timestampLength + 3 , byteArraySensorData, 0, byteArraySensorData.length);

        String sensortype = deviceID.substring(5,7);

        if (sensortype == "01") {
            processDataType1(byteArraySensorData);
        }
        if (sensortype == "02") {
            processDataType2(byteArraySensorData);
        }
        if (sensortype == "03") {
            processDataType3(byteArraySensorData);
        }
        if (sensortype == "04") {
            processDataType4(byteArraySensorData);
        }

    }
    public void handleOrder5Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int flag = byteToUnsignedValue(responseContent[0]);

    }
    public void handleOrder6Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int netMaskIDLength = byteToUnsignedValue(responseContent[0]);
        int deviceIDLength = byteToUnsignedValue(responseContent[1 + netMaskIDLength + 1 - 1]);

        byte[] byteArrayNetMaskID = new byte[netMaskIDLength];
        System.arraycopy(responseContent, 1, byteArrayNetMaskID, 0, byteArrayNetMaskID.length);
        String netmaskID = byteArrayToString(byteArrayNetMaskID, 10);
        byte[] byteArrayDeviceID = new byte[deviceIDLength];
        System.arraycopy(responseContent, netMaskIDLength + 2, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);
        byte[] byteArrayTimestamp = new byte[4];
        System.arraycopy(responseContent, netMaskIDLength + deviceIDLength + 2, byteArrayNetMaskID, 0, 4);
        int timestamp = byteArrayToInt(byteArrayTimestamp, 0, byteArrayTimestamp.length - 1);

    }
    public void handleOrder7Response(byte[] responseContent) {
        if(responseContent.length == 0) return;
        int flag = byteToUnsignedValue(responseContent[responseContent.length - 1]);
        byte[] byteArrayDeviceID = new byte[responseContent.length - 1];
        System.arraycopy(responseContent, 0, byteArrayDeviceID, 0, byteArrayDeviceID.length);
        String deviceID = byteArrayToString(byteArrayDeviceID, 16);

    }

}
