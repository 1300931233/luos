package com.asurplus.common.utils;

import com.asurplus.common.enums.BaseEnums;
import com.asurplus.common.enums.StatusEnums;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 单例模式返回接口相应数据
 */
@Data
public class ResponseResult<T> implements Serializable {

    @ApiModelProperty(value = "执行是否成功,true:成功,false:失败", required = true)
    private boolean success;//是否成功

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "提示信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public  ResponseResult () {

    }


    private static <T> ResponseResult<T>  resultData(boolean success,Integer code, String msg, T data) {
        ResponseResult<T> res = new  ResponseResult<T> ();
        res.setSuccess(success);
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    /**
     * 成功
     */
    public static  <T> ResponseResult  <T> success() {
        return resultData(true,StatusEnums.OK.getCode(), StatusEnums.OK.getMsg(), null);
    }

    /**
     * 成功
     */
    public static  <T> ResponseResult  <T> success(String msg) {
        return resultData(true,StatusEnums.OK.getCode(), msg, null);
    }

    /**
     * 成功
     */
    public static <T> ResponseResult <T> success(T data) {
        return resultData(true,StatusEnums.OK.getCode(), StatusEnums.OK.getMsg(), data);
    }

    /**
     * 成功
     */
    public static <T> ResponseResult <T> success(String msg, T data) {
        return resultData(true,StatusEnums.OK.getCode(), msg, data);
    }

    /**
     * 失败
     */
    public static <T> ResponseResult <T> error() {
        return resultData(false,StatusEnums.ERROR.getCode(), StatusEnums.ERROR.getMsg(), null);
    }

    /**
     * 失败
     */
    public static <T> ResponseResult <T> error(Integer code) {
        return resultData(false,code, null, null);
    }

    /**
     * 失败
     */
    public static <T> ResponseResult <T> error(Integer code, String msg) {
        return resultData(false,code, msg, null);
    }

    /**
     * 失败
     */
    public static <T> ResponseResult <T> error(String msg) {
        return resultData(false,StatusEnums.ERROR.getCode(), msg, null);
    }

    /**
     * 失败
     */
    public static <T> ResponseResult <T> error(T data) {
        return resultData(false,StatusEnums.ERROR.getCode(), StatusEnums.ERROR.getMsg(), data);
    }

    /**
     * 失败
     */
    public static <T> ResponseResult <T> error(Integer code, String msg, T data) {
        return resultData(false,code, msg, data);
    }

    /**
     * 失败
     */
    public static <T> ResponseResult <T> error(BaseEnums enums) {
        return resultData(false,enums.getCode(), enums.getMsg(), null);
    }

    /**
     * 请求参数验证
     * @param statusEnums
     */
    public  ResponseResult (StatusEnums statusEnums){
        this.code = statusEnums.getCode();
        this.msg = statusEnums.getMsg();
    }

    /**
     * 请求参数验证
     * @param code
     * @param msg
     */
    public  ResponseResult (Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
