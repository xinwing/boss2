package com.itgg.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.itgg.bos.domain.system.Role;

/**  
 * ClassName:RoleRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午5:50:09 <br/>       
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("select r from Role r inner join r.users u where u.id = ?")
    List<Role> findByUser(Long id);

}
  
