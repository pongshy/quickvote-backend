package com.shu.votetool.model.response;

import com.shu.votetool.tool.TimeTool;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

/*
  * @Description: 错误返回类
  * @Author: pongshy
  * @Date: 2020/7/15
 **/
@Data
public class ErrorResult {
    private String timestamp;

    private Integer status;

    private HttpStatus error;

    private String message;

    private String path;

    public ErrorResult(Integer status,
                       HttpStatus error,
                       String message,
                       String path) {
        Date date = new Date(System.currentTimeMillis());

        this.timestamp = TimeTool.DateToString(date);
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResult(HttpStatus error,
                       String message,
                       String path
    ) {
        Date date = new Date(System.currentTimeMillis());

        this.timestamp = TimeTool.DateToString(date);
        this.status = error.value();
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
