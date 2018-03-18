package com.itgg.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.base.FixedAreaRepository;
import com.itgg.bos.domain.base.FixedArea;
import com.itgg.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午7:48:45 <br/>       
 */
@Transactional
@Service
public class FixedAreaServiceImpl implements FixedAreaService {

    @Autowired
    private FixedAreaRepository fixedAreaRepository;    
    
    @Override
    public void save(FixedArea model) {
        System.out.println(model);
        System.out.println(model);
        System.out.println(model);
        fixedAreaRepository.save(model);
        //fixedAreaRepository.add(model);
        
    }

    @Override
    public Page<FixedArea> findAll(Pageable pageable) {
          
          
        return fixedAreaRepository.findAll(pageable);
    }

}
  
