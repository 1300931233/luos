package com.asurplus.console.system.service.impl;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.shiro.ShiroUtils;
import com.asurplus.common.utils.PasswordUtils;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysManager;
import com.asurplus.console.system.entity.SysParamConf;
import com.asurplus.console.system.mapper.SysManagerMapper;
import com.asurplus.console.system.service.SysManagerService;
import com.asurplus.console.system.service.SysParamConfService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户信息表 服务实现类
 */
@Service
public class SysManagerServiceImpl extends ServiceImpl<SysManagerMapper, SysManager> implements SysManagerService {

    @Autowired
    private SysParamConfService sysParamConfService;

    @Override
    public LayuiTableResult list(Integer page, Integer limit, SysManager sysManager) {
        LambdaQueryWrapper<SysManager> queryWrapper = new LambdaQueryWrapper<>();

        //不查询超级管理员（id大于1）
        queryWrapper.gt(SysManager::getSmanId,1);
        if (StringUtils.isNotBlank(sysManager.getSmanName())) {
            queryWrapper.like(SysManager::getSmanName, sysManager.getSmanName());
        }
        if (StringUtils.isNotBlank(sysManager.getSmanAccount())) {
            queryWrapper.like(SysManager::getSmanAccount, sysManager.getSmanAccount());
        }
        if (StringUtils.isNotBlank(sysManager.getSmanPhone())) {
            queryWrapper.like(SysManager::getSmanPhone, sysManager.getSmanPhone());
        }
        if (null != sysManager.getSmanSex()) {
            queryWrapper.eq(SysManager::getSmanSex, sysManager.getSmanSex());
        }
        if (null != sysManager.getSmanStatus()) {
            queryWrapper.eq(SysManager::getSmanStatus, sysManager.getSmanStatus());
        }
        queryWrapper.orderByDesc(SysManager::getSmanCreateTime);
        return new LayuiTableResult<>(this.page(new Page<>(page, limit), queryWrapper));
    }

    @Override
    public ResponseResult add(SysManager sysManager) {
        LambdaQueryWrapper<SysManager> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysManager::getSmanAccount, sysManager.getSmanAccount());
        List<SysManager> userInfoList = this.list(queryWrapper);
        if (null != userInfoList && !userInfoList.isEmpty()) {
            return ResponseResult.error("该账户已经存在，请检查后提交");
        }
        SysParamConf sysParamConf = sysParamConfService.saveGetSysParamConf();
        String defaultPassword = "123456";
        if (null != sysParamConf && StringUtils.isNotBlank(sysParamConf.getSpcoDefaultPassword())) {
            defaultPassword = sysParamConf.getSpcoDefaultPassword();
        }
        String password = PasswordUtils.getPassword(defaultPassword, sysManager.getSmanAccount());
        sysManager.setSmanPassword(password);
        sysManager.setSmanCreateUserId(ShiroUtils.getSysUserId());
        sysManager.setSmanCreateTime(new Date());
        this.save(sysManager);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysManager sysManager) {
        sysManager.setSmanUpdateUserId(ShiroUtils.getSysUserId());
        this.updateById(sysManager);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult info(SysManager sysManager) {
        this.updateById(sysManager);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Long id) {
        SysManager sysManager = this.getById(id);
        //删除原始图片
        if (StringUtils.isNotBlank(sysManager.getSmanAvatar())) {
//            minioUtils.delFile(sysManager.getSmanAvatar());
        }
        this.removeById(id);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Long id, Integer SmenStatus) {
        SysManager userInfo = this.getById(id);
        userInfo.setSmanStatus(SmenStatus);
        this.updateById(userInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updatePassword(String oldPassword, String password) {
        if (oldPassword.equals(password)) {
            return ResponseResult.error("新密码不能与旧密码相同");
        }
        SysParamConf sysParamConf = sysParamConfService.saveGetSysParamConf();
        if (null != sysParamConf && StringUtils.isNotBlank(sysParamConf.getSpcoDefaultPassword())) {
            if (password.equals(sysParamConf.getSpcoDefaultPassword())) {
                return ResponseResult.error("为了您的账户安全，请尽量不要使用系统初始密码");
            }
        }
        // 获取登录用户
        SysManager sysManager = this.getById(ShiroUtils.getSysUserId());
        oldPassword = PasswordUtils.getPassword(oldPassword, sysManager.getSmanAccount());
        if (!oldPassword.equals(sysManager.getSmanPassword())) {
            return ResponseResult.error("旧密码不正确");
        }
        password = PasswordUtils.getPassword(password, sysManager.getSmanAccount());
        sysManager.setSmanPassword(password);
        this.updateById(sysManager);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updatePassword(Integer id, String password) {
        if (null == id || 0 == id) {
            return ResponseResult.error("请选择需要操作的用户");
        }
        if (StringUtils.isBlank(password)) {
            return ResponseResult.error("请输入密码");
        }
        SysManager sysManager = this.getById(id);
        if (null == sysManager) {
            return ResponseResult.error("用户信息错误");
        }
        sysManager.setSmanPassword(PasswordUtils.getPassword(password, sysManager.getSmanAccount()));
        this.updateById(sysManager);
        return ResponseResult.success();
    }
}
