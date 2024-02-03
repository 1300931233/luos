package com.asurplus.api.common.service.impl;

import com.asurplus.api.common.service.CommonApiService;
import com.asurplus.api.common.vo.CommonDicInfoVo;
import com.asurplus.common.utils.BeanUtil;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.console.system.entity.SysDictDetail;
import com.asurplus.console.system.service.SysDictDetailService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName CommonServiceImpl
 **/
@Service
public class CommonApiServiceImpl implements CommonApiService {

    @Autowired
    private SysDictDetailService sysDictDetailService;


    @Override
    public ResponseResult<List<CommonDicInfoVo>> getDictionariesList(String dicCode) {
        QueryWrapper<SysDictDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysDictDetail::getSddeCode, SysDictDetail::getSddeName);
        queryWrapper.lambda().eq(SysDictDetail::getSddeDictCode, dicCode);
        return ResponseResult.success(BeanUtil.CollectionCopy(this.sysDictDetailService.list(queryWrapper),CommonDicInfoVo.class));
    }
}
