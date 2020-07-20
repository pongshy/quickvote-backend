package com.shu.votetool.exception;

import org.springframework.http.HttpStatus;

/**
 * @description: 报错枚举
 * @author: 0GGmr0
 * @create: 2019-12-01 21:27
 */
public enum EmAllException implements CommonError {
    BAD_REQUEST(400, "请求参数格式有误", HttpStatus.BAD_REQUEST),

    IDENTITY_ERROR(401, "未经权限", HttpStatus.UNAUTHORIZED),

    DATABASE_ERROR(500, "数据库异常或数据有误", HttpStatus.INTERNAL_SERVER_ERROR),

    ErrorCode(400, " wx.login接口返回", HttpStatus.BAD_REQUEST),

    INTERNAL_ERROR(500, "程序内部错误", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_REFUSE(503, "服务器无法处理请求", HttpStatus.SERVICE_UNAVAILABLE);




//    NO_LOGIN_AUTHORIZATION(403, "没有登录权限"),
//
//    LOGIN_VERIFICATION_ERROR(500, "上海大学登录接口出现异常"),
//
//    TOKEN_PHASE_ERROR(500, "解析token出错"),
//
//    ENCODE_ERROR(500, "token编码错误"),
//
//    BAD_FILE_TYPE(400, "文件格式错误"),
//
//    UNIFY_ERROR(400, "请保证Excel表格格式无误"),
//
//    USER_AND_PASSWORD_BLANK_ERROR(400, "账号或密码不能为空"),
//
//    QUERY_TIME_OUT(500, "请求超时，请确定您是否使用校园网登录"),
//
//    INSERT_ERROR(500, "插入数据失败"),
//
//    USER_ALREADY_EXISTS(410, "用户已存在"),
//
//    DATABASE_ERROR(500, "数据库异常或数据有误");

    // 错误码
    private Integer code;

    // 错误信息
    private String msg;

    private HttpStatus httpStatus;

    EmAllException(Integer code, String msg, HttpStatus httpStatus) {
        this.code = code;
        this.msg = msg;
        this.httpStatus = httpStatus;
    }

    @Override
    public Integer getErrCode() {
        return this.code;
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.msg = errMsg;
        return this;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
