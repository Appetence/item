/**
 * 
 */
package com.ztx.securitys.brower.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztx.securitys.core.properties.SecurityConstants;
import com.ztx.securitys.core.properties.SecurityProperties;
import com.ztx.securitys.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * 浏览器环境下与安全相关的服务
 * 
 * @author zhailiang
 *
 */
@RestController
public class BrowserSecurityController  {

	private Logger logger = LoggerFactory.getLogger(BrowserSecurityController.class);
	//跳转之前请求缓存到session
	private RequestCache requestCache = new HttpSessionRequestCache();
	//跳转
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	//自定义的配置类
	@Autowired
	private SecurityProperties securityProperties;

//	@Autowired
//	private ProviderSignInUtils providerSignInUtils;

	/**
	 * 当需要身份认证时，跳转到这里
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是:" + targetUrl);
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getSignInPage());
			}
		}

		return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
	}

	/**
	 * 用户第一次社交登录时，会引导用户进行用户注册或绑定，此服务用于在注册或绑定页面获取社交网站用户信息
	 * 
	 * @param request
	 * @return
	 */
//	@GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
//	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
//		Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
//		return buildSocialUserInfo(connection);
//	}

	

}
