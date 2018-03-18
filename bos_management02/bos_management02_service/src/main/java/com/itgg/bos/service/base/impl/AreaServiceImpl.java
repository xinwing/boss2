package com.itgg.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.base.AreaRepository;
import com.itgg.bos.domain.base.Area;
import com.itgg.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午10:00:04 <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;
    public void setAreaRepository(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }
    @Override
    public void save(List<Area> list) {
          
         areaRepository.save(list);
        
    }
    @Override
    public Page<Area> findAll(Pageable pageable) {
        
        return areaRepository.findAll(pageable);
    }
    @Override
    public List<Area> findAll() {
          
         
        return areaRepository.findAll();
    }
    @Override
    public List<Area> findQ(String q) {
        
        q="%"+q.toUpperCase()+"%";
          
        return areaRepository.findQ(q);
    }

}
  
