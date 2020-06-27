package com.ztx.securitys.core.validate.code.image;

/**
 * @program: springsecuritys
 * @description: 图像验证码
 * @author: liuhao
 * @create: 2020-06-26 11:35
 */

import com.ztx.securitys.core.validate.code.ValidateCode;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;



/**
 * 图片验证码
 * @author zhailiang
 *
 */
public class ImageCode extends ValidateCode {

    /**
     *
     */
    private static final long serialVersionUID = -6020470039852318468L;

    private BufferedImage image;

    public ImageCode(BufferedImage image, String code, int expireIn){
        super(code, expireIn);
        this.image = image;
    }

    public ImageCode(BufferedImage image, String code, LocalDateTime expireTime){
        super(code, expireTime);
        this.image = image;
    }


    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
