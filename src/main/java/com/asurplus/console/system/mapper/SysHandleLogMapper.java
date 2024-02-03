package com.asurplus.console.system.mapper;

import com.asurplus.console.system.entity.SysHandleLog;
import com.asurplus.console.system.vo.SysHandleLogVO;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper 接口
 */
public interface SysHandleLogMapper extends BaseMapper<SysHandleLog> {

    /**
     * 分页查询
     */
    IPage<SysHandleLogVO> listSysHandleLogVO(Page<SysHandleLogVO> page, @Param(Constants.WRAPPER) Wrapper<SysHandleLogVO> queryWrapper);
}
