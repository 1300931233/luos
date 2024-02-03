package com.asurplus.console.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.asurplus.common.redis.RedisConst;
import com.asurplus.common.redis.RedisUtil;
import com.asurplus.common.utils.DateUtils;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.ServerInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前端控制器
 **/
@Api(tags = "后台管理端--服务器监控")
@Controller
@RequestMapping("/sys/sys-server-info")
public class SysServerInfoController {

    @Autowired
    private RedisUtil redisUtil;

    @ApiOperation(value = "请求服务器监控列表页")
    @GetMapping("init")
    public String init() {
        return "system/serverinfo/info";
    }

    @ApiOperation(value = "查看服务器监控信息")
    @GetMapping("list")
    @ResponseBody
    public ResponseResult list() {
        Object object = redisUtil.get(RedisConst.Key.SYS_SERVER_INFO);
        if (null != object) {
            return ResponseResult.success(JSONObject.parseObject(String.valueOf(object)));
        }
        ServerInfo serverInfo = new ServerInfo();
        try {
            serverInfo.copyTo();
            // 放在redis里面，60秒过期时间
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("data", serverInfo);
            jsonObject.put("updateTime", DateUtils.getYmdHmsZh());
            redisUtil.set(RedisConst.Key.SYS_SERVER_INFO, jsonObject.toJSONString(), 120);
            return ResponseResult.success(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.error("获取服务信息失败");
    }
}
