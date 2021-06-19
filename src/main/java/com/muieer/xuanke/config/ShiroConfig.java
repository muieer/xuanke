package com.muieer.xuanke.config;

import com.muieer.xuanke.shiro.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(
            @Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);
        Map<String, String> filterMap = new LinkedHashMap<>();
        //无需权限，登录页面和登录请求无需拦截
        //发送登陆请求
        filterMap.put("/do/login", "anon");
        //跳转到登陆页面
        filterMap.put("/home", "anon");

        //需要登录才能进入
//        filterMap.put("/index","authc");
        filterMap.put("/*", "authc");
        bean.setFilterChainDefinitionMap(filterMap);

        //没登陆前提下设置登陆跳转
        bean.setLoginUrl("/home");
        return bean;
    }

    @Bean(name = "getDefaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {

        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联Realm
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    @Bean(name = "userRealm")
    public UserRealm getUserRealm() {
        return new UserRealm();
    }

}
