package com.itgg.bos.web.action.system;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.system.Role;
import com.itgg.bos.domain.system.User;
import com.itgg.bos.service.system.UserService;
import com.itgg.bos.web.action.commonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:UserAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午4:04:36 <br/>       
 */
@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller
public class UserAction extends commonAction<User> {

    public UserAction() {
          
        super(User.class);  
        
    }
    
    private String validateCode;
    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }
    @Action(value="userAction_logout",results={@Result(name="success",location="/login.html",type="redirect")})
    public String logout(){
        
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        
        return SUCCESS;
    }
    
    
    @Action(value="userAction_login",results={@Result(name="success",location="/index.html",type="redirect")
    ,@Result(name="error",location="/login.html",type="redirect")})
    public String login(){
        
        HttpSession session = ServletActionContext.getRequest().getSession();
        String serverCode = (String) session.getAttribute("validateCode");
        
        if(StringUtils.isNotEmpty(serverCode)&&StringUtils.isNotEmpty(validateCode)
                &&serverCode.equals(validateCode)){
            System.out.println("session中的验证码："+serverCode);
            Subject subject=SecurityUtils.getSubject();
            
            AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), model.getPassword());
            
            try {
                subject.login(token);
                
                User user = (User) subject.getPrincipal();
                
                session.setAttribute("user", user);
                
                return SUCCESS;
            } catch (UnknownAccountException e) {
                  
                System.out.println("账户不存在");
                e.printStackTrace();  
                
            }catch (IncorrectCredentialsException e) {
                System.out.println("密码错误");
                e.printStackTrace();

            }catch (Exception e) {
                e.printStackTrace();
                System.out.println("其他异常:" + e.getMessage());

            }
        }
        
        return ERROR;
    }
    
    private Long[] roleIds;
    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
    @Autowired
    private UserService userService;
    
    @Action(value="userAction_save",results={@Result(name="success",location="/pages/system/userlist.html",type="redirect")})
    public String save(){
        
        userService.save(model,roleIds);
        
        return SUCCESS;
    }
    
    @Action("userAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(page-1, rows);
        Page<User> page=userService.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        page2json(page, jsonConfig);
        
        return NONE;
    }
    
    
}
  
