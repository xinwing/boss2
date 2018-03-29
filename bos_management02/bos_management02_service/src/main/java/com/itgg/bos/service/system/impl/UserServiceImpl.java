package com.itgg.bos.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.system.UserRepository;
import com.itgg.bos.domain.system.Role;
import com.itgg.bos.domain.system.User;
import com.itgg.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午9:22:28 <br/>       
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User model, Long[] roleIds) {
          
        userRepository.save(model);
        
        if(roleIds!=null&&roleIds.length>0){
            for (Long long1 : roleIds) {
                Role role = new Role();
                role.setId(long1);
                model.getRoles().add(role);
            }
        }
        
        
        
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
          
          
        return userRepository.findAll(pageable);
    }

}
  
