package com.asurplus.api.common.service;

import com.asurplus.api.common.vo.CommonDicInfoVo;
import com.asurplus.common.utils.ResponseResult;

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
