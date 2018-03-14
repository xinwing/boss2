package com.itgg.bos.service.base.StandardService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itgg.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午5:58:58 <br/>       
 */
public interface StandardService {

    void save(Standard model);


    Page<Standard> findAll(Pageable pageable);

}
  
