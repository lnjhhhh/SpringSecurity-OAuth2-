package com.lnjhhhh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 19:42
 * @Version 1.0
 * <p>
 * 将当前资源服务交给OAuth2资源服务进行管理
 */
@Configuration
@EnableResourceServer
public class OAuthSourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    /**
     * 设置OAuth2资源服务保存Token的策略
     * 常用的保存策略有：
     * JdbcTokenStore：将Token保存到数据库中（企业开发常用）
     * RedisTokenStore：将Token保存到Redis中
     * InMemoryTokenStore：将Token保存到内存中
     */
    @Bean
    public TokenStore jdbcTokenStore(DataSource dataSource) {
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 设置OAuth2资源服务器的一些配置
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        //设置当前资源服务器的ID 这个是必须设置 只有设置了id 才能设置其他服务器能访问的资源服务
        //里面的资源服务器ID随意即可 但是要唯一和通俗易懂
        resources.resourceId("product_api")
                .tokenStore(tokenStore);
    }

    /**
     * 设置关于Security的配置 一般对于资源服务器的Security配置都是固定的
     * 以后直接复制粘贴即
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //指定不同请求方式访问资源所需要的权限，一般查询是read，其余是write。
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/**").access("#oauth2.hasScope('read')")
                .antMatchers(HttpMethod.POST, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PATCH, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.PUT, "/**").access("#oauth2.hasScope('write')")
                .antMatchers(HttpMethod.DELETE, "/**").access("#oauth2.hasScope('write')")
                .and()
                .headers().addHeaderWriter((request, response) -> {
                    //设置允许跨域 因为是一般其它服务器访问另外的服务器都是AJAX请求 所以会存在跨域问题
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    //如果是跨域的预检请求，则原封不动向下传达请 求头信息
                    //如果不设置这个就会存在发送预检请求携带的请求头在正式请求中没有存在
                    if (request.getMethod().equals("OPTIONS")) {
                        response.setHeader("Access-Control-Allow-Methods", request.getHeader("AccessControl-Request-Method"));
                        response.setHeader("Access-Control-Allow-Headers", request.getHeader("AccessControl-Request-Headers"));
                    }
                });
    }
}

