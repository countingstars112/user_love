package com.xxwy.redisping.tools;

import com.xxwy.redisping.tools.bean.ResultData;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * * From  xxwy
 */
public class ResultUtil {

    public static ResultData success(){
        return new ResultData("success","100");
    }

    public static ResultData success(Object object){
        ResultData success = success();
        success.setData(object);
        return success;
    }

    public static ResultData error(String msg){
        return new ResultData(msg ,"200");
    }

    public static ResultData error(String msg , Object object){
        ResultData error = error(msg);
        error.setData(object);
        return error;
    }

    public static ResultData error(BindingResult result) {
        Map<String, String> map = new HashMap<>();
        for (FieldError error : result.getFieldErrors()) {
            map.put(error.getField(), error.getDefaultMessage());
        }
        return error("传入的值错误", map);
    }
}

