package com.itgg.bos.web.action.system;

import java.util.List;

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

import com.itgg.bos.domain.system.Menu;
import com.itgg.bos.domain.system.Permission;
import com.itgg.bos.domain.system.Role;
import com.itgg.bos.service.system.PermissionService;
import com.itgg.bos.web.action.commonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:PermissionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:29:00 <br/>       
 */
@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller
public class PermissionAction extends commonAction<Permission> {

    public PermissionAction() {
        super(Permission.class);  
    }
    @Autowired
    private PermissionService permissionService;
    
    @Action("permissionAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(page-1, rows);
        Page<Permission> page=permissionService.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        page2json(page, jsonConfig);
        
        return NONE;
    }
    
    
    @Action(value="permissionAction_save",results={@Result(name="success",location="/pages/system/permission.html",type="redirect")})
    public String save(){
        
        permissionService.save(model);
        
        return SUCCESS;
    }
    
    @Action("permissionAction_findAll")
    public String findAll(){
        
        Page<Permission> page2 = permissionService.findAll(null);
        List<Permission> list = page2.getContent();
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        list2json(list, jsonConfig);
        
        return NONE;
    }
    
}
  
