package com.asurplus.console.system.mapper;

import com.asurplus.console.system.entity.SysDictDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper 接口
 */
public interface SysDictDetailMapper extends BaseMapper<SysDictDetail> {

    /**
     * 根据字典编码，和字典值查询字典数据
     */
    String getSysDictDetail(@Param("dictCode") String dictCode, @Param("code") String code);

    /**
     * 根据字典编码查询字典值
     */
    List<SysDictDetail> queryDictItemsByCode(@Param("dictCode") String dictCode);

    /**
     * 获取数据字典，在系统启动时
     */
    List<SysDictDetail> listSysDictDetailOnSystemStart();
}
