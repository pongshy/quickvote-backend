package com.shu.votetool.tool;

import com.alibaba.fastjson.JSONObject;
import com.shu.votetool.exception.AllException;
import com.shu.votetool.exception.EmAllException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Author: pongshy
 * @Date: 2020/7/19 16:53
 * @Description: 调用微信小程序Api
 **/
@Slf4j
public class WXApiTool {

    /*
      * @Description: 获取后端接口调用凭据(access_token)
      * @Author: pongshy
      * @Date: 2020/7/16
     **/
    public static String getAccessToken(String AppId, String secret) throws AllException, IOException {
            String url = "https://api.weixin.qq.com/cgi-bin/token?" +
                    "grant_type=client_credential" +
                    "&appid=" + AppId +
                    "&secret=" + secret;
            log.info(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //请求
            HttpGet httpGet = new HttpGet(url);
            //Get请求内容
            CloseableHttpResponse response = httpClient.execute(httpGet);

            HttpEntity httpEntity = response.getEntity();
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(httpEntity));
            log.info(jsonObject.toString());
            String errcode = jsonObject.getString("errcode");
            if (StringUtils.isEmpty(errcode)) {
                String access_token = jsonObject.getString("access_token");

                return access_token;
            } else if (errcode.equals("-1")) {
                throw new AllException(EmAllException.INTERNAL_ERROR, "系统繁忙，此时请开发者稍候再试");
            } else if (errcode.equals("40001")) {
                throw new AllException(EmAllException.BAD_REQUEST, "AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性");
            } else if (errcode.equals("40002")) {
                throw new AllException(EmAllException.INTERNAL_ERROR, "请确保 grant_type 字段值为 client_credential");
            } else if (errcode.equals("40013")) {
                throw new AllException(EmAllException.BAD_REQUEST, "不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写");
            } else if (errcode.equals("40125")) {
                throw new AllException(EmAllException.BAD_REQUEST, "invalid appsecret");
            } else {
                throw new AllException(EmAllException.BAD_REQUEST, "文档中未标明错误");
            }
    }

}
