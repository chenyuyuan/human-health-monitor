##### 已改：

1.MySQL数据库netmaskInfo添加port字段，记录网关端口，与ipAddress字段共同确定唯一网关

##### 待改：

1.密码加密

##### 计划：

1.在添加设备的Controller：equipmentAddResult里，使用redis服务代替等待十五秒后查询数据库有无添加结果

2.