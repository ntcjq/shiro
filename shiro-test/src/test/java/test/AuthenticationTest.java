package test;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author Cuijq
 */
public class AuthenticationTest {


    SimpleAccountRealm realm = new SimpleAccountRealm();


    @Before
    public void addUser(){
        //添加用户 和 角色
        realm.addAccount("cjq","123456","admin");
    }


    @Test
    public void testAuthentication(){

        //构件SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);
        //主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("cjq","123456");
        //登陆
        subject.login(usernamePasswordToken);
        System.out.println(subject.isAuthenticated());

        //验证角色
        subject.checkRoles("admin");

        //登出
        subject.logout();
        System.out.println(subject.isAuthenticated());
    }

}
