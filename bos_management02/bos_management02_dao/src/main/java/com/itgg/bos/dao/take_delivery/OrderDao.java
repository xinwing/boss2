package com.itgg.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itgg.bos.domain.take_delivery.Order;

/**  
 * ClassName:OrderDao <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午5:49:45 <br/>       
 */
public interface OrderDao extends JpaRepository<Order, Long>{
    
}
  
