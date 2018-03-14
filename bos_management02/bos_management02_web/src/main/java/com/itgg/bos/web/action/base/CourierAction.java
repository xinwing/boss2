package com.itgg.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.xml.resolver.helpers.PublicId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.base.Courier;
import com.itgg.bos.service.base.CourierService.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:40:46 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {

    private Courier model=new Courier();
    @Autowired
    private CourierService service;
    
    public void setService(CourierService service) {
        this.service = service;
    }
    
    @Override
    public Courier getModel() {
          
        return model;
    }
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        
        service.save(model);
        return SUCCESS;
    }
    
    private int page;
    private int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @Action("courierAction_pageQuery")
    public String pageQuery() throws IOException{
        
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Courier> page=service.findAll(pageable);
        long total = page.getTotalElements();
        List<Courier> list = page.getContent();
        Map<String, Object> map=new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas"});
        String json = JSONObject.fromObject(map, jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        return NONE;
    }
    
    private String ids;
    public void setIds(String ids) {
        this.ids = ids;
    }
    
    @Action(value="courierAction_batchDel",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String batchDel(){
        
        if(StringUtils.isNotEmpty(ids)){
            
            String[] idStrings = ids.split(",");
            for(int i=0;i<idStrings.length;i++){
                service.batchDel(Long.parseLong(idStrings[i]));
            }
        }
        return SUCCESS;
    }
    
}
  
