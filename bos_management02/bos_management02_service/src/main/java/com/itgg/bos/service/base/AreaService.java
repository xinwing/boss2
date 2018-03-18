package com.itgg.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itgg.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午9:59:27 <br/>       
 */
public interface AreaService {

    void save(List<Area> list);

    Page<Area> findAll(Pageable pageable);

    List<Area> findAll();

    List<Area> findQ(String q);

}
  
