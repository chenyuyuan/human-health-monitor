package com.humanhealthmonitor;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsMultiSenderResult;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;

public class CloudMsgUtil {
    private int appid = 1400176937; // 1400开头// 短信应用SDK AppID
    private String appkey = "9b9899ca5e4510220410d6298df2f3cc";// 短信应用SDK AppKey
    private int templateId = 259905; // 短信模板ID，真实的模板ID需要在短信控制台中申请,如："您的验证码是: {1}"
    private String smsSign = "威小工健康";// 签名// NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`

    //短信单发
    public void sendSingleCloudMsg(String phoneNumber,String eqpId,String warningMsg) throws HTTPException, IOException {
        String[] params = {eqpId,warningMsg};//数组具体的元素个数和模板中变量个数必须一致//{"A00020201","高压168超出正常范围"}
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,
                templateId, params, smsSign, "", "");//单发// 签名参数未提供或者为空时，会使用默认签名发送短信
        System.out.println(result.toString());
        String reStr;//结果字符串
        if(result.result == 0) {
            reStr = "send single message successfully";
        }
        else {
            reStr = "!send  single message error";
        }
        System.out.println(reStr);
    }

    //短信多发
    public void sendMultiCloudMsg(String[] phoneNumbers,String eqpId,String warningMsg) throws HTTPException, IOException {
        String[] params = {eqpId,warningMsg};//数组具体的元素个数和模板中变量个数必须一致
        SmsMultiSender msender = new SmsMultiSender(appid, appkey);
        SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                templateId, params, smsSign, "", "");//群发// 签名参数未提供或者为空时，会使用默认签名发送短信
        System.out.println(result.toString());
        String reStr;//结果字符串
        if(result.result == 0) {
            reStr = "send multi cloud message successfully";
        }
        else {
            reStr = "!send multi message error";
        }
        System.out.println(reStr);
    }
}
