package com.shu.votetool.service;

import org.springframework.http.ResponseEntity;

/**
 * @Author: pongshy
 * @Date: 2020/7/20 10:53
 * @Description: 微信小程序提醒消息发送接口
 **/
public interface MessageService {
    /*
     * @Description: 向未投票用户发送提醒消息
     * @Param: [String, String, String, Integer]
     * @Return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: pongshy
     * @createTime: 2020/7/20 12:41
     */
    public ResponseEntity<Object> sendMsg(String access_token, String openid, Integer id);
}
