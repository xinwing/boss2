package com.itgg.bos.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.system.MenuRepository;
import com.itgg.bos.dao.system.PermissionRepository;
import com.itgg.bos.dao.system.RoleRepository;
import com.itgg.bos.domain.system.Menu;
import com.itgg.bos.domain.system.Permission;
import com.itgg.bos.domain.system.Role;
import com.itgg.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:49:29 <br/>       
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<Role> findAll(Pageable pageable) {
          
        return roleRepository.findAll(pageable);
    }

    @Override
    public void save(Role model, String menuIds, Long[] permissionIds) {
          
          roleRepository.save(model);
          
          //方式一，使用托管态
          //method01(model, menuIds, permissionIds);
          //方式二，查询持久太对象
          method02(model, menuIds, permissionIds);
        
    }

    private void method02(Role model, String menuIds, Long[] permissionIds) {
        if (StringUtils.isNotEmpty(menuIds)) {
              String[] split = menuIds.split(",");
              for (String string : split) {
                  
                  Menu menu = menuRepository.findOne(Long.parseLong(string));
                  
                  model.getMenus().add(menu);
              }
            }
            
            if(permissionIds!=null&&permissionIds.length>0){
                for (Long long1 : permissionIds) {
                  
                  Permission permission = permissionRepository.findOne(long1);
                    
                  model.getPermissions().add(permission);
              }
            }
    }

    private void method01(Role model, String menuIds, Long[] permissionIds) {
        if (StringUtils.isNotEmpty(menuIds)) {
            String[] split = menuIds.split(",");
            for (String string : split) {
                Menu menu = new Menu();
                menu.setId(Long.parseLong(string));
                model.getMenus().add(menu);
            }
          }
          
          if(permissionIds!=null&&permissionIds.length>0){
              for (Long long1 : permissionIds) {
                Permission permission = new Permission();
                permission.setId(long1);
                model.getPermissions().add(permission);
            }
          }
    }
    
}
  
