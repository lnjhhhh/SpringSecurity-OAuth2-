package com.lnjhhhh.config;

import com.lnjhhhh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * @Author lnjhhhh
 * @CreateDate 2020/10/2 22:58
 * @Version 1.0
 *
 * OAuth2 认证服务端的配置
 *
 */
@Configuration
@EnableAuthorizationServer
public class OAuthServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    /**
     * 配置客户端信息来源：
     *      InMemoryClientDetailsService：从内存中来
     *      JdbcClientDetailsService：从数据库中来（推荐）
     */
    @Bean
    public ClientDetailsService jdbcClientDetailsService(){
        return new JdbcClientDetailsService(dataSource);
    }

    /**
     * 配置Token的保存策略：
     *      JdbcTokenStore：保存到数据库中
     *      RedisTokenStore：保存到Redis中
     *      InMemoryTokenStore：保存到内存中
     */
    @Bean
    public TokenStore tokenStore(){
        return new JdbcTokenStore(dataSource);
    }

    /**
     * 配置授权信息保存策略:
     *      JdbcApprovalStore：保存到数据库中
     *      InMemoryApprovalStore：保存到内存中
     */
    @Bean
    public ApprovalStore approvalStore(){
        return new JdbcApprovalStore(dataSource);
    }

    /**
     * 配置授权信息专用对象来源：
     *      JdbcAuthorizationCodeServices：从数据库中来
     *      InMemoryAuthorizationCodeServices：从内存中来
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(dataSource);
    }

    /**
     * 让客户端登录信息来源生效
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //注意必须配置了这段配置，OAuth2 才会去数据库中获取客户端信息
        clients.withClientDetails(jdbcClientDetailsService());
    }

    /**
     * 设置OAuth2 检测Token的策略
     * 并且也可以重新设置身份认证方式，即自定义认证方式
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许客户端以Form表单的方式传递Token，即我们可以直接在地址栏输入Token传递
        //否则只允许以Post请求传递
        security.allowFormAuthenticationForClients();
        //设置检验Token之前必须认证，这个是固定写法
        security.checkTokenAccess("isAuthenticated()");
    }

    /**
     * 让我配置的各种信息来源生效
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
       endpoints.approvalStore(approvalStore())
                .authenticationManager(authenticationManager)
                .authorizationCodeServices(authorizationCodeServices())
                .tokenStore(tokenStore())
                //让刷新Token生效  即如果要使刷新Token生效必须设置这个
                .userDetailsService(userService);
    }
}
