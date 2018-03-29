package com.itgg.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.base.WaybillRepository;
import com.itgg.bos.domain.take_delivery.WayBill;

/**  
 * ClassName:WaybillService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月25日 下午5:24:13 <br/>       
 */
@Service
@Transactional
public class WaybillService implements com.itgg.bos.service.take_delivery.WaybillService {
    @Autowired
    private WaybillRepository waybillRepository;
    
    @Override
    public void save(WayBill model) {
          
         //waybillRepository.save(model);
         waybillRepository.saveAndFlush(model);
         
    }

    @Override
    public WayBill findByWayBillNum(String wayBillNum) {
          
        return waybillRepository.findByWayBillNum(wayBillNum);
    }

    @Override
    public Page<WayBill> findAll(Pageable pageable) {
          
          
        return waybillRepository.findAll(pageable);
    }


}
  
