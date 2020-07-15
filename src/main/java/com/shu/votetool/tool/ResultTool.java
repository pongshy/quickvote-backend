package com.shu.votetool.tool;


import com.shu.votetool.model.Result;

import java.util.List;

/*
  * @Description: 返回工具类
  * @Author: pongshy
  * @Date: 2020/7/15
 **/
public class ResultTool {

    @SuppressWarnings("unchecked")
    public static Result success(List<Object> object){
        Result result = new Result();
        result.setCode(200);
        result.setData(object);
        return result;
    }


    @SuppressWarnings("unchecked")
    public static Result success(Object object){
        Result result = new Result();
        result.setCode(200);
        result.setData(object);
        return result;
    }


    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        return result;
    }


    public static Result error(Integer code, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }


    public static Result error(Integer code){
        Result result = new Result();
        result.setCode(code);
        return result;
    }
}
