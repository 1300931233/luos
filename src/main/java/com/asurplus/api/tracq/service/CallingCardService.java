package com.asurplus.api.tracq.service;

import com.asurplus.api.tracq.vo.CallingCardAskVo;
import com.asurplus.common.layui.LayuiTableResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
public interface CallingCardService {
    /**
     * 分页查询 公开库列表数据
     */
    LayuiTableResult getDiscloseList(CallingCardAskVo cardAskVo);
}
