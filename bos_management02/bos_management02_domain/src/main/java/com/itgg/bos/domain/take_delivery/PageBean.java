package com.itgg.bos.domain.take_delivery;  

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
/**  
 * ClassName:PageBean <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午4:41:12 <br/>       
 */
@XmlRootElement(name="pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {
    
    private List<T> list;
    
    private long total;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    
}
  
