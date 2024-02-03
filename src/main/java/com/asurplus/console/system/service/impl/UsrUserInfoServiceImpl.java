package com.asurplus.console.system.service.impl;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.console.system.entity.UsrUserInfo;
import com.asurplus.console.system.mapper.UsrUserInfoMapper;
import com.asurplus.console.system.service.UsrUserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
@Service
public class UsrUserInfoServiceImpl extends ServiceImpl<UsrUserInfoMapper, UsrUserInfo> implements UsrUserInfoService {

    @Override
    public LayuiTableResult list(Integer page, Integer limit, UsrUserInfo userInfo) {
        LambdaQueryWrapper<UsrUserInfo> queryWrapper = new LambdaQueryWrapper<>();
        //姓名
        queryWrapper.like(StringUtils.isNotBlank(userInfo.getUuinName()),UsrUserInfo::getUuinName,userInfo.getUuinName());
        //电话
        queryWrapper.like(StringUtils.isNotBlank(userInfo.getUuinPhone()),UsrUserInfo::getUuinPhone,userInfo.getUuinPhone());

        queryWrapper.orderByDesc(UsrUserInfo::getUuinCreateTime);
        return new LayuiTableResult(this.page(new Page<>(page,limit),queryWrapper));
    }
}
