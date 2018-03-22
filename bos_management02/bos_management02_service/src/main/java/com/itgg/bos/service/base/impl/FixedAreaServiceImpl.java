package com.itgg.bos.service.base.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.base.AreaRepository;
import com.itgg.bos.dao.base.CourierRepository;
import com.itgg.bos.dao.base.FixedAreaRepository;
import com.itgg.bos.dao.base.SubareaRepository;
import com.itgg.bos.dao.base.TakeTimeRepository;
import com.itgg.bos.domain.base.Area;
import com.itgg.bos.domain.base.Courier;
import com.itgg.bos.domain.base.FixedArea;
import com.itgg.bos.domain.base.SubArea;
import com.itgg.bos.domain.base.TakeTime;
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
    
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Autowired
    private CourierRepository courierRepository;

    @Override
    public void associationCourierToFixedArea(Long id, Long takeTimeId, Long courierId) {
          
        TakeTime takeTime=takeTimeRepository.findOne(takeTimeId);
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Courier courier = courierRepository.findOne(courierId);
        
        courier.setTakeTime(takeTime);
        fixedArea.getCouriers().add(courier);
        
    }
    @Autowired
    private SubareaRepository subareaRepository;
    public void setSubareaRepository(SubareaRepository subareaRepository) {
        this.subareaRepository = subareaRepository;
    }
    
    @Override
    public void assignSubAreas2FixedArea(Long id, Long[] subAreaIds) {
          
        //根据fixedarea的id查找subarea表记录，并将areaid置为null
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Set<SubArea> subareas = fixedArea.getSubareas();
        for (SubArea subArea : subareas) {
            subArea.setFixedArea(null);
        }
        
        for(Long subAreaId : subAreaIds){
            SubArea subArea = subareaRepository.findOne(subAreaId);
            subArea.setFixedArea(fixedArea);
        }
        
    }

    @Override
    public void unAssignSubAreas2FixedArea(Long id) {
          
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        List<SubArea> list=subareaRepository.findByFixedArea(fixedArea);
        
        for (SubArea subArea : list) {
            subArea.setFixedArea(null);
        }
        
    }

}
  
