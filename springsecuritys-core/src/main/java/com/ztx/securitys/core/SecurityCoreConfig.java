/**
 * 
 */
package com.ztx.securitys.core;

import com.ztx.securitys.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * @author zhangyang
 * 接口安全配置核心模块 读取yml配置请求生效
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
