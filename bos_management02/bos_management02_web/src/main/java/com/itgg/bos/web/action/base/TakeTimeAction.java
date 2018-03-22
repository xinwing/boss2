package com.itgg.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.base.TakeTime;
import com.itgg.bos.service.base.TakeTimeService;
import com.itgg.bos.web.action.commonAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午2:57:52 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends commonAction<TakeTime> {
    @Autowired
    private TakeTimeService takeTimeService;
    
    public TakeTimeAction() {
          
        super(TakeTime.class);  
        
    }

    @Action("takeTimeAction_findAll")
    public String findAll(){
        
        List<TakeTime> list=takeTimeService.findAll();
        
        list2json(list, null);
        
        return NONE;
    }
    
    
}
  
