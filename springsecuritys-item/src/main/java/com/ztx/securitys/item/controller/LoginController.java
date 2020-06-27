package com.ztx.securitys.item.controller;

import com.ztx.securitys.core.properties.SecurityConstants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: springsecuritys
 * @description: 登录处理
 * @author: liuhao
 * @create: 2020-06-26 10:35
 */
@Controller
public class LoginController {

    @RequestMapping(value = "itemSignIn")
    public String siginUp(){
        return "item-signIn";
    }
    @ResponseBody
    @RequestMapping(value = "/authentication/form")
    public String successUrl(){
        System.out.println("登录成功");
        return "登录成功";
    }
    @ResponseBody
    @RequestMapping(value = "/user/form")
    public String userInfo(){
        System.out.println("登录成功");
        return "userInfo";
    }
    @ResponseBody
    @RequestMapping(value = "/users")
    public String user(){
        System.out.println("登录成功");
        return "userInfo";
    }
}

