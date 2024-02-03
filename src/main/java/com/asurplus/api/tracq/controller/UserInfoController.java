package com.asurplus.api.tracq.controller;

import com.asurplus.api.tracq.service.UserInfoService;
import com.asurplus.api.tracq.vo.UserInfoVo;
import com.asurplus.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "2用户相关接口")
@RestController("/api/userInfo")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    @PostMapping("login")
    @ApiOperation(value = "保存用户信息接口")
    public ResponseResult login(UserInfoVo userInfoVo) {

        if (StringUtils.isBlank(userInfoVo.getUuinOpenid())){
            return ResponseResult.error("参数openid不能为空！");
        }
        return userInfoService.login(userInfoVo);
    }

}
