package com.itgg.bos.web.action.take_delivery;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.take_delivery.WayBill;
import com.itgg.bos.service.take_delivery.WaybillService;
import com.itgg.bos.web.action.commonAction;
import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JsonConfig;

/**  
 * ClassName:WaybillAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午5:21:37 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class WaybillAction extends commonAction<WayBill> {
    
    
    public WaybillAction() {
          
        super(WayBill.class);  
        
    }

    @Autowired
    private WaybillService waybillService;
    
    
    @Action("wayBillAction_findAll")
    public String findAll(){
        
        Page<WayBill> page2 = waybillService.findAll(null);
        List<WayBill> list = page2.getContent();
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"order","sendArea","recArea"});
        list2json(list, jsonConfig);
        
        return NONE;
    }
    
    @Action("waybillAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(page-1, rows);
        Page<WayBill> page=waybillService.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"order","sendArea","recArea"});
        
        page2json(page, jsonConfig);
        
        return NONE;
    }
    
    
    
    @Action("waybillAction_save")
    public String save() throws IOException{
        
        String msg="1";
        
        try {
            
            WayBill wayBill=waybillService.findByWayBillNum(model.getWayBillNum());
            
            if(wayBill!=null){
                //arriveCity goodsType num weight floadreqr
                wayBill.setArriveCity(model.getArriveCity());
                wayBill.setGoodsType(model.getGoodsType());
                wayBill.setNum(model.getNum());
                wayBill.setWeight(model.getWeight());
                wayBill.setFloadreqr(model.getFloadreqr());
                wayBill.setId(wayBill.getId());
                wayBill.setDelTag(model.getDelTag());
                waybillService.save(wayBill);
                
            }else{
                waybillService.save(model);
            }
            
        } catch (Exception e) {
              
            e.printStackTrace();  
            msg="0";
        }
        
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(msg);

        
        return NONE;
    }
    
}
  
