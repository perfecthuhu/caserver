package com.card.alumni.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author sunxiaodong10 2019/12/9
 * @date 8:13 PM
 */
public class VerificationCodeUtils {

    public static String getVerificationCode(String phone) {
        String code = buildVerification(phone);
        int retryNum = 0;
        do {
            if (retryNum >= 3) {
                break;
            }
            retryNum ++;
        } while (!sendMsg(phone, code));
        return code;
    }

    private static boolean sendMsg(String phone, String code) {
        String sendUrl = "http://smssh1.253.com/msg/send/json";
        Map map = new HashMap();
        //API账号
        map.put("account","N8396454");
        //API密码
        map.put("password","yvl0gmA4j");
        //短信内容
        map.put("msg","【永鑫科技】呜呜呜呜呜呜" + code);
        //手机号
        map.put("phone",phone);
        //是否需要状态报告
        map.put("report","true");
        //自定义扩展码
        map.put("extend","123");
        JSONObject js = (JSONObject) JSONObject.toJSON(map);
        String result = HttpClientUtils.sendSmsByPost(sendUrl, js.toString());
        if (StringUtils.isEmpty(result)) {
            sendMsg(phone, code);
        }
        String isSussess = (String) JSONObject.parseObject(result).get("code");
        Integer success = Integer.parseInt(isSussess);
        if (success == 1) {
            return true;
        }
        return false;
    }

    private static String buildVerification(String phone) {
        String random=(int)((Math.random()*9+1)*100000)+"";
        System.out.println("phone 创建的验证码为："+random);
        return random;
    }
}
