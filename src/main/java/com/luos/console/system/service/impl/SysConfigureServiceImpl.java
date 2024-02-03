package com.luos.console.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luos.common.redis.RedisConst;
import com.luos.common.redis.RedisUtil;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysConfigure;
import com.luos.console.system.mapper.SysConfigureMapper;
import com.luos.console.system.service.SysConfigureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统设置 服务实现类
 */
@Service
public class SysConfigureServiceImpl extends ServiceImpl<SysConfigureMapper, SysConfigure> implements SysConfigureService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public SysConfigure saveSysConfigure() {
        Object object = redisUtil.get(RedisConst.Key.SYS_CONFIGURE);
        if (null != object) {
            return (SysConfigure) object;
        }
        SysConfigure sysConfigure = this.getOne(null, false);
        if (null == sysConfigure) {
            sysConfigure = new SysConfigure();
            this.baseMapper.insert(sysConfigure);
        }
        // 存入redis
        redisUtil.set(RedisConst.Key.SYS_CONFIGURE, sysConfigure);
        return sysConfigure;
    }

    @Override
    public ResponseResult update(SysConfigure sysConfigure) {
        if (null == sysConfigure || null == sysConfigure.getSconId() || 0 == sysConfigure.getSconId()) {
            return ResponseResult.error("数据错误");
        }
        this.baseMapper.updateById(sysConfigure);
        // 存入redis
        redisUtil.set(RedisConst.Key.SYS_CONFIGURE, sysConfigure);
        return ResponseResult.success();
    }
}
