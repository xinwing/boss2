package com.itgg.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itgg.bos.domain.system.Menu;

/**  
 * ClassName:MenuRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:43:34 <br/>       
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
    
    List<Menu> findByParentMenuIsNull();
    
}
  
