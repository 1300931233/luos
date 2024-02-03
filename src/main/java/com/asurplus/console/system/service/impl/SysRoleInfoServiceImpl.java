package com.asurplus.console.system.service.impl;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.shiro.ShiroUtils;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysRole;
import com.asurplus.console.system.mapper.SysRoleInfoMapper;
import com.asurplus.console.system.service.SysRoleInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 角色信息表 服务实现类
 */
@Service
public class SysRoleInfoServiceImpl extends ServiceImpl<SysRoleInfoMapper, SysRole> implements SysRoleInfoService {

    @Override
    public LayuiTableResult list(Integer page, Integer limit, SysRole sysRole) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(sysRole.getSrolName())) {
            queryWrapper.like(SysRole::getSrolName, sysRole.getSrolName());
        }
        if (StringUtils.isNotBlank(sysRole.getSrolSign())) {
            queryWrapper.like(SysRole::getSrolSign, sysRole.getSrolSign());
        }
        if (null != sysRole.getSrolStatus()) {
            queryWrapper.eq(SysRole::getSrolStatus, sysRole.getSrolStatus());
        }
        queryWrapper.orderByDesc(SysRole::getSrolCreateTime);
        return new LayuiTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(SysRole sysRole) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRole::getSrolName, sysRole.getSrolName()).or().eq(SysRole::getSrolSign, sysRole.getSrolSign());
        List<SysRole> sysRoleList = this.list(queryWrapper);
        if (null != sysRoleList && !sysRoleList.isEmpty()) {
            return ResponseResult.error("该角色名称或标识已经存在，请检查后提交");
        }
        sysRole.setSrolCreateUserId(ShiroUtils.getSysUserId());
        sysRole.setSrolCreateTime(new Date());
        this.save(sysRole);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysRole sysRole) {
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysRole::getSrolId, sysRole.getSrolId());
        queryWrapper.and(i -> i.eq(SysRole::getSrolName, sysRole.getSrolName()).or().eq(SysRole::getSrolSign, sysRole.getSrolSign()));
        List<SysRole> sysRoleList = this.list(queryWrapper);
        if (null != sysRoleList && !sysRoleList.isEmpty()) {
            return ResponseResult.error("该角色名称或标识已经存在，请检查后提交");
        }
        sysRole.setSrolUpdateUser(ShiroUtils.getSysUserId());
        this.updateById(sysRole);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Integer id) {
        this.removeById(id);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Integer id, Integer status) {
        SysRole sysRole = this.getById(id);
        sysRole.setSrolStatus(status);
        this.updateById(sysRole);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult listXmSelectPojo(Integer userId) {
        return ResponseResult.success(this.baseMapper.listXmSelectPojo(userId));
    }
}
