package test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author Cuijq
 */
public class JdbcRealmTest {


    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://47.99.36.32:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("saopigying2018");
    }

    @Test
    public void testAuthentication(){


        JdbcRealm realm = new JdbcRealm();
        realm.setDataSource(dataSource);
        //JdbcRealm需要开启权限查询，默认false
        realm.setPermissionsLookupEnabled(true);

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

        //验证权限
        subject.checkPermissions("user:delete");


        //登出
        subject.logout();
        System.out.println(subject.isAuthenticated());
    }
}
