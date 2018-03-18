package com.itgg.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.base.SubareaRepository;
import com.itgg.bos.domain.base.SubArea;
import com.itgg.bos.service.base.SubareaService;


/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:16:11 <br/>       
 */
@Transactional
@Service
public class SubareaServiceImpl implements SubareaService{
    @Autowired
    private SubareaRepository subareaRepository;
    
    public void setSubareaRepository(SubareaRepository subareaRepository) {
        this.subareaRepository = subareaRepository;
    }

    @Override
    public void save(SubArea model) {
          
        subareaRepository.save(model); 
        
    }
    
}
  
