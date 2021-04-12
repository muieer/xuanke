package com.yhh.xuanke.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Druid 配置
 * http://localhost:8080/druid/login.html
 * @author Long
 */
@Configuration
public class DruidConfig {

    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }


    /**
     * 配置Druid的监控
     * 1、配置一个管理后台的Servlet
     * @return bean
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 后台登录账号密码配置
        Map<String, String> initParams = new HashMap<>(2);
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "admin");
        //默认就是允许所有访问 key为allow
        initParams.put("allow", "");
        // 配置不允许谁访问 key为deny
//        initParams.put("deny", "192.168.15.21");
        // 配置初始化参数
        bean.setInitParameters(initParams);
        return bean;
    }

    /**
     * 2、配置一个web监控的filter
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());

        Map<String, String> initParams = new HashMap<>();
        initParams.put("exclusions", "*.js,*.css,/druid/*");

        bean.setInitParameters(initParams);

        bean.setUrlPatterns(Collections.singletonList("/*"));

        return bean;
    }

}
