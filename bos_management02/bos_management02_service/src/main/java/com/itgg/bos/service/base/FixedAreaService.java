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

    void associationCourierToFixedArea(Long id, Long takeTimeId, Long courierId);

    void assignSubAreas2FixedArea(Long id, Long[] subAreaIds);

    void unAssignSubAreas2FixedArea(Long id);

}
  
