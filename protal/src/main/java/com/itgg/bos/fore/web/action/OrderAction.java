package com.itgg.bos.fore.web.action;

import javax.ws.rs.core.MediaType;


import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.base.Area;
import com.itgg.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:OrderAction <br/>
 * Function: <br/>
 * Date: 2018年3月22日 下午4:30:18 <br/>
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    private Order model = new Order();

    @Override
    public Order getModel() {

        return model;
    }

    // 属性驱动获取收件人和发件人详细地址信息
    private String sendAreaInfo;
    private String recAreaInfo;

    public void setSendAreaInfo(String sendAreaInfo) {
        this.sendAreaInfo = sendAreaInfo;
    }

    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }

    // 保存订单
    @Action(value="orderAction_add",results={@Result(name="success",location="/index.html",type = "redirect")}
           )
    public String add() {

        // 如果发件人详细信息存在,将其转换为Area对象
        if (StringUtils.isNotEmpty(sendAreaInfo)) {
            String[] split = sendAreaInfo.split("/");
            String province=split[0];
            String city=split[1];
            String district=split[2];
            province=province.substring(0, province.length()-1);
            city=city.substring(0, city.length()-1);
            district=district.substring(0, district.length()-1);
            Area sendArea = new Area(province, city, district);
            model.setSendArea(sendArea);
        }
        // 如果收件人详细信息存在,将其转换为Area对象
        if (StringUtils.isNotEmpty(recAreaInfo)) {
            String[] split = recAreaInfo.split("/");
            String province=split[0];
            String city=split[1];
            String district=split[2];
            province=province.substring(0, province.length()-1);
            city=city.substring(0, city.length()-1);
            district=district.substring(0, district.length()-1);
            Area recArea = new Area(province, city, district);
            model.setRecArea(recArea);
        }
        
         WebClient.create("http://localhost:8080/bos_management02_web/service/orderService/save")
          .accept(MediaType.APPLICATION_JSON) .type(MediaType.APPLICATION_JSON) .post(model);
         

        return SUCCESS;
    }

    
    
}
