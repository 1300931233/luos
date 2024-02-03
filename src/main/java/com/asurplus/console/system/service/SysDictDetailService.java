package com.asurplus.console.system.service;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysDictDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  服务类
 */
public interface SysDictDetailService extends IService<SysDictDetail> {

    /**
     * 分页查询
     */
    LayuiTableResult list(String dictCode);

    /**
     * 新增
     */
    ResponseResult add(SysDictDetail sysDictDetail);

    /**
     * 编辑
     */
    ResponseResult update(SysDictDetail sysDictDetail);

    /**
     * 删除
     */
    ResponseResult delete(Long id);

    /**
     * 根据字典编码，和字典值查询字典数据
     */
    String getDictDataByTypeAndValue(String dictCode, String code);

    /**
     * 根据字典编码查询字典配置信息
     * 提供给下拉框使用
     */
    List<SysDictDetail> listSysDictDetailByDictCode(String dictCode);

    /**
     * 根据字典编码查询字典配置信息
     * 提供给下拉框使用
     * @param dictCode 字典编码
     * @param code  不查询的编码状态
     * @return
     */
    List<SysDictDetail> listSysDictDetailByDictCodeNeCode(String dictCode,String code);
}
