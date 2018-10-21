package com.cui.controller;

import com.cui.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Cuijq
 */

@Controller
@RequestMapping
public class UserController {

    @RequestMapping(value="subLogin",method = RequestMethod.POST,produces = "application/json;charset=UTF-8" )
    public @ResponseBody String subLogin(User user){
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try{
            subject.login(token);
        }catch(AuthenticationException e){
            return e.getMessage();
        }
        return "登陆成功";
    }

}
