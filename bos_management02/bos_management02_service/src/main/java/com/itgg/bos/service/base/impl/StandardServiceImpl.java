package com.itgg.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.base.StandardDao;
import com.itgg.bos.domain.base.Standard;
import com.itgg.bos.service.base.StandardService.StandardService;

/**  
 * ClassName:StandardServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午6:00:44 <br/>       
 */
@Transactional
@Service
public class StandardServiceImpl implements StandardService {
    @Autowired
    private StandardDao dao;
    @Override
    public void save(Standard model) {

        dao.save(model);  

    }
    
    @Override
    public Page<Standard> findAll(Pageable pageable) {
          
         
        return dao.findAll(pageable);
    }

}
  
