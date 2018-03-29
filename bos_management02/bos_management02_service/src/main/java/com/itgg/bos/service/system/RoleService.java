package com.itgg.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itgg.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:49:13 <br/>       
 */
public interface RoleService {

    Page<Role> findAll(Pageable pageable);

    void save(Role model, String menuIds, Long[] permissionIds);

}
  
