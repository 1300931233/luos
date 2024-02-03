package com.luos.common.shiro;

import com.luos.console.system.entity.SysManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.subject.Subject;

/**
 * 登录用户通用处理工具类
 */
@Slf4j
public class ShiroUtils {

    private ShiroUtils() {
        throw new AssertionError();
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    public static SysManager getSysUserInfo() {
        try {
            Subject subject = SecurityUtils.getSubject();
            SysManager user = (SysManager) subject.getPrincipal();
            if (user != null) {
                return user;
            }
        } catch (UnavailableSecurityManagerException e) {
            log.error("SingletonLoginUtils.getSysUserInfo:{}", e);
        }
        return null;
    }

    /**
     * 获取登录用户ID
     *
     * @return
     */
    public static Integer getSysUserId() {
        try {
            Subject subject = SecurityUtils.getSubject();
            SysManager userInfo = (SysManager) subject.getPrincipal();
            if (null != userInfo) {
                return userInfo.getSmanId();
            }
        } catch (UnavailableSecurityManagerException e) {
            log.error("SingletonLoginUtils.getSysUserId:{}", e);
        }
        return null;
    }
}
