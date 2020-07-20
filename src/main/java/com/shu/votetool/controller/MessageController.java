package com.shu.votetool.controller;

import com.shu.votetool.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: pongshy
 * @Date: 2020/7/20 10:38
 * @Description: 发送订阅消息
 **/
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/msg")
public class MessageController {
    @Resource
    private MessageService messageService;

    @Value("${wx.AppId}")
    private String AppId;

    @Value("${wx.secret}")
    private String secret;

    
    /*
     * @Description: 向未投票用户发送消息提醒
     * @Param: []
     * @Return: org.springframework.http.ResponseEntity<java.lang.Object>
     * @Author: pongshy
     * @createTime: 2020/7/20 10:54
     */
    @GetMapping("/send")
    public ResponseEntity<Object> sendMessage(@RequestHeader("accessToken") String access_token,
                                              @RequestHeader("openid") String openid,
                                              @RequestHeader("id") Integer id) {

        return messageService.sendMsg(access_token, openid, id);
    }


}
