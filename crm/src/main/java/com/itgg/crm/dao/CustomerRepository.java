package com.itgg.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itgg.crm.domain.Customer;

/**  
 * ClassName:CustomerRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午2:55:32 <br/>       
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    List<Customer> findByFixedAreaIdIsNull();

    List<Customer> findByFixedAreaId(String fixedAreaId);
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    @Modifying
    void unbindByFixedAreaId(String fixedAreaId);
    @Query("update Customer set fixedAreaId = ?2 where id = ?1")
    @Modifying
    void bindByFixedAreaId(Long customerId, String fixedAreaId);
    @Query("update Customer set type = 1 where telephone = ?")
    @Modifying
    void active(String telephone);

    Customer findByTelephone(String telephone);
    
}
  
