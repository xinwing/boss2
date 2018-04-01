package com.itgg.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.take_delivery.PromotionRepository;
import com.itgg.bos.domain.take_delivery.PageBean;
import com.itgg.bos.domain.take_delivery.Promotion;

/**  
 * ClassName:PromotionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午3:01:10 <br/>       
 */
@Service
@Transactional
public class PromotionServiceImpl implements com.itgg.bos.service.take_delivery.PromotionService {
    @Autowired
    private PromotionRepository promotionRepository;
    
    @Override
    public void save(Promotion promotion) {

        promotionRepository.save(promotion);
    }

    @Override
    public Page<Promotion> findAll(Pageable pageable) {
          
        return promotionRepository.findAll(pageable);
    }


    @Override
    public PageBean<Promotion> findAll4Fore(int pageIndex, int pageSize) {
        
        Pageable pageable=new PageRequest(pageIndex-1, pageSize);
         
        Page<Promotion> page = promotionRepository.findAll(pageable);
        PageBean<Promotion> pageBean = new PageBean<>();
        pageBean.setList(page.getContent());
        pageBean.setTotal(page.getTotalElements());
        
        return pageBean;
    }

}
  
