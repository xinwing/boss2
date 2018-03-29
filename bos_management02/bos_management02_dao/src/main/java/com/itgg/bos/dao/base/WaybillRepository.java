package com.itgg.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itgg.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WaybillRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午5:24:54 <br/>       
 */
public interface WaybillRepository extends JpaRepository<WayBill, Long> {

    WayBill findByWayBillNum(String wayBillNum);

}
  
