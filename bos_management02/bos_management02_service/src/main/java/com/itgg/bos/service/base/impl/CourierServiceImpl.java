package com.itgg.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.base.CourierRepository;
import com.itgg.bos.domain.base.Courier;
import com.itgg.bos.service.base.CourierService.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午4:33:43 <br/>       
 */
@Transactional
@Service
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    
    public void setCourierRepository(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @Override
    public void save(Courier courier) {
          
        courierRepository.save(courier); 
        
    }

   

    @Override
    public void batchDel(long id) {
          
          courierRepository.batchDel(id);
        
    }

    @Override
    public Page<Courier> findAll(Specification<Courier> specification, Pageable pageable) {
          
        return courierRepository.findAll(specification, pageable);
    }
}
  
