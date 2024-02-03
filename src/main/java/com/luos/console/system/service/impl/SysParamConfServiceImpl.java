package com.luos.console.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luos.common.redis.RedisConst;
import com.luos.common.redis.RedisUtil;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysParamConf;
import com.luos.console.system.mapper.SysParamConfMapper;
import com.luos.console.system.service.SysParamConfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统参数设置 服务实现类
 */
@Service
public class SysParamConfServiceImpl extends ServiceImpl<SysParamConfMapper, SysParamConf> implements SysParamConfService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysParamConf saveGetSysParamConf() {
        Object object = redisUtil.get(RedisConst.Key.SYS_PARAM_CONF);
        if (null != object) {
            return (SysParamConf) object;
        }
        SysParamConf sysParamConf = this.getOne(null, false);
        if (null == sysParamConf) {
            sysParamConf = new SysParamConf();
            this.baseMapper.insert(sysParamConf);
        }
        // 存入redis
        redisUtil.set(RedisConst.Key.SYS_PARAM_CONF, sysParamConf);
        return sysParamConf;
    }

    @Override
    public ResponseResult updateSysParamConf(SysParamConf sysParam) {
        if (null == sysParam || null == sysParam.getSpcoId() || 0 == sysParam.getSpcoId()) {
            return ResponseResult.error("数据错误");
        }
        this.baseMapper.updateById(sysParam);
        // 存入redis
        redisUtil.set(RedisConst.Key.SYS_PARAM_CONF, sysParam);
        return ResponseResult.success();

    }
}
