package com.luos.api.tracq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.luos.api.tracq.service.UserInfoService;
import com.luos.api.tracq.vo.UserInfoVo;
import com.luos.common.jwt.JwtUtil;
import com.luos.common.utils.BeanUtil;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.UsrUserInfo;
import com.luos.console.system.service.UsrUserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UsrUserInfoService userInfoService;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public ResponseResult login(UserInfoVo userInfoVo) {
        LambdaQueryWrapper<UsrUserInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UsrUserInfo::getUuinOpenid,userInfoVo.getUuinOpenid());
        List<UsrUserInfo> list = userInfoService.getBaseMapper().selectList(wrapper);
        UsrUserInfo userInfo = null;
        //用户已存在
        if(null != list && list.size()>0){
            userInfo= list.get(0);
            userInfo.setUuinName(userInfoVo.getUuinName());
            userInfo.setUuinSex(userInfoVo.getUuinSex());
            userInfo.setUuinPhone(userInfoVo.getUuinPhone());
        }else{
            //用户不存在创建
            userInfo= BeanUtil.propertiesCopy(userInfoVo, UsrUserInfo.class);
            userInfo.setUuinCreateTime(new Date());
        }

        userInfoService.saveOrUpdate(userInfo);
        //6、获取返回给前台的token
        String token = jwtUtil.createToken(userInfo.getUuinId());
        userInfo.setToken(token);
        return ResponseResult.success(userInfo);
    }
}
