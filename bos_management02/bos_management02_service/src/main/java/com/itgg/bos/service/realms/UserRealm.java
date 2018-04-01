package com.itgg.bos.service.realms;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itgg.bos.dao.system.PermissionRepository;
import com.itgg.bos.dao.system.RoleRepository;
import com.itgg.bos.dao.system.UserRepository;
import com.itgg.bos.domain.system.Permission;
import com.itgg.bos.domain.system.Role;
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
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //动态授权
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        
        List<Permission> permissions=null;
        List<Role> roles=null;
        if("admin".equals(user.getUsername())){
            //给管理员授予所有权限
            permissions = permissionRepository.findAll();
            
            roles = roleRepository.findAll();
            
        }else{
            //根据用户id授权
            permissions=permissionRepository.findByUser(user.getId());
            
            roles=roleRepository.findByUser(user.getId());
        }
        //授权
        for (Permission permission : permissions) {
            info.addStringPermission(permission.getKeyword());
        }
        for (Role role : roles) {
            info.addRole(role.getKeyword());
        }
        
       /* info.addRole("admin");*/
        
        return info;
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
  
