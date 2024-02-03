package com.asurplus.console.system.mapper;

import com.asurplus.console.system.entity.SysRole;
import com.asurplus.console.system.pojo.XmSelectPojo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 角色信息表 Mapper 接口
 */
public interface SysRoleInfoMapper extends BaseMapper<SysRole> {

    /**
     * 根据用户id查询角色，给xmselect赋值
     */
    List<XmSelectPojo> listXmSelectPojo(@Param("userId") Integer userId);
}
