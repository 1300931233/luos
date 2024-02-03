package com.luos.console.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.luos.common.consts.SystemConst;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysManagerRole;
import com.luos.console.system.mapper.SysManageRoleMapper;
import com.luos.console.system.service.SysUserRoleService;
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
 * 用户-角色关系表 服务实现类
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysManageRoleMapper, SysManagerRole> implements SysUserRoleService {

    @Override
    public Set<String> listUserRoleByUserId(Integer userId) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object object = session.getAttribute(SystemConst.SYSTEM_USER_ROLE);
        if (null != object) {
            return (Set<String>) object;
        }
        Set<String> set = this.baseMapper.listUserRoleByUserId(userId);
        session.setAttribute(SystemConst.SYSTEM_USER_ROLE, set);
        return set;
    }

    @Override
    public ResponseResult auth(Integer id, String roles) {
        // 先删除
        LambdaQueryWrapper<SysManagerRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysManagerRole::getSmroManagerId, id);
        this.remove(queryWrapper);
        // 保存用户角色
        if (StringUtils.isNotBlank(roles)) {
            String[] roleArr = roles.split(",");
            List<SysManagerRole> list = new ArrayList<>();
            SysManagerRole sysManagerRole = null;
            for (String item : roleArr) {
                sysManagerRole = new SysManagerRole();
                sysManagerRole.setSmroManagerId(id);
                sysManagerRole.setSmroRoleId(Integer.parseInt(item));
                list.add(sysManagerRole);
            }
            this.saveBatch(list);
        }
        return ResponseResult.success();
    }
}
