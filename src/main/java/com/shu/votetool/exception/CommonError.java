package com.shu.votetool.exception;

import org.springframework.http.HttpStatus;

/**
 * @description: 报错接口
 * @author: 0GGmr0
 * @create: 2019-12-01 21:25
 */
public interface CommonError {
    Integer getErrCode();

    String getMsg();

    HttpStatus getHttpStatus();

    CommonError setErrMsg(String errMsg);
}
