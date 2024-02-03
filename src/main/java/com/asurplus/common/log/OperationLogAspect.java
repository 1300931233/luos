package com.asurplus.common.log;

import com.asurplus.common.shiro.ShiroUtils;
import com.asurplus.common.utils.AddressUtils;
import com.asurplus.common.utils.IPUtils;
import com.asurplus.console.system.entity.SysHandleLog;
import com.asurplus.console.system.service.SysHandleLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 操作日志切面类
 *
 *  @author xlp
 *  @since 2021-04-13
 */

@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    /**
     * sysLoginLogService 日志写入数据库的业务处理类
     */
    @Autowired
    SysHandleLogService sysHandleLogService;

    /**
     * 方法进入前时间戳
     */
    private Long startTime;

    /**
     * 定义切点 @Pointcut
     * 在注解的位置切入代码
     * com.asurplus.common.log.OperationLogAnnotate 是注解所在路劲
     */
    @Pointcut("@annotation(com.asurplus.common.log.OperationLogAnnotate)")
    public void logPointCut() {
    }

    /**
     * 方法执行之前(前置通知)
     */
    @Before(value="@annotation(logger)")
    public void before(OperationLogAnnotate logger){
        startTime = System.currentTimeMillis();
    }


    /**
     * 用户操作完后保存日志到数据库
     * 在注解的位置切入代码
     */
    @AfterReturning(value = "logPointCut() && @annotation(logger)")
    public void saveOperationLog(OperationLogAnnotate logger) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //创建一个操作日志实体类存放此次操作信息
        SysHandleLog sysHandleLog = new SysHandleLog();
        try {
            //获取用户id
            sysHandleLog.setShloCreateUserId(ShiroUtils.getSysUserInfo().getSmanId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            //获取ip
            sysHandleLog.setShloIp(IPUtils.getIpAddress(request));
            //用户位置
            sysHandleLog.setShloLocation(AddressUtils.getAddressByIP(sysHandleLog.getShloIp()));

        } catch (Exception e) {
            //获取ip
            sysHandleLog.setShloIp("127.0.0.1");
            //用户位置
            sysHandleLog.setShloLocation("局域网，无法获取位置");
        }

        //操作时间
        sysHandleLog.setShloCreateTime(new Date());
        //获取类名方法名
//        String className = joinPoint.getTarget().getClass().getName();
//        String methodName = joinPoint.getSignature().getName();
//        operationLog.setOperatorMethod(className + "." + methodName + "()");
        //获取uri
        String requestURI = request.getRequestURI();
        sysHandleLog.setShloUrl(requestURI);
        sysHandleLog.setShloSpendTime(System.currentTimeMillis() - startTime);//响应时间
        sysHandleLog.setShloModel(logger.shloModem());//模块
        //将操作日志写入数据库
        this.sysHandleLogService.add(sysHandleLog);
    }


}

