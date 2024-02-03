package com.asurplus.common.layui;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * layui 文件上传返回类
 */
@Data
public class LayuiUploadResult {

    private Integer code;
    private String msg;
    private Object data;

    private static LayuiUploadResult resultData(Integer code, String msg, Object data) {
        LayuiUploadResult res = new LayuiUploadResult();
        res.setCode(code);
        res.setMsg(msg);
        res.setData(data);
        return res;
    }

    public static LayuiUploadResult success(Object data) {
        return resultData(0, "上传成功", data);
    }

    public static LayuiUploadResult success(String data) {
        JSONObject res = new JSONObject();
        res.put("src", data);
        return resultData(0, "上传成功", res);
    }

    public static LayuiUploadResult success(String msg, Object data) {
        JSONObject res = new JSONObject();
        res.put("src", data);
        return resultData(0, msg, res);
    }

    public static LayuiUploadResult error(String msg) {
        return resultData(1, msg, null);
    }
}
