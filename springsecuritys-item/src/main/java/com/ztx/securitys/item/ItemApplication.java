package com.ztx.securitys.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: springsecuritys
 * @description: 主启动类
 * @author: liuhao
 * @create: 2020-06-25 13:26
 */
@RestController
@SpringBootApplication(scanBasePackages={"com.ztx.securitys.item","com.ztx.securitys.brower","com.ztx.securitys.core"})

public class ItemApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemApplication.class,args);
    }

    @RequestMapping("/")
    public String hellow(){
        return "hellow";
    }

}

