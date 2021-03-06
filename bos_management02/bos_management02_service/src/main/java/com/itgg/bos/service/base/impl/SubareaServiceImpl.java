package com.itgg.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<SubArea> findAll(Pageable pageable) {
          
        return subareaRepository.findAll(pageable);  
    }

    @Override
    public List<SubArea> findByFixedAreaIdIsNull() {
          
        return subareaRepository.findByFixedAreaIdIsNull();  
    }

    @Override
    public List<SubArea> findByFixedAreaId(Long fixedAreaId) {
          
          
        return subareaRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void save(List<SubArea> list) {
          
        subareaRepository.save(list);  
        
    }
    
}
  
