package com.itgg.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itgg.bos.domain.base.Area;
import com.itgg.bos.domain.base.FixedArea;
import com.itgg.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:18:19 <br/>       
 */
public interface SubareaRepository extends JpaRepository<SubArea,Long > {
    
    @Query("from SubArea where fixedArea.id = null")
    List<SubArea> findByFixedAreaIdIsNull();
    @Query("from SubArea where fixedArea.id = ?")
    List<SubArea> findByFixedAreaId(Long fixedAreaId);
    
    List<SubArea> findByFixedArea(FixedArea fixedArea);

}
  
