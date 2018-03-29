package com.itgg.bos.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itgg.bos.domain.system.User;

/**  
 * ClassName:UserRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午4:27:27 <br/>       
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
    
    
}
  
