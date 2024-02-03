package com.luos.api.tracq.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luos.api.tracq.service.CallingCardService;
import com.luos.api.tracq.vo.CallingCardAskVo;
import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.UsrCallingCard;
import com.luos.console.system.service.UsrCallingCardService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xlp
 * @since 2023-06-07
 */
@Service
public class CallingCardServiceImpl implements CallingCardService {

    @Autowired
    private UsrCallingCardService callingCardService;

    @Override
    public LayuiTableResult getDiscloseList(CallingCardAskVo cardAskVo) {

        LambdaQueryWrapper<UsrCallingCard> queryWrapper = new LambdaQueryWrapper<>();
        //是否公开
        queryWrapper.eq(UsrCallingCard::getUccaDisclose,0);
        //查询启用状态的数据
        queryWrapper.eq(UsrCallingCard::getUccaStatus,0);
        //行业
        queryWrapper.like(StringUtils.isNotBlank(cardAskVo.getUccaIndustry()),UsrCallingCard::getUccaIndustry,cardAskVo.getUccaIndustry());
        //省市区
        queryWrapper.eq(StringUtils.isNotBlank(cardAskVo.getUccaProvince()),UsrCallingCard::getUccaProvince,cardAskVo.getUccaProvince());
        queryWrapper.eq(StringUtils.isNotBlank(cardAskVo.getUccaCity()),UsrCallingCard::getUccaCity,cardAskVo.getUccaCity());
        queryWrapper.eq(StringUtils.isNotBlank(cardAskVo.getUccaArea()),UsrCallingCard::getUccaArea,cardAskVo.getUccaArea());
        //名称、公司
        if (StringUtils.isNotEmpty(cardAskVo.getKey())) {
            queryWrapper.and(wrapper -> wrapper.like(UsrCallingCard::getUccaName,cardAskVo.getKey())
                    .or()
                    .like(UsrCallingCard::getUccaCompany, cardAskVo.getKey()));
        }
        queryWrapper.orderByAsc(UsrCallingCard::getUccaCreatTime);
        return new LayuiTableResult(callingCardService.page(new Page<>(cardAskVo.getPage(),cardAskVo.getLimit()),queryWrapper));
    }
}
