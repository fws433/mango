package com.louis.mango.admin.controller;

import com.github.pagehelper.Constant;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import com.louis.mango.admin.model.SysUser;
import com.louis.mango.admin.security.JwtAuthenticatioToken;
import com.louis.mango.admin.service.SysUserService;
import com.louis.mango.admin.util.PasswordUtils;
import com.louis.mango.admin.util.SecurityUtils;
import com.louis.mango.admin.vo.LoginBean;
import com.louis.mangocore.http.HttpResult;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
@RestController
public class SysLoginController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    public Producer producer;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request)throws IOException {
        response.setHeader("Cache-Control","no-store,no-cache");
        response.setContentType("image/jpeg");
        String text=producer.createText();
        BufferedImage image=producer.createImage(text);
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,text);
        ServletOutputStream out=response.getOutputStream();
        ImageIO.write(image,"jpg",out);
        IOUtils.closeQuietly(out);
    }
    @PostMapping(value = "/login")
    public HttpResult login(@RequestBody LoginBean loginBean, HttpServletRequest request) {
        String username=loginBean.getAccount();
        String password=loginBean.getPassword();
        String captcha=loginBean.getCaptcha();
        Object kaptcha=request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if(kaptcha==null){
            return HttpResult.error("验证码已失效");
        }
        if(!captcha.equals(kaptcha)){
            return HttpResult.error("验证码不正确");
        }
        SysUser user=sysUserService.findByName(username);
        if(user==null){
            return HttpResult.error("账号不存在");
        }
        if(!PasswordUtils.matches(user.getSalt(),password,user.getPassword())){
            return HttpResult.error("密码不正确");
        }
        if(user.getStatus()==0){
            return HttpResult.error("账号已被锁定，请联系管理员");
        }
        //AuthenticationManager authenticationManager = null;
        JwtAuthenticatioToken token= SecurityUtils.login(request,username,password, authenticationManager);
        return HttpResult.ok(token);
    }
}
