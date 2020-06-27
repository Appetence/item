/**
 * 
 */
package com.ztx.securitys.core.validate.code;

import com.ztx.securitys.core.validate.code.image.ImageCode;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码生成器
 * @author zhailiang
 *
 */
public interface ValidateCodeGenerator {

	/**
	 * 生成校验码
	 * @param request
	 * @return
	 */
	ImageCode generate(ServletWebRequest request);
	
}
