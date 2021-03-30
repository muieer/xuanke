package com.yhh.xuanke.controller;

import com.yhh.xuanke.common.CodeMsg;
import com.yhh.xuanke.entiy.UserEntity;
import com.yhh.xuanke.exception.GlobalException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @PostMapping("/do/login")
    @ResponseBody
    public String doLogin(UserEntity userEntity){

        String sno = String.valueOf(userEntity.getSno());
        String password = userEntity.getPassword();

        // 获取当前的subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(sno, password);
        // 执行登录方法，如果没有异常就说明正常
        try{
            // shiro 登录 授权 认证
            subject.login(token);
            // 放到session中
            subject.getSession().setAttribute("sno",sno);
            String msg = "success";
            LOGGER.info("do login result ==> "+msg);
            return msg;
        }catch (UnknownAccountException e) {
            LOGGER.warn("do login result ==> "+ CodeMsg.STUDENT_NUM_NOT_EXISTS.getMsg());
            throw new GlobalException(CodeMsg.STUDENT_NUM_NOT_EXISTS);
        } catch (IncorrectCredentialsException e){
            LOGGER.warn("do login result ==> "+CodeMsg.PASSWORD_WRONG.getMsg());
            throw new GlobalException(CodeMsg.PASSWORD_WRONG);
        } catch (LockedAccountException e){
            LOGGER.warn("do login result ==> "+CodeMsg.ACCOUNT_LOCKED.getMsg());
            throw new GlobalException(CodeMsg.ACCOUNT_LOCKED);
        }
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        // 移除session中的sno
        subject.getSession().removeAttribute("sno");
        // 登出
        subject.logout();
        LOGGER.info("User "+subject.getPrincipal()+" logout.");
        return "redirect:/home";
    }
}
