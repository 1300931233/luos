package com.luos.console.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luos.common.consts.CacheConst;
import com.luos.common.layui.LayuiTableResult;
import com.luos.common.redis.RedisConst;
import com.luos.common.redis.RedisUtil;
import com.luos.common.shiro.ShiroUtils;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysDictDetail;
import com.luos.console.system.mapper.SysDictDetailMapper;
import com.luos.console.system.service.SysDictDetailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 服务实现类
 */
@Slf4j
@Service
public class SysDictDetailServiceImpl extends ServiceImpl<SysDictDetailMapper, SysDictDetail> implements SysDictDetailService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public LayuiTableResult list(String dictCode) {
        if(StringUtils.isBlank(dictCode)){
            return new LayuiTableResult<>(0, null);
        }
        LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictDetail::getSddeDictCode, dictCode);
        queryWrapper.orderByAsc(SysDictDetail::getSddeCode);
        List<SysDictDetail> list = this.baseMapper.selectList(queryWrapper);
        return new LayuiTableResult<>(null != list && !list.isEmpty() ? list.size() : 0L, list);
    }

    @Override
    public ResponseResult add(SysDictDetail sysDictDetail) {
        LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDictDetail::getSddeDictCode, sysDictDetail.getSddeDictCode());
        queryWrapper.eq(SysDictDetail::getSddeCode, sysDictDetail.getSddeCode());
        List<SysDictDetail> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该字典编号已经存在");
        }
        sysDictDetail.setSddeCreateUserId(ShiroUtils.getSysUserId());
        sysDictDetail.setSddeCreateTime(new Date());
        this.save(sysDictDetail);
        // 存入redis
        redisUtil.setHashMap(RedisConst.Key.SYS_DICT + sysDictDetail.getSddeDictCode(), sysDictDetail.getSddeCode(), sysDictDetail.getSddeName());
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysDictDetail sysDictDetail) {
        LambdaQueryWrapper<SysDictDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.ne(SysDictDetail::getSddeId, sysDictDetail.getSddeId());
        queryWrapper.eq(SysDictDetail::getSddeDictCode, sysDictDetail.getSddeDictCode());
        queryWrapper.eq(SysDictDetail::getSddeCode, sysDictDetail.getSddeCode());
        List<SysDictDetail> sysRoleInfoList = this.list(queryWrapper);
        if (null != sysRoleInfoList && !sysRoleInfoList.isEmpty()) {
            return ResponseResult.error("该字典编号已经存在");
        }
        sysDictDetail.setSddeUpdateUserId(ShiroUtils.getSysUserId());
        this.updateById(sysDictDetail);
        // 存入redis
        redisUtil.setHashMap(RedisConst.Key.SYS_DICT + sysDictDetail.getSddeDictCode(), sysDictDetail.getSddeCode(), sysDictDetail.getSddeName());
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Long id) {
        SysDictDetail sysDictDetail = this.getById(id);
        this.removeById(id);
        // 删除redis
        redisUtil.deleteHashMap(RedisConst.Key.SYS_DICT + sysDictDetail.getSddeDictCode(), sysDictDetail.getSddeCode());
        return ResponseResult.success();
    }

    @Override
    @Cacheable(value = CacheConst.SYS_DICT_CACHE, key = "#dictCode+':'+#code")
    public String getDictDataByTypeAndValue(String dictCode, String code) {
        // 先从 Redis里面获取
        Object object = redisUtil.getHashMap(RedisConst.Key.SYS_DICT + dictCode, code);
        // log.info("从Redis获取字典：{}-->{}:{}", dictCode, code, object);
        if (null != object) {
            return String.valueOf(object);
        }
        // 再从数据库获取
        String dictText = this.baseMapper.getSysDictDetail(dictCode, code);
        // log.info("从数据库获取字典：{}-->{}:{}", dictCode, code, dictText);
        // 存入 Redis
        if (StringUtils.isNotBlank(dictText)) {
            redisUtil.setHashMap(RedisConst.Key.SYS_DICT + dictCode, code, dictText);
        }
        return dictText;
    }

    @Override
    public List<SysDictDetail> listSysDictDetailByDictCode(String dictCode) {
        QueryWrapper<SysDictDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysDictDetail::getSddeCode, SysDictDetail::getSddeName);
        queryWrapper.lambda().eq(SysDictDetail::getSddeDictCode, dictCode);
        return this.list(queryWrapper);
    }
    @Override
    public List<SysDictDetail> listSysDictDetailByDictCodeNeCode(String dictCode,String code) {
        QueryWrapper<SysDictDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(SysDictDetail::getSddeCode, SysDictDetail::getSddeName);
        queryWrapper.lambda().eq(SysDictDetail::getSddeDictCode, dictCode);
        queryWrapper.lambda().ne(SysDictDetail::getSddeCode,code);
        return this.list(queryWrapper);
    }

}
