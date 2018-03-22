package com.itgg.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;

import com.itgg.bos.domain.base.Area;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:commonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午5:17:37 <br/>       
 */
public class commonAction<T> extends ActionSupport implements ModelDriven<T> {
    
    protected T model;
    private Class<T> clazz;
    
    public commonAction(Class<T> clazz) {
        this.clazz=clazz;
        try {
            model=this.clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
              
            e.printStackTrace();  
            
        }
    }
    
    @Override
    public T getModel() {
          
        return model;
    }
    
    protected int page;
    protected int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    
    public void page2json(Page<T> page,JsonConfig jsonConfig){
        long total = page.getTotalElements();
        List<T> list = page.getContent();
        Map<String, Object> map=new HashMap<>();
        map.put("total", total);
        map.put("rows", list);
        String json;
        if(jsonConfig==null){
            json = JSONObject.fromObject(map).toString();
        }else{
            json = JSONObject.fromObject(map,jsonConfig).toString();
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
              
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            
        }
    }
    
    
    public void list2json(List list,JsonConfig jsonConfig){
       
        String json;
        if(jsonConfig==null){
            json = JSONArray.fromObject(list).toString();
        }else{
            json = JSONArray.fromObject(list,jsonConfig).toString();
        }
        //System.err.println(json);
        HttpServletResponse response = ServletActionContext.getResponse();
        
        response.setContentType("application/json;charset=utf-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
              
            // TODO Auto-generated catch block  
            e.printStackTrace();  
            
        }
    }

}
  
