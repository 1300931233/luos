package com.luos.api.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.luos.api.common.service.CommonApiService;
import com.luos.api.common.vo.CommonDicInfoVo;
import com.luos.common.utils.BeanUtil;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysDictDetail;
import com.luos.console.system.service.SysDictDetailService;
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
