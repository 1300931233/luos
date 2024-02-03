package com.luos.console.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luos.common.layui.LayuiTreePojo;
import com.luos.console.system.entity.SysPermissionInfo;
import com.luos.console.system.pojo.MenuPojo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单权限表 Mapper 接口
 */
public interface SysPermissionInfoMapper extends BaseMapper<SysPermissionInfo> {

    /**
     * 根据用户id，查询用户的菜单权限信息
     */
    List<MenuPojo> listPermissionInfoByUserId(@Param("id") Integer id);

    /**
     * 查询所有有效的菜单权限信息
     */
    List<MenuPojo> listPermissionInfo();

    /**
     * 角色赋权，tree组件
     */
    List<LayuiTreePojo> listPermissionForTree(@Param("id") Integer id);

    /**
     * 批量删除
     */
    void deleteBatch(@Param("ids") String ids);

    /**
     * 批量修改数据状态
     */
    void updateStatusBatch(@Param("ids") String ids, @Param("status") Integer status);

    /**
     * 获取子集的ids
     */
    String getChilderIds(@Param("pid") String pid);
}
