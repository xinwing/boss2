package com.itgg.bos.dao.base;  
import java.util.List;

/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午4:19:09 <br/>       
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.domain.base.Standard;

public interface StandardRepository extends JpaRepository<Standard, Long>{
    
    List<Standard> findByName(String name);
    
    List<Standard> findByNameLike(String name);
    
    //JPQL
    @Query("from Standard where name=? and maxWeight=?")
    List<Standard> findByNameAndMaxWeight321312(String name,Integer maxWeight);
    
    @Transactional
    @Modifying
    @Query("update Standard set maxWeight=? where name=?")
    void updateMaxWeightByName(Integer maxWeight,String name);
    
    @Transactional
    @Modifying
    @Query("delete from Standard where name=?")
    void deleteByName(String name);
    
}
  
