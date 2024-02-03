package com.asurplus.console.system.service.impl;

import com.asurplus.common.consts.SystemConst;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysRolePermission;
import com.asurplus.console.system.mapper.SysRolePermissionMapper;
import com.asurplus.console.system.service.SysRolePermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 角色-权限关系表 服务实现类
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

    @Override
    public ResponseResult auth(Integer id, String perms) {
        // 先删除
        LambdaQueryWrapper<SysRolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRolePermission::getSrpeRoleId, id);
        remove(queryWrapper);
        // 保存角色权限
        if (StringUtils.isNotBlank(perms)) {
            String[] roleArr = perms.split(",");
            List<SysRolePermission> list = new ArrayList<>();
            SysRolePermission sysRolePermission = null;
            for (String item : roleArr) {
                sysRolePermission = new SysRolePermission();
                sysRolePermission.setSrpeRoleId(id);
                sysRolePermission.setSrpePermissionId(Integer.parseInt(item));
                list.add(sysRolePermission);
            }
            this.saveBatch(list);
        }
        return ResponseResult.success();
    }

    @Override
    public Set<String> listRolePermissionByUserId(Integer userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object object = session.getAttribute(SystemConst.SYSTEM_USER_PERMISSION);
        if (null != object) {
            return (Set<String>) object;
        }
        Set<String> set = this.baseMapper.listRolePermissionByUserId(userId);
        session.setAttribute(SystemConst.SYSTEM_USER_PERMISSION, set);
        return set;
    }
}
