package com.asurplus.console.system.service.impl;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.redis.RedisUtil;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.pojo.RedisPojo;
import com.asurplus.console.system.service.SysRedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName SysRedisServiceImpl
 * @Description
 **/
@Service
public class SysRedisServiceImpl implements SysRedisService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public LayuiTableResult list(String perfix) {
        Set<String> data = redisUtil.getKeys((StringUtils.isBlank(perfix) ? "" : perfix) + "*");
        List<RedisPojo> list = new ArrayList<>();
        if (null != data && !data.isEmpty()) {
            RedisPojo redisPojo = null;
            for (String item : data) {
                redisPojo = new RedisPojo();
                redisPojo.setKey(item);
                String value = String.valueOf(redisUtil.get(item));
                Map<Object,Object> map = new HashMap<Object,Object>();
                //判断是否是has值（has值的value获取方式不同）
                if(value.equals("null")){
                    map = redisUtil.hmget(item);
                    int i = 0;
                    //遍历has值并且拼接成指定的数据格式
                   for (Map.Entry<Object, Object> Entry : map.entrySet()) {
                        if(i != 0){
                            value += " , "+Entry.getKey()+" : "+Entry.getValue();
                        }else {
                            value = Entry.getKey()+" : "+Entry.getValue().toString();
                        }
                        i++;
                   }
                }
                redisPojo.setValue(value);
                list.add(redisPojo);
            }
        }
        return new LayuiTableResult<>(Long.parseLong("" + list.size()), list);
    }

    @Override
    public ResponseResult add(RedisPojo redisPojo) {
        if (StringUtils.isBlank(redisPojo.getKey())) {
            return ResponseResult.error("请输入键（key）");
        }
        if (null == redisPojo.getValue()) {
            return ResponseResult.error("请输入值（value）");
        }
        redisUtil.set(redisPojo.getKey(), redisPojo.getValue());
        if (null != redisPojo.getExpire() && -1 != redisPojo.getExpire()) {
            redisUtil.setExpire(redisPojo.getKey(), redisPojo.getExpire());
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(String key) {
        if (StringUtils.isBlank(key)) {
            return ResponseResult.error("请输入键（key）");
        }
        String[] keyArr = key.split(",");
        List<String> list = Arrays.asList(keyArr);
        redisUtil.delBatch(list);
        return ResponseResult.success();
    }
}
