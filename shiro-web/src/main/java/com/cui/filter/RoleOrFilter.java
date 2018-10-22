package com.cui.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @Author: cuijq
 */
public class RoleOrFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse ServletRequest, Object o) throws Exception {
        Subject subject = getSubject(servletRequest,ServletRequest);
        //例：/testRole4 = rolesOr["admin","admin1"]
        //此处roles就是上面的数组
        String[] roles = (String[])o;
        //如果为空 都可以访问
        if(roles == null || roles.length == 0){
            return true;
        }
        //如果不为空  当前主体有一个角色 即可访问
        for (String role : roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
