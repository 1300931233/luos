package com.asurplus.api.common.controller;

import com.asurplus.api.common.service.CommonApiService;
import com.asurplus.api.common.vo.CommonDicInfoVo;
import com.asurplus.common.utils.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 通用接口
 **/
//@Api(tags = "通用接口")
@RestController
@RequestMapping("api/common")
public class CommonController {
    @Autowired
    private CommonApiService commonApiService;

    @ApiOperation(value = "获取字典数据")
    @ApiImplicitParams({@ApiImplicitParam(name = "dictCode", value = "字典编码", required = true)})
    @PostMapping("listDictData")
    public ResponseResult<List<CommonDicInfoVo>> listDictData(String dictCode) {
        if (StringUtils.isBlank(dictCode)) {
            return ResponseResult.error("请输入必要参数");
        }
        return commonApiService.getDictionariesList(dictCode);
    }
}
