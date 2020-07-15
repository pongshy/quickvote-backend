package com.shu.votetool.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/*
  * @Description: 登录接口
  * @Author: pongshy
  * @Date: 2020/7/15
 **/
public interface LoginService {
    public ResponseEntity<Object> loginWX(String code) throws Exception;
}
