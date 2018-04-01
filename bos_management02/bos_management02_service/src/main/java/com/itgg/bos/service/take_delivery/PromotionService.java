package com.itgg.bos.service.take_delivery;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.itgg.bos.domain.take_delivery.PageBean;
import com.itgg.bos.domain.take_delivery.Promotion;

/**  
 * ClassName:PromotionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午3:00:37 <br/>       
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PromotionService {
    
    void save(Promotion promotion);

    Page<Promotion> findAll(Pageable pageable);
    @GET
    @Path("/findAll4Fore")
    PageBean<Promotion> findAll4Fore(@QueryParam("pageIndex") int pageIndex,@QueryParam("pageSize") int pageSize);
    
}
  
