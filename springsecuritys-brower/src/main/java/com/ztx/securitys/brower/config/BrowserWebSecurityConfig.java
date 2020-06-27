package com.ztx.securitys.brower.config;

import authentication.BrowserAuthenticationFailureHandler;
import authentication.BrowserAuthenticationSuccessHandler;
import com.ztx.securitys.core.config.ItemUserDetailService;
import com.ztx.securitys.core.properties.SecurityConstants;
import com.ztx.securitys.core.properties.SecurityProperties;
import com.ztx.securitys.core.validate.code.ValidateCode;
import com.ztx.securitys.core.validate.code.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;
import javax.annotation.Resource;

/**
 * SpringSecurity配置，
 * Created by macro on 2019/10/8.
 */
@Configuration
@EnableWebSecurity// 配置安全认证策略。2: 加载了AuthenticationConfiguration, 配置了认证信息。
public class BrowserWebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private ItemUserDetailService itemUserDetailService;
    @Autowired
    private DataSource dataSource;
        @Autowired
    private SecurityProperties securityProperties;

    /*
        异常处理，由于使用了jwt 所以不生效
        @Bean
        public AccessDeniedHandler getAccessDeniedHandler() {
            return new SecurityAccessDeniedHandler();
        }
        @Bean
        public AuthenticationEntryPoint authenticationEntryPoint(){
            return  new CustomizeAuthenticationEntryPoint();
        }
    */
    @Bean
    public BrowserAuthenticationSuccessHandler browserAuthenticationSuccessHandler() {
        return new BrowserAuthenticationSuccessHandler();
    }

    @Bean
    public BrowserAuthenticationFailureHandler browserAuthenticationFailureHandler() {
        return new BrowserAuthenticationFailureHandler();
    }
    @Bean//加密模式，密码编码器
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource((DataSource) dataSource);
       // jdbcTokenRepository.setCreateTableOnStartup(true);//自动根据语句建表
        return  jdbcTokenRepository;
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 在这里配置是因为所有的请求走的都是交给security来完成的
     *
     * @param http
     * @throws Exception
     */
    @Override//安全拦截机制
    public void configure(HttpSecurity http) throws Exception {
        //添加自定义的过滤器链
        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(browserAuthenticationFailureHandler());
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();//调用方法
        http
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_SIGN_IN_PROCESSING_URL_FORM)
                .successHandler(browserAuthenticationSuccessHandler())
                .failureHandler(browserAuthenticationFailureHandler())
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())//token保存方法
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(itemUserDetailService)
                .and()
                .authorizeRequests()
                .antMatchers(securityProperties.getBrowser().getSignInPage(), SecurityConstants.DEFAULT_UNAUTHENTICATION_URL, SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/**")//放行请求
                .permitAll()
                .anyRequest()//需要认证请求
                .authenticated()
                //session管理，一个四种，never从不创建,ifrequired如果登录时候需要就创建 alway如果没有及创建  stateles不会创建也不会使用session restful时使用多
                .and().csrf().disable();  //配置取消session管理,又Jwt来获取用户状态,否则即使token无效,也会有session信息,依旧判断用户为登录状态

    }

}