package com.luos.console.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luos.common.consts.SystemConst;
import com.luos.common.layui.LayuiTreePojo;
import com.luos.common.shiro.ShiroUtils;
import com.luos.common.utils.ResponseResult;
import com.luos.console.system.entity.SysPermissionInfo;
import com.luos.console.system.mapper.SysPermissionInfoMapper;
import com.luos.console.system.pojo.MenuPojo;
import com.luos.console.system.pojo.PermissionPojo;
import com.luos.console.system.service.SysPermissionInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 菜单权限表 服务实现类
 */
@Service
public class SysPermissionInfoServiceImpl extends ServiceImpl<SysPermissionInfoMapper, SysPermissionInfo> implements SysPermissionInfoService {

    @Override
    public List<SysPermissionInfo> list(Integer pid) {
        LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(pid>0){
            queryWrapper.eq(SysPermissionInfo::getSpinPid,pid);
        }else{
            queryWrapper.eq(SysPermissionInfo::getSpinPid,0);
        }
        queryWrapper.orderByAsc(SysPermissionInfo::getSpinType).orderByAsc(SysPermissionInfo::getSpinPid).orderByAsc(SysPermissionInfo::getSpinSort);
        List<SysPermissionInfo> list = this.list(queryWrapper);
        for(SysPermissionInfo tmp:list){
            LambdaQueryWrapper<SysPermissionInfo> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(SysPermissionInfo::getSpinPid,tmp.getSpinId());
            if(0==this.count(queryWrapper1)){
                tmp.setHaveChild(false);
            }
        }
        return list;
    }

    @Override
    public ResponseResult add(JSONObject param) {
        SysPermissionInfo sysPermissionInfo = JSONObject.parseObject(param.getString("data"), SysPermissionInfo.class);
        if (StringUtils.isBlank(sysPermissionInfo.getSpinName())) {
            return ResponseResult.error("请输入菜单名称");
        }
        if (0 != sysPermissionInfo.getSpinType() && StringUtils.isBlank(sysPermissionInfo.getSpinSign())) {
            return ResponseResult.error("请输入权限标识");
        }
        if (StringUtils.isNotBlank(sysPermissionInfo.getSpinSign())) {
            //匹配中文
            if (!sysPermissionInfo.getSpinSign().matches("^[a-zA-Z]+(:[a-zA-Z]+)*$")) {
                return ResponseResult.error("请输入以冒号隔开的英文字符，不区分大小写，形如：Qwe:aWm");
            }
            LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysPermissionInfo::getSpinSign, sysPermissionInfo.getSpinSign());
            List<SysPermissionInfo> sysPermissionInfos = this.list(queryWrapper);
            if (null != sysPermissionInfos && !sysPermissionInfos.isEmpty()) {
                return ResponseResult.error("该权限标识已经存在，请检查后提交");
            }
        }
        sysPermissionInfo.setCreateUserId(ShiroUtils.getSysUserId());
        sysPermissionInfo.setSpinCreateTime(new Date());
        this.save(sysPermissionInfo);
        PermissionPojo permissionPojo = JSONObject.parseObject(param.getString("pojo"), PermissionPojo.class);
        if (null != permissionPojo) {
            // 按钮集合
            List<SysPermissionInfo> btnList = new ArrayList<>();
            SysPermissionInfo btnObj = null;
            if (null != permissionPojo.getShow() && 1 == permissionPojo.getShow()) {
                btnObj = new SysPermissionInfo();
                btnObj.setSpinPid(sysPermissionInfo.getSpinId());
                btnObj.setSpinName("查看");
                btnObj.setSpinType(2);
                btnObj.setSpinSign(sysPermissionInfo.getSpinSign().substring(0, sysPermissionInfo.getSpinSign().lastIndexOf(":")) + ":show");
                btnObj.setSpinSort(0);
                btnObj.setSpinCreateTime(new Date());
                sysPermissionInfo.setCreateUserId(ShiroUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (null != permissionPojo.getAdd() && 1 == permissionPojo.getAdd()) {
                btnObj = new SysPermissionInfo();
                btnObj.setSpinPid(sysPermissionInfo.getSpinId());
                btnObj.setSpinName("新增");
                btnObj.setSpinType(2);
                btnObj.setSpinSign(sysPermissionInfo.getSpinSign().substring(0, sysPermissionInfo.getSpinSign().lastIndexOf(":")) + ":add");
                btnObj.setSpinSort(1);
                btnObj.setSpinCreateTime(new Date());
                sysPermissionInfo.setCreateUserId(ShiroUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (null != permissionPojo.getEdit() && 1 == permissionPojo.getEdit()) {
                btnObj = new SysPermissionInfo();
                btnObj.setSpinPid(sysPermissionInfo.getSpinId());
                btnObj.setSpinName("修改");
                btnObj.setSpinType(2);
                btnObj.setSpinSign(sysPermissionInfo.getSpinSign().substring(0, sysPermissionInfo.getSpinSign().lastIndexOf(":")) + ":edit");
                btnObj.setSpinSort(2);
                btnObj.setSpinCreateTime(new Date());
                sysPermissionInfo.setCreateUserId(ShiroUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (null != permissionPojo.getDel() && 1 == permissionPojo.getDel()) {
                btnObj = new SysPermissionInfo();
                btnObj.setSpinPid(sysPermissionInfo.getSpinId());
                btnObj.setSpinName("删除");
                btnObj.setSpinType(2);
                btnObj.setSpinSign(sysPermissionInfo.getSpinSign().substring(0, sysPermissionInfo.getSpinSign().lastIndexOf(":")) + ":del");
                btnObj.setSpinSort(3);
                btnObj.setSpinCreateTime(new Date());
                sysPermissionInfo.setCreateUserId(ShiroUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (null != permissionPojo.getExport() && 1 == permissionPojo.getExport()) {
                btnObj = new SysPermissionInfo();
                btnObj.setSpinPid(sysPermissionInfo.getSpinId());
                btnObj.setSpinName("导出");
                btnObj.setSpinType(2);
                btnObj.setSpinSign(sysPermissionInfo.getSpinSign().substring(0, sysPermissionInfo.getSpinSign().lastIndexOf(":")) + ":export");
                btnObj.setSpinSort(4);
                btnObj.setSpinCreateTime(new Date());
                sysPermissionInfo.setCreateUserId(ShiroUtils.getSysUserId());
                btnList.add(btnObj);
            }
            if (CollectionUtil.isNotEmpty(btnList)) {
                this.saveBatch(btnList);
            }
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult update(SysPermissionInfo sysPermissionInfo) {
        if (StringUtils.isBlank(sysPermissionInfo.getSpinName())) {
            return ResponseResult.error("请输入菜单名称");
        }
        if (0 != sysPermissionInfo.getSpinType() && StringUtils.isBlank(sysPermissionInfo.getSpinSign())) {
            return ResponseResult.error("请输入权限标识");
        }
        if (!sysPermissionInfo.getSpinSign().matches("^[a-zA-Z]+(:[a-zA-Z]+)*$")) {
            return ResponseResult.error("请输入以冒号隔开的英文字符，不区分大小写，形如：Qwe:aWm");
        }
        if (StringUtils.isNotBlank(sysPermissionInfo.getSpinSign())) {
            LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.ne(SysPermissionInfo::getSpinId, sysPermissionInfo.getSpinId());
            queryWrapper.eq(SysPermissionInfo::getSpinSign, sysPermissionInfo.getSpinSign());
            List<SysPermissionInfo> sysPermissionInfos = this.list(queryWrapper);
            if (null != sysPermissionInfos && !sysPermissionInfos.isEmpty()) {
                return ResponseResult.error("该权限标识已经存在，请检查后提交");
            }
        }
        if (StringUtils.isNotBlank(sysPermissionInfo.getSpinIcon()) && !sysPermissionInfo.getSpinIcon().startsWith("fa ")) {
            sysPermissionInfo.setSpinIcon("fa " + sysPermissionInfo.getSpinIcon());
        }
        this.updateById(sysPermissionInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult delete(Integer id) {
        SysPermissionInfo sysPermissionInfo = this.baseMapper.selectById(id);
        if (null == sysPermissionInfo) {
            return ResponseResult.error("数据错误");
        }
        // 所有下级ids
        StringBuffer ids = new StringBuffer();
        ids.append(id).append(",");
        // 删除下级
        StringBuffer pidBuffer = new StringBuffer();
        pidBuffer.append(id);
        boolean flag = true;
        while (flag) {
            // 查询下级ids
            String childerIds = this.baseMapper.getChilderIds(pidBuffer.toString());
            if (StringUtils.isNotBlank(childerIds)) {
                pidBuffer = new StringBuffer();
                pidBuffer.append(childerIds);
                // 下级ids
                ids.append(childerIds).append(",");
            } else {
                flag = false;
            }
        }
        // 修改自身和下级
        ids.deleteCharAt(ids.length() - 1);
        this.baseMapper.deleteBatch(ids.toString());
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateStatus(Integer id, Integer status) {
        SysPermissionInfo sysPermissionInfo = this.baseMapper.selectById(id);
        if (null == sysPermissionInfo) {
            return ResponseResult.error("数据错误");
        }
        // 所有下级ids
        StringBuffer ids = new StringBuffer();
        ids.append(id).append(",");
        // 删除下级
        StringBuffer pidBuffer = new StringBuffer();
        pidBuffer.append(id);
        boolean flag = true;
        while (flag) {
            // 查询下级ids
            String childerIds = this.baseMapper.getChilderIds(pidBuffer.toString());
            if (StringUtils.isNotBlank(childerIds)) {
                pidBuffer = new StringBuffer();
                pidBuffer.append(childerIds);
                // 下级ids
                ids.append(childerIds).append(",");
            } else {
                flag = false;
            }
        }
        // 修改自身和下级
        ids.deleteCharAt(ids.length() - 1);
        this.baseMapper.updateStatusBatch(ids.toString(), status);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult updateSort(Integer id, Integer sort) {
        if (null == id || 0 == id || null == sort) {
            return ResponseResult.error("数据错误");
        }
        SysPermissionInfo sysPermissionInfo = this.getById(id);
        if (null == sysPermissionInfo) {
            return ResponseResult.error("数据错误");
        }
        if (sort.equals(sysPermissionInfo.getSpinSort())) {
            return ResponseResult.success();
        }
        sysPermissionInfo.setSpinSort(sort);
        this.updateById(sysPermissionInfo);
        return ResponseResult.success();
    }

    @Override
    public ResponseResult listPermissionInfoByPid(Integer type, Integer pid) {
        LambdaQueryWrapper<SysPermissionInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPermissionInfo::getSpinType, type);
        queryWrapper.eq(SysPermissionInfo::getSpinStatus, 0);
        if (null != pid && 0 < pid) {
            queryWrapper.eq(SysPermissionInfo::getSpinPid, pid);
        }
        queryWrapper.orderByAsc(SysPermissionInfo::getSpinSort);
        return ResponseResult.success(this.list(queryWrapper));
    }

    @Override
    public JSONObject initMenu(HttpSession session) {
        Integer userId = ShiroUtils.getSysUserId();
        if (null == userId || 0 == userId) {
            return null;
        }
        List<MenuPojo> list;
        // 如果是超级管理员
        if (SecurityUtils.getSubject().hasRole("administrator")) {
            list = this.baseMapper.listPermissionInfo();
        }
        // 根据用户id查询菜单权限
        else {
            list = this.baseMapper.listPermissionInfoByUserId(userId);
        }
        if (null == list || list.isEmpty()) {
            ResponseResult.error("用户信息异常");
        }
        // 需要返回的list
        List<MenuPojo> resList = new ArrayList<>();
        // 遍历list
        Iterator<MenuPojo> iter = list.iterator();
        // 先取出第一层
        MenuPojo menuPojo = null;
        while (iter.hasNext()) {
            menuPojo = iter.next();
            if (0 == menuPojo.getType()) {
                menuPojo.setChild(new ArrayList<>());
                resList.add(menuPojo);
                iter.remove();
            }
        }
        if (null == list || list.isEmpty() || null == resList || resList.isEmpty()) {
            return null;
        }
        List<MenuPojo> child = null;
        for (MenuPojo item : list) {
            for (MenuPojo temp : resList) {
                if (item.getPid().equals(temp.getId())) {
                    child = temp.getChild();
                    child.add(item);
                    temp.setChild(child);
                    break;
                }
            }
        }
        menuPojo = new MenuPojo();
        menuPojo.setTitle("");
        menuPojo.setIcon("");
        menuPojo.setHref("");
        menuPojo.setChild(resList);
        child = new ArrayList<>();
        child.add(menuPojo);
        // 返回的JSON
        JSONObject resJson = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("title", "首页");
        jsonObject.put("href", "welcome");
        resJson.put("homeInfo", jsonObject);
        resJson.put("menuInfo", child);
        // 将用户权限信息缓存到session中
        session.setAttribute(SystemConst.SYSTEM_USER_MENU, resJson);
        return resJson;
    }

    @Override
    public ResponseResult listPermissionForTree(Integer roleId) {
        List<LayuiTreePojo> resList = new ArrayList<>();
        List<LayuiTreePojo> list = this.baseMapper.listPermissionForTree(roleId);
        if (null == list || list.isEmpty()) {
            return ResponseResult.success();
        }
        List<LayuiTreePojo> list1 = new ArrayList<>();
        List<LayuiTreePojo> list2 = new ArrayList<>();
        for (LayuiTreePojo item : list) {
            // 一级菜单
            if (0 == item.getType()) {
                item.setChildren(new ArrayList<>());
                resList.add(item);
            }
            // 二级菜单
            if (1 == item.getType()) {
                item.setChildren(new ArrayList<>());
                list1.add(item);
            }
            // 三级菜单
            if (2 == item.getType()) {
                list2.add(item);
            }
        }
        List<LayuiTreePojo> child = null;
        if (null == list1 || list1.isEmpty()) {
            return ResponseResult.success(resList);
        } else if (null == list2 || list2.isEmpty()) {
            for (LayuiTreePojo item : resList) {
                for (LayuiTreePojo temp : list1) {
                    if (item.getId().equals(temp.getPid())) {
                        item.setChecked(false);
                        child = item.getChildren();
                        child.add(temp);
                        item.setChildren(child);
                    }
                }
            }
            return ResponseResult.success(resList);
        }
        for (LayuiTreePojo item : list1) {
            for (LayuiTreePojo temp : list2) {
                if (item.getId().equals(temp.getPid())) {
                    item.setChecked(false);
                    child = item.getChildren();
                    child.add(temp);
                    item.setChildren(child);
                }
            }
        }
        for (LayuiTreePojo item : resList) {
            for (LayuiTreePojo temp : list1) {
                if (item.getId().equals(temp.getPid())) {
                    item.setChecked(false);
                    child = item.getChildren();
                    child.add(temp);
                    item.setChildren(child);
                }
            }
        }
        return ResponseResult.success(resList);
    }

}
