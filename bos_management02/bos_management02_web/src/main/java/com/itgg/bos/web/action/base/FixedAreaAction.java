package com.itgg.bos.web.action.base;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
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

import com.itgg.bos.dao.base.CourierRepository;
import com.itgg.bos.dao.base.TakeTimeRepository;
import com.itgg.bos.domain.base.FixedArea;
import com.itgg.bos.domain.base.TakeTime;
import com.itgg.bos.service.base.FixedAreaService;
import com.itgg.bos.web.action.commonAction;
import com.itgg.crm.domain.Customer;

import net.sf.json.JsonConfig;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月16日 下午7:42:10 <br/>
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

    @Action(value = "fixedAreaAction_save", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String save() {

        System.out.println(model.getCompany());
        System.out.println(model.getCompany());
        System.out.println(model.getCompany());
        // System.err.println(model);
        service.save(model);
        return SUCCESS;
    }

    @Action("fixedAreaAction_pageQuery")
    public String pageQuery() {

        Pageable pageable = new PageRequest(page - 1, rows);

        Page<FixedArea> page = service.findAll(pageable);

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"couriers", "subareas"});

        page2json(page, jsonConfig);
        return NONE;

    }

    @Action("fixedAreaAction_findCustomersUnAssociated")
    public String findCustomersUnAssociated() {

        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        list2json(list, null);
        return NONE;

    }

    @Action("fixedAreaAction_findCustomersAssociated2FixedArea")
    public String findCustomersAssociated2FixedArea() {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/crm/webService/customerService/findCustomersAssociated2FixedArea")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .query("fixedAreaId", model.getId()).getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }

    private Long[] customerIds;

    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }

    @Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String assignCustomers2FixedArea() {
        WebClient
                .create("http://localhost:8180/crm/webService/customerService/associateCustomer2FixedArea")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .query("fixedAreaId", model.getId()).query("customerIds", customerIds).put(null);
        return SUCCESS;
    }
    
    private Long courierId;
    private Long takeTimeId;
    
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    
    
    @Action(value="fixedAreaAction_associationCourierToFixedArea",results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String associationCourierToFixedArea(){
        
        service.associationCourierToFixedArea(model.getId(),takeTimeId,courierId);
       
        
        return SUCCESS;
    }
    
    private Long[] SubAreaIds;
    public void setSubAreaIds(Long[] subAreaIds) {
        SubAreaIds = subAreaIds;
    }
    
    //关联分区
    @Action(value="fixedAreaAction_assignSubAreas2FixedArea",results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String assignSubAreas2FixedArea(){
        if (SubAreaIds!=null) {
            
            service.assignSubAreas2FixedArea(model.getId(),SubAreaIds);
        }else {
            service.unAssignSubAreas2FixedArea(model.getId());
        }
        
        return SUCCESS;
    }

}
