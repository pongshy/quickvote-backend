package com.shu.votetool.model;

import lombok.Data;


@Data
public class Result<T> {
    /**
     * 标识码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 数据
     */
    private T data;
}
