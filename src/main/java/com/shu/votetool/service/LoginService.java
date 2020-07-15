package com.shu.votetool.service;

import com.shu.votetool.model.request.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/*
  * @Description: 登录接口
  * @Author: pongshy
  * @Date: 2020/7/15
 **/
public interface LoginService {
    /*
      * @Description: 登录实现接口
      * @Param: [String]
      * @Return: org.springframework.http.ResponseEntity
      * @Author: pongshy
      * @Date: 2020/7/16
     **/
    public ResponseEntity<Object> loginWX(String code) throws Exception;

    /*
      * @Description: 用户信息插入接口
      * @Param: [String, UserInfo]
      * @Return: org.springframework.http.ResponseEntity
      * @Author: pongshy
      * @Date: 2020/7/16
     **/
    public ResponseEntity<Object> updateUserInfo(String openid, UserInfo userInfo) throws Exception;
}
