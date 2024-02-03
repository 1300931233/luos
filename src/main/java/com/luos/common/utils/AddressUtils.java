package com.luos.common.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * 获取地址工具类
 **/
public class AddressUtils {

    /**
     * 根据IP地址获取地理位置
     */
    public static String getAddressByIP(String ip) {
        if (StringUtils.isBlank(ip)) {
            return "";
        }
        if ("127.0.0.1".equals(ip)) {
            return "局域网，无法获取位置";
        }
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6006&format=json&query=" + ip;
        HttpResponse res = HttpRequest.get(url).execute();
        if (200 != res.getStatus()) {
            return "获取位置失败";
        } else {
            JSONObject resJson = JSONObject.parseObject(res.body());
            JSONArray resArr = JSONArray.parseArray(resJson.getString("data"));
            if (0 == resArr.size()) {
                return "获取位置失败";
            }
            resJson = JSONObject.parseObject("" + resArr.get(0));
            return resJson.getString("location");
        }
    }
}
