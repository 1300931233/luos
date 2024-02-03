package com.luos.console.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luos.console.system.entity.SysManagerRole;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * 用户-角色关系表 Mapper 接口
 */
public interface SysManageRoleMapper extends BaseMapper<SysManagerRole> {

    /**
     * 根据用户id，查询多个角色标识
     */
    Set<String> listUserRoleByUserId(@Param("userId") Integer userId);
}
