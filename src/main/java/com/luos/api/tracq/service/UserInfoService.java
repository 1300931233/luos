package com.luos.api.tracq.service;


import com.luos.api.tracq.vo.UserInfoVo;
import com.luos.common.utils.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
public interface UserInfoService {

    /**
     *
     * @param userInfoVo
     * @return
     */
    ResponseResult login(UserInfoVo userInfoVo);
}
