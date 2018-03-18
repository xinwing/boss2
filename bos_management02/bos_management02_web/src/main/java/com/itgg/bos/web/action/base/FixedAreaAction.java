package com.itgg.bos.web.action.base;

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

import com.itgg.bos.domain.base.FixedArea;
import com.itgg.bos.service.base.FixedAreaService;
import com.itgg.bos.web.action.commonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:42:10 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class FixedAreaAction extends commonAction<FixedArea> {
    
    @Autowired
    private FixedAreaService service;
    
    public void setService(FixedAreaService service) {
        this.service = service;
    }
    public FixedAreaAction() {
          
        super(FixedArea.class);  
        
    }
    @Action(value="fixedAreaAction_save",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String save(){
        
        
        System.out.println(model.getCompany());
        System.out.println(model.getCompany());
        System.out.println(model.getCompany());
        //System.err.println(model);
        service.save(model);
        return SUCCESS;
    }
    
    @Action("fixedAreaAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(page-1, rows);
        
        Page<FixedArea> page=service.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"couriers","subareas"});
        
        page2json(page, jsonConfig);
        return NONE;
        
    }

}
  
