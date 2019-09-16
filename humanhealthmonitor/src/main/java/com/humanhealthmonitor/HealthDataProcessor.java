package com.humanhealthmonitor;

//@Controller
public class HealthDataProcessor {

//    @Autowired
//    private EquipmentService equipmentService;

    private InfluxDBConnector influxDBConnector;//创建influxDB连接实例

    //处理Socket收到的信息
//    public void socketInfoProcess(List<Byte> byteArrayList) {
////        Equipment equipmentData = equipmentService.queryEquipmentByNetSerial(1,0);//////////////////////
////        System.out.println("EqpId: "+equipmentData.getEqpId());////////////////////////
//        while (byteArrayList.size() >= 22) {
//            if (byteArrayList.get(0) == (byte) 0xFE && byteArrayList.get(1) == (byte) 0xFE) {
//                int dataLength = byteToUsignedValue(byteArrayList.get(4)) * 256 + byteToUsignedValue(byteArrayList.get(5));//获取传感器数据长度
//                if (byteArrayList.size() >= (dataLength + 6 + 3))//字节总长度达不到，证明数据损坏，这里的9是数据前6后3附加字节总长
//                {
//                    if (byteArrayList.get(dataLength + 9 - 2) == (byte) 0xAA && byteArrayList.get(dataLength + 9 - 1) == (byte) 0xBB)//验证结尾格式AABB
//                    {
//                        //校验和计算
//                        int check = 0;
//                        for (int i = 0; i < dataLength; i++) {
//                            check += byteArrayList.get(i + 6);
//                        }
//                        check = Math.abs(check) % 64;
//                        if (check == byteArrayList.get(dataLength + 9 - 3))//比对数据发送前后的校验和，一致则继续，不一致说明数据传输错误//这里需要判断包长会否大于大dataLength+9-3，防止出错
//                        {
//                            System.out.println("check pass...");
//                            int netMaskId = byteToUsignedValue(byteArrayList.get(2));//这里要注意，网关id是按0x11字面16转10进制的值17来与上面对应
//                            int orderType = byteToUsignedValue(byteArrayList.get(3));//04是全部设备信息，03是指定设备信息
//                            System.out.println("netMaskId: " + netMaskId + "  orderType: " + orderType);
//                            List<Byte> dataList = byteArrayList.subList(6, 6 + dataLength);//取出校验成功的数据区数据，放到dataList中
//
//                            int validLength = 0;//存储有效数据长度
//                            //存储时间
//                            byte[] timeByte = new byte[7];
//                            String timeString = "";
//
//                            //连接InfluxDB
//                            influxDBConnector = new InfluxDBConnector("Andy","123456",
//                                    "http://140.143.232.52:8086","health_data");
//                            influxDBConnector.connectToDatabase();
//                            influxDBConnector.setRetentionPolicy();
//                            Map<String, String> tags = new HashMap<>();
//                            Map<String, Object> fields = new HashMap<>();
//
//                            while (dataList.size() >= 13) {
//                                int deviceSerial = byteToUsignedValue(dataList.get(0));//数据对应的在网关数据区的序号
//                                System.out.println("deviceSerial: "+deviceSerial);
////                                Equipment equipmentData = equipmentMapper.queryEquipmentByNetSerial(netMaskId,deviceSerial);//查询指定网关和顺序号的设备信息0416
////                                if(equipmentData == null)
////                                {
////                                    System.out.println("Info: Equipment Matching for Data Not Found...");
////                                    continue;
////                                }
//                                validLength = byteToUsignedValue(dataList.get(1));//获取有效数据长度
//                                System.out.println("deviceSerial: " + deviceSerial + "  validLength: " + validLength);
//                                //根据deviceSerial查询数据库，获知设备类型，根据设备类型case解码，这个后来再做，先用deviceSerial直接测试
//                                switch (deviceSerial) {
//                                    case 0:
//                                        int highBloodPressure = byteToUsignedValue(dataList.get(2));
//                                        int lowBloodPressure = byteToUsignedValue(dataList.get(3));
//                                        int heartRate = byteToUsignedValue(dataList.get(4));
//                                        for (int i = 0; i < 7; i++) {
//                                            timeByte[i] = dataList.get(i + 5);
//                                        }
//                                        timeString = bytesToHexString(timeByte);
//                                        System.out.println("BloodPressure... " + "highBloodPressure: " + highBloodPressure + "  lowBloodPressure: " +
//                                                lowBloodPressure + "  heartBeat: " + heartRate + " bloodPressureTimeString: " + timeString);
//
//                                        //插入新数据到influxDB
//                                        tags.clear();
//                                        fields.clear();
////                                        List<BloodPressure01> listBloodPressure01 = new ArrayList<>();
////                                        BloodPressure01 bloodPressure01 = new BloodPressure01();
////                                        bloodPressure01.setNetmaskId(String.valueOf(netMaskId));
//
////                                        bloodPressure01.setSensorId(equipmentData.getEqpId());
////                                        bloodPressure01.setObjectId(equipmentData.getObjectId());
////                                        bloodPressure01.setSendTime(timeString);
////                                        bloodPressure01.setHighPressure(highBloodPressure);
////                                        bloodPressure01.setLowPressure(lowBloodPressure);
////                                        bloodPressure01.setHeartRate(heartRate);
//
//                                        tags.put("netmaskId", String.valueOf(netMaskId));
////                                        tags.put("eqpId", equipmentData.getEqpId());
////                                        tags.put("objectId",equipmentData.getObjectId());
//                                        tags.put("eqpId", "A00020201");
//                                        tags.put("objectId","hitwhob001");
//                                        tags.put("sendTime",timeString);
//                                        fields.put("highPressure",highBloodPressure);
//                                        fields.put("lowPressure",lowBloodPressure);
//                                        fields.put("heartRate",heartRate);
//                                        influxDBConnector.insertData("bloodPressure", tags, fields);
//                                        break;
//                                    case 1:
//                                        int bodyTemperatureInt = byteToUsignedValue(dataList.get(2)) * 256 + byteToUsignedValue(dataList.get(3));
//                                        float bodyTemperature = (float) bodyTemperatureInt / 100;
//                                        int envTemperatureInt = byteToUsignedValue(dataList.get(4)) * 256 + byteToUsignedValue(dataList.get(5));
//                                        float envTemperature = (float) envTemperatureInt / 100;
//                                        for (int i = 0; i < 7; i++) {
//                                            timeByte[i] = dataList.get(i + 6);
//                                        }
//                                        timeString = bytesToHexString(timeByte);
//                                        System.out.println("Temperature... " + "bodyTemperature: " + bodyTemperature + "  envTemperature: " +
//                                                envTemperature + " temperatureTimeString: " + timeString);
//
//                                        //插入新数据到influxDB
//                                        tags.clear();
//                                        fields.clear();
//                                        tags.put("netmaskId", String.valueOf(netMaskId));
////                                        tags.put("eqpId", equipmentData.getEqpId());
////                                        tags.put("objectId",equipmentData.getObjectId());
//                                        tags.put("eqpId", "A00030101");
//                                        tags.put("objectId","hitwhob001");
//                                        tags.put("sendTime",timeString);
//                                        fields.put("bodyTemp",bodyTemperature);
//                                        fields.put("envTemp",envTemperature);
//                                        influxDBConnector.insertData("temperature", tags, fields);
//
//                                        break;
//                                    case 2:
//                                        int bloodOxygenDegree = byteToUsignedValue(dataList.get(2));
//                                        for (int i = 0; i < 7; i++) {
//                                            timeByte[i] = dataList.get(i + 3);
//                                        }
//                                        timeString = bytesToHexString(timeByte);
//                                        System.out.println("BloodOxygen... " + "bodyOxygenDegree: " + bloodOxygenDegree + " timeString: " +
//                                                timeString);
//
//                                        //插入新数据到influxDB
//                                        tags.clear();
//                                        fields.clear();
//                                        tags.put("netmaskId", String.valueOf(netMaskId));
////                                        tags.put("eqpId", equipmentData.getEqpId());
////                                        tags.put("objectId",equipmentData.getObjectId());
//                                        tags.put("eqpId", "A00060302");
//                                        tags.put("objectId","hitwhob001");
//                                        tags.put("sendTime",timeString);
//                                        fields.put("spo2",bloodOxygenDegree);
//                                        influxDBConnector.insertData("bloodOxygen", tags, fields);
//
//                                        break;
//                                    default:
//                                        System.out.println("Other device...");
//                                        break;
//                                }
//                                dataList = dataList.subList(13, dataList.size());//去掉已经处理的13个字节，进入下一个循环
//                            }
//                            //内圈while处理完之后
//                            System.out.println("Info: data section analyze completed...");
//                            //可能存在dataLength写的值大于实际值，出现dataLength + 9 < byteArrayList.size()的情况，于是加了一个判断来避免异常
//                            if(dataLength + 9 < byteArrayList.size())//如果小于，则截取然后继续，针对的是一个数据包内多个回复帧的情况
//                            {
//                                byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
//                            }
//                            else//如果数据分析完毕，直接跳出外圈while循环
//                            {
//                                System.out.println("Info: package analyze completed....");
//                                break;
//                            }
//                            //校验和验证，验证成功继续，然后是判断设备类型，这里先简易地写死，00是血压，01是温度。02是血氧，
//                            //然后把数据库里面的设备号改成网关一样的，或者设备上加字段，设备号之外再加网关和网关内设备序号
//                            //取出数据后存入influxdb
//
//                        } else {//校验和错误，去掉最前面的一个字节，进入下一个循环
//                            System.out.println("InfoProcessor: data check error...");
//                            byteArrayList.remove(0);
//                        }
//                    } else {//结尾格式不是AABB，证明数据损坏，去掉最前面的一个字节，进入下一个循环
//                        System.out.println("InfoProcessor: package tail is broken...");
//                        byteArrayList.remove(0);
//                    }
//
//                } else {//字节总长度达不到，证明数据可能损坏，去掉最前面的一个字节，进入下一个循环
//                    System.out.println("InfoProcessor: package length error compared to dataLength, maybe package is broken...");
//                    byteArrayList.remove(0);
////                    break;
//                }
////                byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
//                //可能存在dataLength写的值大于实际值，出现dataLength + 9 < byteArrayList.size()的情况，于是加了一个判断来避免异常
////                if(dataLength + 9 < byteArrayList.size())//如果小于，则截取然后继续
////                {
////                    byteArrayList = byteArrayList.subList(dataLength + 9, byteArrayList.size());
////                }
////                else//否则直接跳出while循环
////                {
////                    System.out.println("Info: package is deserted...no valuable data...");
////                    break;
////                }
//            } else {//如果数据头不是FEFE，去掉前面的一个字节，进入下一个循环
//                byteArrayList.remove(0);
//            }
//            //外圈while循环每圈一定会执行的位置
//        }
//    }
//    //根据netmaskId和deviceSerial查询设备信息
//    public Equipment queryEquipmentByNetMaskSerial(int netmaskId, int deviceSerial)
//    {
//        return equipmentMapper.queryEquipmentByNetSerial(netmaskId,deviceSerial);
//    }
    //字节转为16进制字符串，如“FE”
//    public String bytesToHexString(byte[] src) {
//        StringBuilder stringBuilder = new StringBuilder("");
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] & 0xFF;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv);
//        }
//        return stringBuilder.toString();
//    }
//
//    public byte[] toByteArray(String hexString) {
//        if (hexString.equals("")) {
//            System.out.println("toByteArray(): this hexString is empty");
//            throw new IllegalArgumentException("this hexString must not be empty");
//        }
//        hexString = hexString.toLowerCase();
//        final byte[] byteArray = new byte[hexString.length() / 2];
//        int k = 0;
//        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
//            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
//            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
//            byteArray[i] = (byte) (high << 4 | low);
//            k += 2;
//        }
//        return byteArray;
//    }
//
//    //将1个字节的8个位解析成无符号0-255的值
//    public int byteToUsignedValue(Byte b) {
//        int bInt = (int) b;
//        if (bInt >= 0) {
//            return bInt;
//        } else {
//            return (bInt + 256);
//        }
//    }
}
