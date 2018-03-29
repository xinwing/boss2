package com.itgg.bos.util;  

import java.util.Properties;  
import java.util.UUID;  
  
import javax.mail.Authenticator;  
import javax.mail.Message;  
import javax.mail.Message.RecipientType;  
import javax.mail.PasswordAuthentication;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;  
  
import com.sun.mail.util.MailSSLSocketFactory;
/**  
 * 注意：
    1.运行此java application例子，需要联网，因为我们需要连接比如QQ邮箱的smtp服务器
    
    2.若使用QQ邮箱，则发送方需要到QQ邮箱网页开启SMTP服务器，通过验证后获取授权码，
    
    比如menfeesxhkyebdgd，不再需要邮箱本身的密码
 * ClassName:MyMailUtils <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午7:45:26 <br/>       
 */
public class MyMailUtils {
    
    /** 
     * @param toAddr：接收方邮箱 
     * @param code：使用UUID产生的随机激活码 
     */  
    public static void sendEmail(String toAddr, String code) throws Exception {  
        final String fromAddr = "642110754@qq.com"; // 发送方邮箱  
        final String licenseCode = "ubhmefctwvitbcga"; // QQ邮箱的授权码  
        // 1.创建属性对象,本质就是属性文件的key-value  
        Properties properties = new Properties();  
        properties.put("mail.smtp.host", "smtp.qq.com"); // 设置邮件服务器  
        properties.put("mail.smtp.auth", "true"); // 打开认证  
  
        // QQ邮箱需要下面这段代码，使用SSL加密  
        MailSSLSocketFactory sf = new MailSSLSocketFactory();  
        sf.setTrustAllHosts(true);  
        properties.put("mail.smtp.ssl.enable", "true");  
        properties.put("mail.smtp.ssl.socketFactory", sf);  
  
        // 2.由发送者发起会话,并创建认证（发送者邮箱的帐号密码）  
        Session session = Session.getDefaultInstance(properties, new Authenticator() {  
            public PasswordAuthentication getPasswordAuthentication() {  
                return new PasswordAuthentication(fromAddr, licenseCode);  
            }  
        });  
        // 3.创建邮件消息  
        Message message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(fromAddr));  
        message.addRecipient(RecipientType.TO, new InternetAddress(toAddr));  
        message.setSubject("来自XXX网站的帐号激活");  
        String content = "<h1>帐号激活</h1><h3>请点击此链接完成帐号激活<a href='http://localhost:8080/miniBBS/activeUser?code=" + code  
                        + "'>http://localhost:8080/miniBBS/activeUser?code=" + code + "</a></h3>";  
        message.setContent(content, "text/html;charset=UTF-8");  
        //message.setText("https://www.baidu.com"); // 纯字符串文本  
        // 4.发送邮件  
        Transport.send(message);  
    }  
      
    /** 
     * @return UUID生成的随机32位激活码 
     */  
    public static String getCode() {  
        return UUID.randomUUID().toString().replace("-", "");  
    }  
  
    public static void main(String[] args) throws Exception {  
        // System.out.println(UUID.randomUUID().toString().replace("-", ""));  
        sendEmail("3126747716@qq.com", getCode());  
    }  

}
  
