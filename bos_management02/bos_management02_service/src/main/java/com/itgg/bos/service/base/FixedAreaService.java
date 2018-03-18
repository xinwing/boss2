package com.itgg.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itgg.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:48:21 <br/>       
 */
public interface FixedAreaService {

    void save(FixedArea model);

    Page<FixedArea> findAll(Pageable pageable);

}
  
