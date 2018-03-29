package com.itgg.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itgg.bos.domain.system.Menu;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:45:58 <br/>       
 */
public interface MenuService {

    List<Menu> findByParentMenuIsNull();

    void save(Menu model);

    Page<Menu> findAll(Pageable pageable);
    
}
  
