package com.itgg.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itgg.bos.domain.base.Area;
import com.itgg.bos.service.base.AreaService;
import com.itgg.bos.utils.PinYin4jUtils;
import com.itgg.bos.web.action.commonAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午9:08:03 <br/>       
 */
@Namespace("/") 
@ParentPackage("struts-default") 
@Scope("prototype") 
@Controller
public class AreaAction extends commonAction<Area>{
    


    public AreaAction() {
          
        super(Area.class);  
        
    }

    @Autowired
    private AreaService areaService;
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }
   
   
    
    // 使用属性驱动,获取用户传递的文件
    private File File;
    public void setFile(File File) {
        this.File = File;
    }
    
    

    @Action(value="areaAction_importXLS",results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
    public String importXLS() throws Exception {
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(File));
        
        HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
        List<Area> list=new ArrayList<>();
        for (Row row : sheet) {
            if (row.getRowNum()==0) {
                continue;
            }
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
            
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);
            
            String citycode = PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
            String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
            String shortcode = PinYin4jUtils.stringArrayToString(headByString);
            
            Area area = new Area();
            //area.setId(null);
            area.setProvince(province);
            area.setCity(city);
            area.setCitycode(citycode);
            area.setDistrict(district);
            area.setPostcode(postcode);
            area.setCitycode(citycode);
            area.setShortcode(shortcode);
            
            list.add(area);
            
        }
        areaService.save(list);
        
        
        hssfWorkbook.close();
        return SUCCESS;
    }
    
    
    
    
    @Action("areaAction_pageQuery")
    public String pageQuery() throws IOException{
        
        Pageable pageable=new PageRequest(page-1, rows);
        Page<Area> page=areaService.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas"});
        
        page2json(page, jsonConfig);
        return NONE;
    }

}
  
