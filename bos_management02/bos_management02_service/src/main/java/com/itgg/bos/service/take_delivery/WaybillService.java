package com.itgg.bos.service.take_delivery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itgg.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WaybillService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午5:23:55 <br/>       
 */
public interface WaybillService {

    void save(WayBill model);

    WayBill findByWayBillNum(String wayBillNum);

    Page<WayBill> findAll(Pageable pageable);


}
  
