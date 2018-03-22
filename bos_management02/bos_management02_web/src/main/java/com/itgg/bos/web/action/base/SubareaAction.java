package com.itgg.bos.web.action.base;

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

import com.itgg.bos.domain.base.SubArea;
import com.itgg.bos.service.base.SubareaService;
import com.itgg.bos.web.action.commonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:12:02 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class SubareaAction extends commonAction<SubArea> {
    @Autowired
    private SubareaService subareaService;
    
    public void setSubareaService(SubareaService subareaService) {
        this.subareaService = subareaService;
    }
    public SubareaAction() {
          
        super(SubArea.class);  
        
    }
    @Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type = "redirect")})
    public String save(){
        
        subareaService.save(model);
        
        return SUCCESS;
    }
    @Action("subareaAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(page-1, rows);
        Page<SubArea> page=subareaService.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","fixedArea"});
        page2json(page, jsonConfig);
        return NONE;
    }
    
    @Action("subAreaAction_findSubAreaUnAssociated")
    public String findSubAreaUnAssociated(){
        
        List<SubArea> list=subareaService.findByFixedAreaIdIsNull();
        JsonConfig jsonConfig= new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","area"});
        list2json(list, jsonConfig);
        return NONE;
    }
    
    private Long fixedAreaId;
    public void setFixedAreaId(Long fixedAreaId) {
        this.fixedAreaId = fixedAreaId;
    }
    
    @Action("subAreaAction_findSubAreaAssociated2FixedArea")
    public String findSubAreaAssociated2FixedArea(){
        
        List<SubArea> list=subareaService.findByFixedAreaId(fixedAreaId);
        JsonConfig jsonConfig= new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","area","couriers"});
        list2json(list, jsonConfig);
        return NONE;
    }
    
}
  
