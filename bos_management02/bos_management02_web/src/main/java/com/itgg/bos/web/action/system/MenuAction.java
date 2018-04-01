package com.itgg.bos.web.action.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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
import com.itgg.bos.domain.system.User;
import com.itgg.bos.service.system.MenuService;
import com.itgg.bos.web.action.commonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:MenuAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:25:07 <br/>       
 */
@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller
public class MenuAction extends commonAction<Menu> {

    public MenuAction() {
          
        super(Menu.class);  
        
    }
    @Autowired
    private MenuService menuService;
    
    @Action("menuAction_findLevelOne")
    public String findLevelOne(){
        
        List<Menu> menus=menuService.findByParentMenuIsNull();
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles","parentMenu","childrenMenus"});
        list2json(menus, jsonConfig);
        
        return NONE;
    }
    
    @Action(value="menuAction_save",results={@Result(name="success",location="/pages/system/menu.html",type="redirect")})
    public String save(){
        
        menuService.save(model);
        
        return SUCCESS;
    }
    
    @Action("menuAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(Integer.parseInt(model.getPage())-1, rows);
        Page<Menu> page=menuService.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles","parentMenu","childrenMenus"});
        page2json(page, jsonConfig);
        
        return NONE;
    }
    
    @Action("menuAction_findByUser")
    public String findByUser(){
        
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Menu> list=null;
        if("admin".equals(user.getUsername())){
            Page<Menu> page2 = menuService.findAll(null);
            list = page2.getContent();
        }else{
            list=menuService.findByUser(user.getId());
        }
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"children","roles","parentMenu","childrenMenus"});
        list2json(list, jsonConfig);
        return NONE;
    }

}
  
