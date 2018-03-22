package com.itgg.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.service.base.TakeTimeService;
import com.itgg.bos.dao.base.TakeTimeRepository;
import com.itgg.bos.domain.base.TakeTime;
/**  
 * ClassName:TakeTimeServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午2:59:28 <br/>       
 */
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
    @Autowired
    private TakeTimeRepository takeTimeRepository;

    @Override
    public List<TakeTime> findAll() {
          
        return takeTimeRepository.findAll();
    }
    
}
  
