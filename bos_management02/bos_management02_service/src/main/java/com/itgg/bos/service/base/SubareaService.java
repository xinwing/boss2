package com.itgg.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itgg.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:26:10 <br/>       
 */
public interface SubareaService {

    void save(SubArea model);

    Page<SubArea> findAll(Pageable pageable);

    List<SubArea> findByFixedAreaIdIsNull();

    List<SubArea> findByFixedAreaId(Long fixedAreaId);

    void save(List<SubArea> list);

}
  
