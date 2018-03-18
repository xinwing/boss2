package com.itgg.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itgg.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:49:21 <br/>       
 */
public interface FixedAreaRepository extends JpaRepository<FixedArea, Long> {
    /*定区编码    
    定区名称    
    负责人     
    联系电话    
    所属公司*/
    /*@Query("insert into FixedArea ")
    public void add(FixedArea model);*/
}
  
