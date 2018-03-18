package com.itgg.bos.web.action.base;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.base.SubArea;
import com.itgg.bos.service.base.SubareaService;
import com.itgg.bos.web.action.commonAction;

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
    
    
}
  
