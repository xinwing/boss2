package com.itgg.bos.service.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itgg.bos.dao.system.UserRepository;
import com.itgg.bos.domain.system.User;

/**  
 * ClassName:UserRealm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午4:24:42 <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        
        info.addStringPermission("courierAction_pageQuery");
        info.addStringPermission("courier_delete");
        System.out.println("shouquan");
       /* info.addRole("admin");*/
        
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        
        UsernamePasswordToken uToken=(UsernamePasswordToken) token;
        
        String username = uToken.getUsername();
        
        User user=userRepository.findByUsername(username);
        
        if (user!=null) {
            
            AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), getName()); 
            
            return info;
        }
        
        return null;
    }

}
  
