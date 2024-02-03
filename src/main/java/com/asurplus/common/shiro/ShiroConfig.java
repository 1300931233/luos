package com.asurplus.common.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import cn.hutool.core.codec.Base64;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    /**
     * 注入这个是是为了在thymeleaf中使用shiro的自定义tag。
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    /**
     * 地址过滤器
     *
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 设置登录url
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 设置主页url
        shiroFilterFactoryBean.setSuccessUrl("/");
        // 设置未授权的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 注销登录
        filterChainDefinitionMap.put("/loginOut", "logout");
        // 开放登录接口
        filterChainDefinitionMap.put("/doLogin", "anon");
        // 开放获取登录验证码接口
        filterChainDefinitionMap.put("/kaptcha/**", "anon");
        // 文件访问
        filterChainDefinitionMap.put("/file/**", "anon");
        // 开放App接口
        filterChainDefinitionMap.put("/api/**", "anon");
        // 开放接口文档
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/service-worker.js", "anon");
        filterChainDefinitionMap.put("/swagger-resources/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");


        // 开放静态页面
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/layui/**", "anon");
        filterChainDefinitionMap.put("/layuimini/**", "anon");
        filterChainDefinitionMap.put("/module/**", "anon");
        filterChainDefinitionMap.put("/upload/**", "anon");
        filterChainDefinitionMap.put("/font-awesome/**", "anon");


        // 其余url全部拦截，必须放在最后
//        filterChainDefinitionMap.put("/**", "authc");
        filterChainDefinitionMap.put("/**", "user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 安全管理器
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        /**
         设置自定义的relam
         */
        securityManager.setRealm(loginRelam());
        // 设置session管理器
        securityManager.setSessionManager(sessionManager());
        // 设置记住登录管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }
    /**
     * 登录规则
     * 会话管理
     * 默认使用容器session，这里改为自定义session
     * session的全局超时时间默认是30分钟
     *
     * @return
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        // 会话超时时间，单位：毫秒
        sessionManager.setGlobalSessionTimeout(30 * 60 * 1000);
        // 定时清理失效会话, 清理用户直接关闭浏览器造成的孤立会话
        sessionManager.setSessionValidationInterval(60 * 60 * 1000);
        // 是否开启定时清理失效会话
        sessionManager.setSessionValidationSchedulerEnabled(true);
        // 指定sessionid
        sessionManager.setSessionIdCookie(sessionIdCookie());
        // 是否允许将sessionId 放到 cookie 中
        sessionManager.setSessionIdCookieEnabled(true);
        // 是否允许将 sessionId 放到 Url 地址拦中
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        // 默认使用MemerySessionDao，设置为EnterpriseCacheSessionDAO以配合ehcache实现分布式集群缓存支持
        sessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return sessionManager;
    }

    /**
     * 指定本系统sessionid, 问题: 与servlet容器名冲突, 如jetty, tomcat 等默认jsessionid,
     * 当跳出shiro servlet时如error-page容器会为jsessionid重新分配值导致登录会话丢失!
     *
     * @return
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("shiro.session");
        // 防止xss攻击，窃取cookie内容
        simpleCookie.setHttpOnly(true);
        return simpleCookie;
    }

    /**
     * cookie管理对象
     *
     * @return
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4BxVhuFKUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }
    /**
     * 记住登录
     *
     * @return
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 防止xss攻击，窃取cookie内容
        simpleCookie.setHttpOnly(true);
        // 15天有效期
        simpleCookie.setMaxAge(15 * 24 * 60 * 60);
        return simpleCookie;
    }


    /**
     * 自定义认证授权规则
     */
    @Bean
    public LoginRelam loginRelam() {
        // 登录认证规则
        LoginRelam loginRelam = new LoginRelam();
        // 自定义加密规则
        loginRelam.setCredentialsMatcher(hashedCredentialsMatcher());
        return loginRelam;
    }
    /**
     * 凭证匹配器
     * 执行login(token)后由securityManager调用，用于计算密码加密后的密文
     *
     * @return
     */
    @Bean
    public RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher(ehCacheManager());

//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 设置散列算法
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列计算次数，相当于md5(md5(""))
        retryLimitHashedCredentialsMatcher.setHashIterations(6);
        // storedCredentialsHexEncoded默认是true，此时用的是密码加密用的是Hex编码；false时用Base64编码
        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        // 错误限制次数，5次
        retryLimitHashedCredentialsMatcher.setIncrementAndGet(5);
        return retryLimitHashedCredentialsMatcher;
    }

    /**
     * 以下是为了能够使用@RequiresPermission()等标签
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启shiro aop注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 显式声明 CacheManager ,防止 EhCacheCacheConfiguration 调用 ehCacheCacheManager()
     * 继续生成 CacheManager
     * @return
     */
    @Bean
    public CacheManager ehCacheCacheManager() {
        CacheManager cacheManager = CacheManager.getCacheManager("test");// 2.10.6
        if(cacheManager == null) {
            try {
                cacheManager = CacheManager.create(ResourceUtils.getInputStreamForPath("classpath:shiro-ehcache.xml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return cacheManager;
    }

    /**
     * 将生成的 CacheManager 对象转为Shiro 管理的 EhCacheManager 对象
     * @return
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        CacheManager cacheManager = ehCacheCacheManager();
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }

}
