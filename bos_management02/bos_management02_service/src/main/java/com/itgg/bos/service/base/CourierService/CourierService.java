package com.itgg.bos.service.base.CourierService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itgg.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:58:12 <br/>       
 */
public interface CourierService {

    void save(Courier courier);


    void batchDel(long id);

    Page<Courier> findAll(Specification<Courier> specification, Pageable pageable);
    
}
  
