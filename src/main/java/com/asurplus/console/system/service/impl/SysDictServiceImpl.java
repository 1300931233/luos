package com.asurplus.console.system.service.impl;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.redis.RedisConst;
import com.asurplus.common.redis.RedisUtil;
import com.asurplus.common.shiro.ShiroUtils;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysDict;
import com.asurplus.console.system.mapper.SysDictMapper;
import com.asurplus.console.system.service.SysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 服务实现类
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public LayuiTableResult list(Integer page, Integer limit, SysDict sysDict) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(sysDict.getSdicCode())) {
            queryWrapper.like(SysDict::getSdicCode, sysDict.getSdicCode());
        }
        if (StringUtils.isNotBlank(sysDict.getSdicName())) {
            queryWrapper.like(SysDict::getSdicName, sysDict.getSdicName());
        }
        if (null != sysDict.getSdicStatus()) {
            queryWrapper.eq(SysDict::getSdicStatus, sysDict.getSdicStatus());
        }
        queryWrapper.orderByDesc(SysDict::getSdicCreateTime);
        return new LayuiTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(SysDict sysDict) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDict::getSdicCode, sysDict.getSdicCode());
        List<SysDict> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该字典编号已经存在");
        }
        sysDict.setSdicCreateUserId(ShiroUtils.getSysUserId());
        sysDict.setSdicCreateTime(new Date());
        this.save(sysDict);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysDict sysDict) {
        LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysDict::getSdicId, sysDict.getSdicId());
        queryWrapper.eq(SysDict::getSdicCode, sysDict.getSdicCode());
        List<SysDict> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该字典编号已经存在");
        }
        this.updateById(sysDict);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Integer id) {
        SysDict sysDict = this.getById(id);
        this.removeById(id);
        Set<String> keys = redisUtil.getKeys(RedisConst.Key.SYS_DICT + sysDict.getSdicCode());
        redisUtil.delBatch(keys);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Integer id, Integer status) {
        SysDict sysDict = this.getById(id);
        sysDict.setSdicStatus(status);
        this.updateById(sysDict);
        return ResponseResult.success();
    }
}
