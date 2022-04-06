package com.it.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 短信发送工具类
 *
 * @author Administrator
 */

@Component
public class SMSUtils {

    @Value("${sms.signName}")
    private static String signName;
    @Value("${sms.templateCode}")
    private static String templateCode;
    @Value("${sms.accessKeyId}")
    private static String accessKeyId;
    @Value("${sms.accessKeySecret}")
    private static String accessKeySecret;

    /**
     * 发送短信
     *
     * @param phoneNumbers 手机号码
     * @return 验证码
     */
    public static String sendMessage(String phoneNumbers) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        // 生成随机验证码
        String code = ValidateCodeUtils.generateValidateCode4String(4);

        SendSmsRequest request = new SendSmsRequest();
        request.setSysRegionId("cn-hangzhou");
        request.setPhoneNumbers(phoneNumbers);
        request.setSignName(signName);
        request.setTemplateCode(templateCode);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        try {
            SendSmsResponse response = client.getAcsResponse(request);
            System.out.println("短信发送成功");
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return code;
    }

    private SMSUtils() {
    }
}
