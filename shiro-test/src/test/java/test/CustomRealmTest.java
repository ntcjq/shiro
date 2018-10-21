package test;

import com.cui.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author Cuijq
 */
public class CustomRealmTest {


    @Test
    public void testAuthentication(){


        CustomRealm realm = new CustomRealm();

        //构件SecurityManager环境
        DefaultSecurityManager securityManager = new DefaultSecurityManager();
        securityManager.setRealm(realm);


        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密方式
        credentialsMatcher.setHashAlgorithmName("md5");
        //设置加密次数
        credentialsMatcher.setHashIterations(1);
        realm.setCredentialsMatcher(credentialsMatcher);

        //主体提交认证请求
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("cjq","123456");
        //登陆
        subject.login(usernamePasswordToken);
        System.out.println(subject.isAuthenticated());

        //验证角色
        subject.checkRoles("admin");

        //验证权限
        subject.checkPermissions("user:delete");


        //登出
        subject.logout();
        System.out.println(subject.isAuthenticated());
    }
}
