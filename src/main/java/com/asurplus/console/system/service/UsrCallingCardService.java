package com.asurplus.console.system.service;

import com.asurplus.common.layui.LayuiTableResult;
import com.asurplus.console.system.entity.UsrCallingCard;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
public interface UsrCallingCardService extends IService<UsrCallingCard> {
    /**
     * 分页查询
     */
    LayuiTableResult list(Integer page, Integer limit, UsrCallingCard callingCard);
}
