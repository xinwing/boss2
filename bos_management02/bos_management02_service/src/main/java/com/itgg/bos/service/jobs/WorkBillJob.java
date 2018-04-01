package com.itgg.bos.service.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itgg.bos.dao.take_delivery.WorkBillRepository;
import com.itgg.bos.domain.take_delivery.WorkBill;
import com.itgg.bos.utils.MailUtils;

/**  
 * ClassName:WorkBillJob <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午5:36:19 <br/>       
 */
@Component
public class WorkBillJob {
    @Autowired
    private WorkBillRepository workBillRepository;
    
    public void sendEmail(){
        
        String message="编号\t快递员\t取件状态\t时间<br/>";
        
        List<WorkBill> list = workBillRepository.findAll();
        for (WorkBill workBill : list) {
            message=message+workBill.getId()+"\t"+workBill.getCourier().getName()+"\t"+
                    workBill.getPickstate()+"\t"+workBill.getBuildtime().toLocaleString()+"<br/>";
        }
        
        MailUtils.sendMail("sun@store.com", "工单信息统计", message);
        System.out.println("邮件已经发送");
        
    }
}
  
