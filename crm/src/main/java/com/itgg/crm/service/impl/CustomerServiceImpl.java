package com.itgg.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.crm.dao.CustomerRepository;
import com.itgg.crm.domain.Customer;
import com.itgg.crm.service.CustomerService;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月18日 下午2:44:48 <br/>       
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public List<Customer> findAll() {
          
        return customerRepository.findAll();
    }

    @Override
    public List<Customer> findCustomersUnAssociated() {
          
          
        return customerRepository.findByFixedAreaIdIsNull();
    }

    @Override
    public List<Customer> findCustomersAssociated2FixedArea(String fixedAreaId) {
          
          
        return customerRepository.findByFixedAreaId(fixedAreaId);
    }

    @Override
    public void associateCustomer2FixedArea(String fixedAreaId, Long[] customerIds) {
          
        if(StringUtils.isNotEmpty(fixedAreaId)){
            customerRepository.unbindByFixedAreaId(fixedAreaId);
            if(customerIds!=null&&customerIds.length>0){
                for (Long customerId : customerIds) {
                    customerRepository.bindByFixedAreaId(customerId,fixedAreaId);
                }
            }
        }
        
    }

    @Override
    public void regist(Customer customer) {
          
        customerRepository.save(customer);  
        
    }

    @Override
    public void active(String telephone) {
        System.err.println("active");
        System.err.println("active");
        customerRepository.active(telephone);  
    }

    @Override
    public Customer login(String telephone) {
          
        return customerRepository.findByTelephone(telephone);
    }

   

}
  
