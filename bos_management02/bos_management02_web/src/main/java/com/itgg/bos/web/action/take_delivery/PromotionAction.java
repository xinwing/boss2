package com.itgg.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
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

import com.itgg.bos.domain.take_delivery.Promotion;
import com.itgg.bos.service.take_delivery.PromotionService;
import com.itgg.bos.web.action.commonAction;

/**  
 * ClassName:PromotionAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月31日 下午3:05:12 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class PromotionAction extends commonAction<Promotion> {

    public PromotionAction() {
          
        super(Promotion.class);  
    }
    @Autowired
    private PromotionService promotionService;
    
    private File titleImgFile;
    private String titleImgFileFileName;
    
    public void setTitleImgFile(File titleImgFile) {
        this.titleImgFile = titleImgFile;
    }
    public void setTitleImgFileFileName(String titleImgFileFileName) {
        this.titleImgFileFileName = titleImgFileFileName;
    }
    
    @Action(value="promotionAction_save",results={@Result(name="success",location="/pages/take_delivery/promotion.html",type="redirect"),
            @Result(name = "error", location = "/error.html",
            type = "redirect")
})
    public String save(){
        
        try {
            if(titleImgFile!=null){
                
                String saveDirPath="upload";
                String saveDirRealPath=ServletActionContext.getServletContext().getRealPath(saveDirPath);
                
                String suffix=titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
                
                String fileName = UUID.randomUUID().toString()
                        .replaceAll("-", "").toUpperCase() + suffix;
                //复制文件
                FileUtils.copyFile(titleImgFile, new File(saveDirRealPath+"/"+fileName));
                
                model.setTitleImg("/upload/"+fileName);
            }
            model.setStatus("1");
            promotionService.save(model);
            return SUCCESS;
        } catch (IOException e) {
              
            e.printStackTrace();  
            
        }
        return ERROR;
    }
    @Action("promotionAction_pageQuery")
    public String pageQuery(){
        
        Pageable pageable=new PageRequest(page-1, rows);
        Page<Promotion> page=promotionService.findAll(pageable);
        
        page2json(page, null);
        return NONE;
    }
    
    
}
  
