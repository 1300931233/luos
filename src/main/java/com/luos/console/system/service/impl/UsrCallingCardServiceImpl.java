package com.luos.console.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luos.common.layui.LayuiTableResult;
import com.luos.console.system.entity.UsrCallingCard;
import com.luos.console.system.mapper.UsrCallingCardMapper;
import com.luos.console.system.service.UsrCallingCardService;
import org.apache.commons.lang3.StringUtils;
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
public class UsrCallingCardServiceImpl extends ServiceImpl<UsrCallingCardMapper, UsrCallingCard> implements UsrCallingCardService {

    @Override
    public LayuiTableResult list(Integer page, Integer limit, UsrCallingCard callingCard) {

        LambdaQueryWrapper<UsrCallingCard> queryWrapper = new LambdaQueryWrapper<>();
        //名称
        queryWrapper.like(StringUtils.isNotBlank(callingCard.getUccaName()), UsrCallingCard::getUccaName,callingCard.getUccaName());
        //电话
        queryWrapper.like(StringUtils.isNotBlank(callingCard.getUccaPhone()), UsrCallingCard::getUccaPhone,callingCard.getUccaPhone());
        //是否公开
        queryWrapper.eq(null !=callingCard.getUccaDisclose(), UsrCallingCard::getUccaDisclose,callingCard.getUccaDisclose());


        return new LayuiTableResult(this.page(new Page<>(page,limit),queryWrapper));
    }
}
