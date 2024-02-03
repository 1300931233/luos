package com.luos.console.system.controller;

import com.luos.common.layui.LayuiTableResult;
import com.luos.common.log.OperationLogAnnotate;
import com.luos.common.redis.RedisUtil;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.pojo.RedisPojo;
import com.luos.console.system.service.SysRedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Redis监控 前端控制器-21
 */
@Api(tags = "后台管理端--Redis监控")
@Controller
@RequestMapping("/sys/sys-redis")
public class SysRedisController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SysRedisService sysRedisService;

    @ApiOperation(value = "请求Redis监控管理列表页")
    @GetMapping("init")
    public String init() {
        return "system/redis/list";
    }

    @ApiOperation(value = "请求Redis监控新增页")
    @GetMapping("add")
    public String add() {
        return "system/redis/add";
    }

    @ApiOperation(value = "请求Redis监控修改页")
    @GetMapping("update/{key}")
    public String update(@PathVariable String key, Model model) {
        RedisPojo redisPojo = new RedisPojo();
        redisPojo.setKey(key);
        redisPojo.setValue(String.valueOf(redisUtil.get(key)));
        redisPojo.setExpire(redisUtil.getExpire(key));
        model.addAttribute("data", redisPojo);
        return "system/redis/update";
    }

    @ApiOperation(value = "分页查询Redis监控管理数据")
    @ApiImplicitParam(name = "perfix", value = "key的前缀")
    @GetMapping("list")
    @ResponseBody
    public LayuiTableResult list(String perfix) {
        return sysRedisService.list(perfix);
    }

    @ApiOperation(value = "新增redis数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键", required = true),
            @ApiImplicitParam(name = "value", value = "值", required = true),
            @ApiImplicitParam(name = "expire", value = "过期时间（-1为永久）")
    })
    @PostMapping("add")
    @ResponseBody
    @OperationLogAnnotate(shloModem="新增redis数据",shloRequest="新增redis数据")
    public ResponseResult add(@RequestBody RedisPojo redisPojo) {
        return sysRedisService.add(redisPojo);
    }

    @ApiOperation(value = "删除redis数据")
    @ApiImplicitParam(name = "key", value = "键（key）", required = true)
    @GetMapping("delete/{key}")
    @ResponseBody
    @OperationLogAnnotate(shloModem="删除redis数据",shloRequest="删除redis数据")
    public ResponseResult delete(@PathVariable String key) {
        return sysRedisService.delete(key);
    }
}
