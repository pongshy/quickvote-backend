package com.shu.votetool.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.shu.votetool.dao.UserDOMapper;
import com.shu.votetool.model.entity.UserDO;
import com.shu.votetool.model.response.ErrorResult;
import com.shu.votetool.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/*
  * @Description: 登录接口实现
  * @Author: pongshy
  * @Date: 2020/7/15
 **/
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Value("${wx.AppId}")
    private String AppId;

    @Value("${wx.secret}")
    private String secret;

    private String grant_type = "authorization_code";

    @Resource
    private UserDOMapper userDOMapper;

    /*
      * @Description:
      * @Return: org.springframework.http.ResponseEntity
      * @Author: pongshy
      * @Date: 2020/7/15
     **/
    @Override
    public ResponseEntity<Object> loginWX(String code) throws Exception {
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + AppId +
                    "&secret=" + secret +
                    "&js_code=" + code +
                    "&grant_type=authorization_code";
            log.info("code:" + code);
            log.info(url);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //请求
            HttpGet httpGet = new HttpGet(url);

            //Get请求内容
            CloseableHttpResponse response = httpClient.execute(httpGet);
            log.info(response.toString());

            HttpEntity httpEntity = response.getEntity();
            JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(httpEntity));
            log.info(jsonObject.toJSONString());
            String errcode = jsonObject.getString("errcode");
            log.info("errcode = " + errcode);
            if (errcode == null) {
                String openid = jsonObject.getString("openid");
                log.info(openid);
                UserDO userDO = userDOMapper.selectByPrimaryKey(openid);
                if (userDO == null) {
                    UserDO record = new UserDO();

                    record.setOpenid(openid);
                    userDOMapper.insertSelective(record);
                }
                return new ResponseEntity<Object>(openid, HttpStatus.OK);
            } else if (errcode.equals("-1")) {
                throw new Exception("系统繁忙，此时请开发者稍候再试");
            } else if (errcode.equals("40029")) {
                throw new Exception("code无效");
            } else if (errcode.equals("45011")) {
                throw new Exception("频率限制，每个用户每分钟100次");
            } else {
                throw new Exception("code has been used");
            }

        }
        catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<Object>(new ErrorResult(400,
                    HttpStatus.BAD_REQUEST,
                    e.getMessage(),
                    "/login"
            ),
                    HttpStatus.BAD_REQUEST);
        }
    }
}
