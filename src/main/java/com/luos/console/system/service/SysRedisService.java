package com.luos.console.system.service;


import com.luos.common.layui.LayuiTableResult;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.pojo.RedisPojo;

/**
 * @ClassName SysRedisService
 * @Description
 **/
public interface SysRedisService {

    /**
     * 查询所有key
     */
    LayuiTableResult list(String perfix);

    /**
     * 新增 key
     * 修改 key
     */
    ResponseResult add(RedisPojo redisPojo);

    /**
     * 根据 key 删除数据
     */
    ResponseResult delete(String key);
}
