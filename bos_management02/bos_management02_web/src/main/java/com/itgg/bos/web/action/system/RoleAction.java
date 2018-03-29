package com.itgg.bos.web.action.system;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import com.itgg.bos.domain.system.Permission;
import com.itgg.bos.domain.system.Role;
import com.itgg.bos.service.system.RoleService;
import com.itgg.bos.web.action.commonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:RoleAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:48:08 <br/>       
 */
@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller
public class RoleAction extends commonAction<Role> {

    public RoleAction() {
          
        super(Role.class);  
        
    }
    @Autowired
    private RoleService roleService;
    
    @Action("roleAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(page-1, rows);
        Page<Role> page=roleService.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"permissions","users","menus"});
        page2json(page, jsonConfig);
        
        return NONE;
    }
    
    private String menuIds;
    private Long[] permissionIds;
    
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    
    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    @Action(value="roleAction_save",results={@Result(name="success",location="/pages/system/role.html",type="redirect")})
    public String save(){
        
            
        roleService.save(model,menuIds,permissionIds);
            
        
        return SUCCESS;
    }
    
    @Action("roleAction_findAll")
    public String findAll(){
        
        Page<Role> page=roleService.findAll(null);
        
        List<Role> list = page.getContent();
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"permissions","users","menus"});
        list2json(list, jsonConfig);
        
        return NONE;
    }
    

}
  
