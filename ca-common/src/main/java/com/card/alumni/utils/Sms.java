package com.card.alumni.utils;

import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Sms {
    public static void main(String[] args) {
        //短信下发
        String sendUrl = "http://smssh1.253.com/msg/send/json";
        Map map = new HashMap();
        //API账号
        map.put("account","N8396454");
        //API密码
        map.put("password","yvl0gmA4j");
        //短信内容
        map.put("msg","【永鑫科技】呜呜呜呜呜呜:" + "123456");
        //手机号
        map.put("phone","13030066787");
        //是否需要状态报告
        map.put("report","true");
        //自定义扩展码
        map.put("extend","123");
        JSONObject js = (JSONObject) JSONObject.toJSON(map);
        System.out.println(sendSmsByPost(sendUrl,js.toString()));
        //查询余额
        String balanceUrl = "http://smssh1.253.com/msg/balance/json";
        Map map1 = new HashMap();
        map1.put("account","N8396454");
        map1.put("password","yvl0gmA4j");
        JSONObject js1 = (JSONObject) JSONObject.toJSON(map1);
        System.out.println(sendSmsByPost(balanceUrl,js1.toString()));
    }

    public static String sendSmsByPost(String path, String postContent) {
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.connect();
            OutputStream os=httpURLConnection.getOutputStream();
            os.write(postContent.getBytes("UTF-8"));
            os.flush();
            StringBuilder sb = new StringBuilder();
            int httpRspCode = httpURLConnection.getResponseCode();
            if (httpRspCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                br.close();
                return sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}