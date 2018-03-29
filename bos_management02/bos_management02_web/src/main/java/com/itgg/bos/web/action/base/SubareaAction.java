package com.itgg.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import com.itgg.bos.dao.base.AreaRepository;
import com.itgg.bos.domain.base.Area;
import com.itgg.bos.domain.base.SubArea;
import com.itgg.bos.service.base.SubareaService;
import com.itgg.bos.web.action.commonAction;

import net.sf.json.JsonConfig;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午9:12:02 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class SubareaAction extends commonAction<SubArea> {
    @Autowired
    private SubareaService subareaService;
    
    public void setSubareaService(SubareaService subareaService) {
        this.subareaService = subareaService;
    }
    public SubareaAction() {
          
        super(SubArea.class);  
        
    }
    @Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type = "redirect")})
    public String save(){
        
        subareaService.save(model);
        
        return SUCCESS;
    }
    @Action("subareaAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(page-1, rows);
        Page<SubArea> page=subareaService.findAll(pageable);
        
        JsonConfig jsonConfig=new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","fixedArea"});
        page2json(page, jsonConfig);
        return NONE;
    }
    
    @Action("subAreaAction_findSubAreaUnAssociated")
    public String findSubAreaUnAssociated(){
        
        List<SubArea> list=subareaService.findByFixedAreaIdIsNull();
        JsonConfig jsonConfig= new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","area"});
        list2json(list, jsonConfig);
        return NONE;
    }
    
    private Long fixedAreaId;
    public void setFixedAreaId(Long fixedAreaId) {
        this.fixedAreaId = fixedAreaId;
    }
    
    @Action("subAreaAction_findSubAreaAssociated2FixedArea")
    public String findSubAreaAssociated2FixedArea(){
        
        List<SubArea> list=subareaService.findByFixedAreaId(fixedAreaId);
        JsonConfig jsonConfig= new JsonConfig();
        jsonConfig.setExcludes(new String[]{"subareas","area","couriers"});
        list2json(list, jsonConfig);
        return NONE;
    }
    
    private File subAreaFile;
    
    public void setSubAreaFile(File subAreaFile) {
        this.subAreaFile = subAreaFile;
    }
    @Autowired
    private AreaRepository areaRepository;
    
    @SuppressWarnings("resource")
    @Action(value="subAreaAction_importXLS",results = {
            @Result(name = "success", location = "/pages/base/sub_area.html", type = "redirect") })
    public String importXLS() throws IOException{
        
        List<SubArea> list=new ArrayList<>();
        
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(subAreaFile));
        
        XSSFSheet sheet = workbook.getSheetAt(0);
        
        for (Row row : sheet) {
            if(row.getRowNum()==0){
                continue;
            }
            
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String keyWords = row.getCell(4).getStringCellValue();
            String startNum = row.getCell(5).getStringCellValue();
            String endNum = row.getCell(6).getStringCellValue();
            String single = row.getCell(7).getStringCellValue();
            String assistKeyWords = row.getCell(8).getStringCellValue();
            
            district=district.substring(0, district.length()-1);
            
            Area area=areaRepository.findByProvinceAndCityAndDistrict(province, city, district);
            SubArea subArea=new SubArea();
            subArea.setArea(area);
            subArea.setAssistKeyWords(assistKeyWords);
            subArea.setEndNum(endNum);
            subArea.setKeyWords(keyWords);
            subArea.setSingle(single.charAt(0));
            subArea.setStartNum(startNum);
            
            list.add(subArea);
        }
        
        subareaService.save(list);
        
        workbook.close();
        
        return SUCCESS;
    }
    
}
  
