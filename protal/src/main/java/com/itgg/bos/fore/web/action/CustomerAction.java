package com.itgg.bos.fore.web.action;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;

import com.aliyuncs.exceptions.ClientException;
import com.itgg.bos.fore.domain.Customer;
import com.itgg.bos.util.MailUtils;
import com.itgg.bos.utils.SmsUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月19日 下午5:08:06 <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
    
    private Customer model=new Customer();
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Override
    public Customer getModel() {
          
        return model;
    }
    @Action("customerAction_sendSMS")
    public String sendSMS(){
        
        final String code = RandomStringUtils.randomNumeric(6);
        System.out.println("短信验证码====="+code);
        
        ServletActionContext.getRequest().getSession().setAttribute("code", code);
        
       /* try {
            SmsUtils.sendSms(model.getTelephone(), code);
        } catch (ClientException e) {
              
            e.printStackTrace();  
            
        }*/
        
        // 将原来发送短信的代码替换为以下代码
        // 参数1:消息队列的名称
        // 参数2:创建消息的对象
        jmsTemplate.send("sms_message", new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("tel", model.getTelephone());
                mapMessage.setString("code", code);
                return mapMessage;
            }
        });
        return NONE;
    }
    
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    private RedisTemplate<String, String> redisTemplate;
    
    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    //用户注册
    @Action(value="customerAction_regist",results={@Result(name="success",location="/signup-success.html",type = "redirect"),
            @Result(name="error",location="/signup-fail.html",type = "redirect")})
    public String regist(){
        
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("code");
        if(StringUtils.isNotEmpty(checkcode)&&StringUtils.isNotEmpty(code)&&code.equals(checkcode)){
            
            String activeCode=RandomStringUtils.randomNumeric(24);
            redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1, TimeUnit.DAYS);
            
            //发送激活邮件
            MailUtils.sendMail(model.getEmail(), "激活邮件", "欢迎注册速运快递!请在24小时之内点击<a href='http://localhost:8280/protal/customerAction_active.action?activeCode="+activeCode+"&telephone="+model.getTelephone()+"'>本链接</a>进行激活");
            
            WebClient.create("http://localhost:8180/crm/webService/customerService/regist")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).post(model);
            return SUCCESS;
        }else{
            return ERROR;
        }
    }
    
    private String activeCode;
    
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
    
    //激活账号
    @Action(value="customerAction_active",results={@Result(name="success",location="/login.html",type = "redirect"),
            @Result(name="error",location="/signup-fail.html",type = "redirect")})
    public String active(){
        
        String serverCode = redisTemplate.opsForValue().get(model.getTelephone());
       
        if (StringUtils.isNotEmpty(serverCode)&&StringUtils.isNotEmpty(activeCode)&&serverCode.equals(activeCode)) {
            
            WebClient.create("http://localhost:8180/crm/webService/customerService/active")
                    .type(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).query("telephone", model.getTelephone()).put(null);
            
            return SUCCESS;
        }
        
        
        return ERROR;
    }
    
    //登录
    @Action(value="customerAction_login",results={@Result(name="success",location="/index.html",type = "redirect"),
            @Result(name="error",location="/login.html",type = "redirect"),
            @Result(name="unactive",location="/signup-success.html",type = "redirect")})
    public String login(){
        
        String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        
        if (StringUtils.isNotEmpty(checkcode)&&StringUtils.
                isNotEmpty(validateCode)&&checkcode
                .equals(validateCode)) {
            
            Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/login")
            .type(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).query("telephone", model.getTelephone()).get(Customer.class);
            
            if (customer!=null) {
                if (customer.getType()!=null) {
                    //用户已经激活，验证密码是否正确
                    if (StringUtils.isNotEmpty(customer.getPassword())&&
                            StringUtils.isNotEmpty(model.getPassword())&&
                            customer.getPassword().equals(model.getPassword())) {
                        
                        ServletActionContext.getRequest().getSession().setAttribute("user", customer);
                        
                        return SUCCESS;
                    }else{
                        System.out.println("密码错误！！！！！");
                        return ERROR;
                    }
                    
                }else {
                    System.out.println("未激活！！！");
                    return "unactive";
                }
            }else {
                System.out.println("用户名不存在！！！");
            }
            
        }
        System.out.println("验证码错误！！！！");
        
        return ERROR;
    }
    
    //验证手机号
    @Action("customerAction_checkTelephone")
    public String checkTelephone() throws IOException{
        
        Customer customer = WebClient.create("http://localhost:8180/crm/webService/customerService/login")
        .type(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON).query("telephone", model.getTelephone()).get(Customer.class);
        
        PrintWriter writer = ServletActionContext.getResponse().getWriter();
        
        if(customer==null){
            writer.write("1");
        }else{
            writer.write("0");
        }
        
        return NONE;
    }
    

}
  
