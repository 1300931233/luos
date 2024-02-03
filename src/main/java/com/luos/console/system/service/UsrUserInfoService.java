package com.luos.console.system.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.UsrUserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
public interface UsrUserInfoService extends IService<UsrUserInfo> {
    /**
     * 分页查询
     */
    LayuiTableResult list(Integer page, Integer limit, UsrUserInfo userInfo);
}
