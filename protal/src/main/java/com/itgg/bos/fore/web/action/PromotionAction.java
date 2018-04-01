package com.itgg.bos.fore.web.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.take_delivery.PageBean;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

/**  
 * ClassName:PromotionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午4:55:40 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class PromotionAction extends ActionSupport {
    
    private int pageSize;
    private int pageIndex;
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
    
    @Action("promotionAction_pageQuery")
    public String pageQuery() throws IOException{
        
        PageBean pageBean = WebClient.create("http://localhost:8080/bos_management02_web/service/promotionService/findAll4Fore")
        .accept(MediaType.APPLICATION_JSON)
        .type(MediaType.APPLICATION_JSON).query("pageIndex", pageIndex)
        .query("pageSize", pageSize).get(PageBean.class);
        
        JSONObject jsonObject=new JSONObject();
        String json = JSONObject.fromObject(pageBean).toString();
        
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        
        
        return NONE;
    }
    
}
  
