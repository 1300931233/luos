package com.asurplus.api.tracq.service;

import com.asurplus.api.tracq.vo.UserInfoVo;
import com.asurplus.common.utils.ResponseResult;

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
