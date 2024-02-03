package com.luos.api.common.service;

import com.luos.api.common.vo.CommonDicInfoVo;
import com.luos.common.utils.ResponseResult;

import java.util.List;

/**
 * 通用内部接口
 */
public interface CommonApiService {

    /**
     * 根据code获取字典中对应的类型
     * @param dicCode
     * @return
     */
    ResponseResult<List<CommonDicInfoVo>> getDictionariesList(String dicCode);
}
