package com.yhh.xuanke.shiro;

import com.yhh.xuanke.entiy.StudentEntity;
import com.yhh.xuanke.service.StudentService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private StudentService studentService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        LOGGER.info("===>>>>>>进行了授权逻辑PrincipalCollection");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        LOGGER.info("===>>>>>>进行了认证逻辑AuthenticationToken");

        //获取token
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //检索用户
        StudentEntity student = studentService.findStudentById(Integer.valueOf(token.getUsername()));

        if (student == null){
            LOGGER.info("用户不存在");
            return null;
        }

        return new SimpleAuthenticationInfo(student, student.getPassword(),
                ByteSource.Util.bytes(student.getSalt()), "");
    }
}
