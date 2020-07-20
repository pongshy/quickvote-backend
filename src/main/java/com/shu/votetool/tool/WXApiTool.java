package com.shu.votetool.tool;

import com.alibaba.fastjson.JSONObject;
import com.shu.votetool.exception.AllException;
import com.shu.votetool.exception.EmAllException;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

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
            log.info(errcode);
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

    /*
     * @Description: 调用微信小程序接口，向授权的未投票发送提醒消息
     * @Param: [String, String, String]
     * @Author: pongshy
     * @createTime: 2020/7/20 12:52
     */
    public static String sendMsgByApi(String access_token, String openid, String template_id, String page, Map<String, Map> data) throws AllException, IOException {
        StringBuilder url = new StringBuilder("https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=");
        url.append(access_token);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("touser", openid);
        jsonObject.put("template_id", template_id);
        jsonObject.put("page", page);
        jsonObject.put("data", data);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url.toString());

        StringEntity s = new StringEntity(jsonObject.toString(), "UTF-8");
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json; charset=UTF-8");
        httpPost.setEntity(s);

        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity httpEntity = response.getEntity();
        JSONObject json = JSONObject.parseObject(EntityUtils.toString(httpEntity));

        log.info(json.toString());
        String errcode = json.getString("errcode");
        if ("0".equals(errcode)) {
            return "success";
        } else if ("40003".equals(errcode)) {
            throw new AllException(EmAllException.BAD_REQUEST, "touser字段openid为空或者不正确");
        } else if ("40037".equals(errcode)) {
            throw new AllException(EmAllException.INTERNAL_ERROR, "订阅模板id为空不正确");
        } else if ("43101".equals(errcode)) {
            throw new AllException(EmAllException.USER_REFUSE, "用户拒绝接受消息");
        } else if ("47003".equals(errcode)) {
            throw new AllException(EmAllException.INTERNAL_ERROR, "模板参数不准确，可能为空或者不满足规");
        } else if ("41030".equals(errcode)) {
            throw new AllException(EmAllException.BAD_REQUEST, "page路径不正确，需要保证在现网版本小程序中存在，与app.json保持一致");
        } else {
            throw new AllException(EmAllException.USER_REFUSE, "文档未标记错误");
        }
    }

}
