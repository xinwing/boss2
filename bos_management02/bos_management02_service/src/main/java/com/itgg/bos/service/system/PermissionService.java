package com.itgg.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itgg.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:30:03 <br/>       
 */
public interface PermissionService {

    Page<Permission> findAll(Pageable pageable);

    void save(Permission model);

}
  
