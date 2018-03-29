package com.itgg.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.system.PermissionRepository;
import com.itgg.bos.domain.system.Permission;
import com.itgg.bos.service.system.PermissionService;

/**  
 * ClassName:PermissionServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:30:21 <br/>       
 */
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<Permission> findAll(Pageable pageable) {
          
          
        return permissionRepository.findAll(pageable);
    }

    @Override
    public void save(Permission model) {
          
        permissionRepository.save(model);  
        
    }
    
}
  
