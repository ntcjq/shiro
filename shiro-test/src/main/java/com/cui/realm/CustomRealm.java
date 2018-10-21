package com.cui.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import sun.applet.Main;

import java.util.*;

/**
 * @Author Cuijq
 */
public class CustomRealm extends AuthorizingRealm {

    //用户信息
    Map<String,String> userMap = new HashMap<>();

    {
        userMap.put("cjq","d44646a1d6a571931d4191a09a4d0baa");

        //设置Realm的名字
        super.setName("customRealm");
    }

    /**
     * 获取授权信息
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String username = (String)principalCollection.getPrimaryPrincipal();
        //从数据粗或缓存中获取角色数据
        Set<String> roles =  getRolesByUsername(username);

        //从数据粗或缓存中获取权限数据
        Set<String> permissions =  getPermissionsByUsername(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setStringPermissions(permissions);
        authorizationInfo.setRoles(roles);
        return authorizationInfo;
    }



    /**
     * 获取认证信息
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //从主体传过来的认证信息中获得用户名
        String username = (String)authenticationToken.getPrincipal();

        //通过用户名取到数据库中的凭证

        String password = getPassWordByUsername(username);
        if(password == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username,password,"customRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes("RandomNum"));
        return authenticationInfo;
    }

    /**
     * 模拟数据库查询用户密码
     * @param username
     * @return
     */
    private String getPassWordByUsername(String username){

        return userMap.get(username);

    }

    /**
     * 模拟数据库查询用户角色
     * @param username
     * @return
     */
    private Set<String> getRolesByUsername(String username){
        Set<String> roleset = new HashSet<>();
        roleset.add("admin");
        roleset.add("common");
        return roleset;
    }
    /**
     * 模拟数据库查询用户权限
     * @param username
     * @return
     */
    private Set<String> getPermissionsByUsername(String username) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:add");
        permissions.add("user:delete");
        return permissions;
    }

    public static void main(String[] args) {

        Md5Hash md5 = new Md5Hash("123456","RandomNum");
        System.out.println(md5);//d44646a1d6a571931d4191a09a4d0baa
    }


}
