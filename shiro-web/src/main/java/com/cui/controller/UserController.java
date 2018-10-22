package com.cui.controller;

import com.cui.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
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

    /**
     * 模拟登陆
     * @param user
     * @return
     */
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


    /**
     * 需要指定角色才能访问，否则报错
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "testRole",method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){

        return "testRole Success";
    }

    /**
     * 需要指定角色才能访问，否则报错
     * @return
     */
    @RequiresRoles("admin1")
    @RequestMapping(value = "testRole1",method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){

        return "testRole Success";
    }

    /**
     * 需要指定权限才能访问，否则报错
     * @return
     */
    @RequiresPermissions("user:add")
    @RequestMapping(value = "testP",method = RequestMethod.GET)
    @ResponseBody
    public String testp(){

        return "testP Success";
    }

    /**
     * 需要指定权限才能访问，否则报错
     * @return
     */
    @RequiresPermissions("user:query")
    @RequestMapping(value = "testP1",method = RequestMethod.GET)
    @ResponseBody
    public String testp1(){

        return "testP1 Success";
    }


    @RequestMapping(value = "testRole2",method = RequestMethod.GET)
    @ResponseBody
    public String testRole2(){

        return "testRole2 Success";
    }

    @RequestMapping(value = "testRole3",method = RequestMethod.GET)
    @ResponseBody
    public String testRole3(){

        return "testRole3 Success";
    }

    @RequestMapping(value = "testRole4",method = RequestMethod.GET)
    @ResponseBody
    public String testRole4(){

        return "testRole4 Success";
    }

    @RequestMapping(value = "testP2",method = RequestMethod.GET)
    @ResponseBody
    public String testP2(){

        return "testP2 Success";
    }

    @RequestMapping(value = "testP3",method = RequestMethod.GET)
    @ResponseBody
    public String testP3(){

        return "testP3 Success";
    }
}
