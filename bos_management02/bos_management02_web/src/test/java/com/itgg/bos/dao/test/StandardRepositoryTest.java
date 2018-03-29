package com.itgg.bos.dao.test;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itgg.bos.dao.base.StandardRepository;
import com.itgg.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     2018年3月12日 下午4:27:22 <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
    @Autowired
    private StandardRepository standardRepository;
    
    //增
    @Test
    public void test1() {
        Standard standard = new Standard();
        standard.setName("李四");
        standard.setMaxWeight(2000);

        standardRepository.save(standard);
    }
    //查
    @Test
    public void test2() {
        List<Standard> list = standardRepository.findAll();
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    //改,一定要传入id
    @Test
    public void test3() {
        Standard standard = new Standard();
        standard.setId(2L);
        standard.setName("lisi");
        standardRepository.save(standard);
    }
    
    //删除
    @Test
    public void test4() {
       
        standardRepository.delete(2L);
    }
    
    @Test
    public void testFindByName() {
       
        List<Standard> list = standardRepository.findByName("张三三");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    @Test
    public void testFindByNameLike() {
       
        List<Standard> list = standardRepository.findByNameLike("%张%");
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    
    @Test
    public void testFindByNameAndMaxWeight321312() {
       
        List<Standard> list = standardRepository.findByNameAndMaxWeight321312("张三三", 100);
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
    @Test
    public void testUpdateMaxWeightByName() {
        
      standardRepository.updateMaxWeightByName(1000, "张三三");
    }
    
    @Test
    public void testDeleteByName() {
        
      standardRepository.deleteByName("张三三");
    }
    
    

}
  
