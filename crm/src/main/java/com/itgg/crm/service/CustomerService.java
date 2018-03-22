package com.itgg.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.itgg.crm.domain.Customer;

/**  
 * ClassName:CustomerService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午2:44:10 <br/>       
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface CustomerService {
    @GET
    @Path("/findAll")
    List<Customer> findAll();
    @GET
    @Path("/findCustomersUnAssociated")
    List<Customer> findCustomersUnAssociated();
    
    @GET
    @Path("/findCustomersAssociated2FixedArea")
    List<Customer> findCustomersAssociated2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId);
    @PUT
    @Path("/associateCustomer2FixedArea")
    void associateCustomer2FixedArea(@QueryParam("fixedAreaId") String fixedAreaId,@QueryParam("customerIds") Long[] customerIds);
        
    @POST
    @Path("/regist")
    void regist(Customer customer);
    
    //邮箱激活
    @PUT
    @Path("/active")
    void active(@QueryParam("telephone") String telephone);
    
    @GET
    @Path("/login")
    Customer login(@QueryParam("telephone") String telephone);
        
   
}
  
