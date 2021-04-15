package com.ca.config;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * @author yayuzhi
 */
@Configuration
public class SpringShiroConfig {
    /**
     *设置全局会话超时时间
     * @return DefaultWebSessionManager
     */
    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sManager= new DefaultWebSessionManager();
        sManager.setGlobalSessionTimeout(60*60*1000);
        return sManager;

    }


    /**
     * 登录成功后返回一个cookie
     * @return CookieRememberMeManager
     */
    @Bean
    public RememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager=new CookieRememberMeManager();
        SimpleCookie cookie=new SimpleCookie("rememberMe");
        cookie.setMaxAge(7*24*60*60);
        cookieRememberMeManager.setCookie(cookie);
        return cookieRememberMeManager;
    }

    /**配置授权缓存管理对象*/
    /**
     * 缓存管理器
     * 内存受限缓存管理器
     * @return MemoryConstrainedCacheManager
     */
    @Bean
    public CacheManager shiroCacheManager(){
        return new MemoryConstrainedCacheManager();
    }

    /**配置授权检测顾问*/
    /**
     * 授权属性来源顾问
     * @param securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        //此对象关联着切入点和通知对象
        return advisor;
    }

    /**此注解描述的方法，其返回值会交给spring管理。
     * @param realm
     * @param shiroCacheManager
     * @param rememberMeManager
     * @param sessionManager
     * @return
     */
    @Bean
    public SecurityManager securityManager(Realm realm,
                                           CacheManager shiroCacheManager,
                                           RememberMeManager rememberMeManager,
                                           SessionManager sessionManager){//此对象为shiro框架的核心
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(shiroCacheManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }


    /**
     *此对象中要配置一些访问规则(匿名访问的资源，认证的访问的资源)
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactory (SecurityManager securityManager) {
        ShiroFilterFactoryBean sfBean= new ShiroFilterFactoryBean();
        sfBean.setSecurityManager(securityManager);
        sfBean.setLoginUrl("/doLoginUI");
        //定义map指定请求过滤规则(哪些资源允许匿名访问,哪些必须认证访问)
        LinkedHashMap<String,String> map= new LinkedHashMap<>();
        //静态资源允许匿名访问:"anon"
        //这里的anon对应着shiro中的一些默认过滤器
        map.put("/bower_components/**","anon");
        map.put("/css/**","anon");
        map.put("/fonts/**","anon");
        map.put("/images/**","anon");
        map.put("/js/**","anon");
        map.put("/lib/**","anon");
        map.put("/admin/doLogin", "anon");
        map.put("/doLogout","logout");
        //除了匿名访问的资源,其它都要认证("authc")后访问
        map.put("/**","user");
        sfBean.setFilterChainDefinitionMap(map);
        return sfBean;
    }
}
