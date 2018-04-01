package com.itgg.bos.service.system.impl;

import java.util.List;

import org.bouncycastle.jce.provider.JDKDSASigner.noneDSA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.system.MenuRepository;
import com.itgg.bos.domain.system.Menu;
import com.itgg.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午4:46:29 <br/>       
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Override
    public List<Menu> findByParentMenuIsNull() {
          
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public void save(Menu model) {
          
        if(model.getParentMenu()!=null&&model.getParentMenu().getId()==null){
            model.setParentMenu(null);
        }
        menuRepository.save(model); 
        
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
          
          
        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findByUser(Long id) {
          
          
        return menuRepository.findByUser(id);
    }
    
}
  
