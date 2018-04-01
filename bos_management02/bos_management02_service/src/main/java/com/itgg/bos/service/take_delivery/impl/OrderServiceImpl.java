package com.itgg.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itgg.bos.dao.base.AreaRepository;
import com.itgg.bos.dao.base.FixedAreaRepository;
import com.itgg.bos.dao.take_delivery.OrderDao;
import com.itgg.bos.dao.take_delivery.WorkBillRepository;
import com.itgg.bos.domain.base.Area;
import com.itgg.bos.domain.base.Courier;
import com.itgg.bos.domain.base.FixedArea;
import com.itgg.bos.domain.base.SubArea;
import com.itgg.bos.domain.take_delivery.Order;
import com.itgg.bos.domain.take_delivery.WorkBill;
import com.itgg.bos.service.take_delivery.OrderService;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午4:22:46 <br/>       
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private WorkBillRepository workBillRepository;
    
    @Override
    public void save(Order order) {
        
        //将order中的area变为持久态
        Area sendArea = order.getSendArea();
        if(sendArea!=null){
            sendArea = areaRepository.findByProvinceAndCityAndDistrict(sendArea.getProvince(),
                    sendArea.getCity(), sendArea.getDistrict());
            order.setSendArea(sendArea);
        }
        
        Area recArea = order.getRecArea();
        if(recArea!=null){
            recArea = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(),
                    recArea.getCity(), recArea.getDistrict());
            order.setRecArea(recArea);
        }
        
        //保存订单
        order.setOrderNum(UUID.randomUUID().toString().replace("-", ""));
        order.setOrderTime(new Date());
        orderDao.save(order);
        
        //根据客户预留地址实现自动分单
        String sendAddress = order.getSendAddress();
        
        if(sendAddress!=null){
            
            String fixedAreaId = WebClient.create("http://localhost:8180/crm/webService/customerService/findFixedIdAreaByAddress")
            .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
            .query("address", sendAddress).get(String.class);
            
            if (StringUtils.isNotEmpty(fixedAreaId)) {
                FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));
                if (fixedArea!=null) {
                    Set<Courier> couriers = fixedArea.getCouriers();
                    if(!couriers.isEmpty()){
                        Iterator<Courier> iterator = couriers.iterator();
                        Courier courier = iterator.next();
                        //给订单分配快递员
                        order.setCourier(courier);
                        //生成工单
                        WorkBill workBill=new WorkBill();
                        workBill.setAttachbilltimes(0);
                        workBill.setBuildtime(new Date());
                        workBill.setCourier(courier);
                        workBill.setOrder(order);
                        workBill.setPickstate("新单");
                        workBill.setRemark(order.getRemark());
                        workBill.setSmsNumber("123");
                        workBill.setType("新");
                        //保存工单
                        workBillRepository.save(workBill);
                        order.setOrderType("自动分单");
                        //发送短信通知快递员取件
                        System.out.println("快递员："+courier.getName()+"请到"+order.getSendAddress()+"取件！");
                        
                        return;
                    }else{
                        System.out.println("该定区没有分配快递员！！！");
                    }
                }else{
                    System.out.println("该定区不存在！！！");
                }
            }else {
                System.out.println("没有该地址的客户信息，根据分区关键字自动分单！！！");
                Area sendArea2 = order.getSendArea();
                if(sendArea2!=null){
                    
                    Set<SubArea> subareas = sendArea2.getSubareas();
                    for (SubArea subArea : subareas) {
                        String keyWords = subArea.getKeyWords();
                        String assistKeyWords = subArea.getAssistKeyWords();
                        if(sendAddress.contains(keyWords)||sendAddress.contains(assistKeyWords)){
                            FixedArea fixedArea = subArea.getFixedArea();
                            if (fixedArea!=null) {
                                Set<Courier> couriers = fixedArea.getCouriers();
                                if(!couriers.isEmpty()){
                                    Iterator<Courier> iterator = couriers.iterator();
                                    Courier courier = iterator.next();
                                    //给订单分配快递员
                                    order.setCourier(courier);
                                    //生成工单
                                    WorkBill workBill=new WorkBill();
                                    workBill.setAttachbilltimes(0);
                                    workBill.setBuildtime(new Date());
                                    workBill.setCourier(courier);
                                    workBill.setOrder(order);
                                    workBill.setPickstate("新单");
                                    workBill.setRemark(order.getRemark());
                                    workBill.setSmsNumber("123");
                                    workBill.setType("新");
                                    //保存工单
                                    workBillRepository.save(workBill);
                                    order.setOrderType("自动分单");
                                    //发送短信通知快递员取件
                                    System.out.println("快递员："+courier.getName()+"请到"+order.getSendAddress()+"取件！");
                                    
                                    return;
                                }else{
                                    System.out.println("该定区没有分配快递员！！！");
                                }
                            }else{
                                System.out.println("该定区不存在！！！");
                            }
                            
                        }
                    }
                    
                }
            }
        }
        
        order.setOrderType("人工分单");
        
    }

}
  
