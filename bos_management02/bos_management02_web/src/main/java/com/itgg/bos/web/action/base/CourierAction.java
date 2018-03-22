package com.itgg.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.base.Area;
import com.itgg.bos.domain.base.Courier;
import com.itgg.bos.domain.base.Standard;
import com.itgg.bos.service.base.CourierService.CourierService;
import com.itgg.bos.web.action.commonAction;
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
public class CourierAction extends commonAction<Courier> {

    public CourierAction() {
        
        super(Courier.class);  
        
    }

    @Autowired
    private CourierService service;
    
    public void setService(CourierService service) {
        this.service = service;
    }
    
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save(){
        
        service.save(model);
        return SUCCESS;
    }
    
    
    @Action("courierAction_pageQuery")
    public String pageQuery() throws IOException{
        
       
        // 构造查询条件
        Specification<Courier> specification=new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                  
                String courierNum = model.getCourierNum();
                String company = model.getCompany();
                String type = model.getType();
                Standard standard = model.getStandard();
                
                List<Predicate> list = new ArrayList<Predicate>();
                
                if(StringUtils.isNotEmpty(courierNum)){
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                    System.err.println("courierNum");
                }
                if(StringUtils.isNotEmpty(company)){
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p2);
                    System.err.println("company");
                }
                if(StringUtils.isNotEmpty(type)){
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                    System.err.println("type");
                }
                
                if(standard!=null){
                    String name=standard.getName();
                    if(StringUtils.isNotEmpty(name)){
                         Join<Object, Object> join = root.join("standard");
                        
                        Predicate p4 = cb.equal(join.get("name").as(String.class), name);
                        list.add(p4);
                        System.err.println("name");
                    }
                }
                
                if(list.size()==0){
                    return null;
                }
                
                //System.err.println(list);
                
                Predicate[] arr=new Predicate[list.size()];
                list.toArray(arr);
                Predicate predicate = cb.and(arr);
                return predicate;
            }
            
        };

                
        
        
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Courier> page=service.findAll(specification,pageable);
        
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        
        page2json(page, jsonConfig);
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
    @Action("courierAction_listajax")
    public String listajax(){
        
        Specification<Courier> specification = new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                  
                Predicate predicate = cb.isNull(root.get("deltag").as(Character.class));
                
                return predicate;
            }};
        Page<Courier> page2 = service.findAll(specification , null);
        List<Courier> list = page2.getContent();
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"fixedAreas","takeTime"});
        
        list2json(list, jsonConfig);
        
        return NONE;
    }
    
    
    
}
  
