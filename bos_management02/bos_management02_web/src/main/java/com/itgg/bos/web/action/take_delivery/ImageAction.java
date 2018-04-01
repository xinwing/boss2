package com.itgg.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import net.sf.json.JSONObject;

/**  
 * ClassName:ImageAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 上午10:41:54 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
@Controller
public class ImageAction extends ActionSupport{
    
    private File imgFile;
    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }
    
    private String imgFileFileName;
    public void setImgFileFileName(String imgFileFileName) {
        this.imgFileFileName = imgFileFileName;
    }
    
 // 属性驱动获取文件类型
    private String imgFileContentType;

    public void setImgFileContentType(String imgFileContentType) {
        this.imgFileContentType = imgFileContentType;
    }

    
    @Action("imageAction_upload")
    public String saveImg() throws IOException{
        System.err.println("imgFileContentType========="+imgFileContentType);
        HashMap<String,Object> map = new HashMap<>();
        
        try {
            String saveDirPath="upload";
            
            String saveDirRealPath = ServletActionContext.getServletContext().getRealPath(saveDirPath);
            System.err.println("saveDirRealPath======"+saveDirRealPath);
            
            // 获取后缀名
            String suffix =
                    imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
            // 生成随机文件名
            String fileName = UUID.randomUUID().toString().replaceAll("-", "")
                    .toUpperCase() + suffix;
            //保存文件
            FileUtils.copyFile(imgFile, new File(saveDirRealPath+"/"+fileName));
            
            //获取项目路径
            String contextPath = ServletActionContext.getServletContext().getContextPath();
            
            String relativePath =
                    contextPath + "/" + saveDirPath + "/" + fileName;
            System.err.println("relativePath=========="+relativePath);
            
            map.put("error", 0);
            map.put("url", relativePath);
        } catch (IOException e) {
              
            e.printStackTrace();  
            map.put("error", 1);
            map.put("message", e.getMessage());
        }
        
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
        
        return NONE;
        
    }
    @Action("imageAction_manager")
    public String manager() throws IOException{
        
      //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        
        String saveDirPath="upload";
        String saveDirRealPath=ServletActionContext.getServletContext().getRealPath(saveDirPath);
        
        File currentPathFile=new File(saveDirRealPath);
        
        // 图片扩展名
        String[] fileTypes = new String[] {"gif", "jpg", "jpeg", "png", "bmp"};

        
        if(currentPathFile.listFiles() != null) {
            for (File file : currentPathFile.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = file.getName();
                if(file.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (file.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if(file.isFile()){
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", file.length());
                    hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
                fileList.add(hash);
            }
        }
        
        HashMap<String, Object> map = new HashMap<>();
        // 指定所有文件的信息
        map.put("file_list", fileList);
        // 指定保存文件的文件夹的路径
        // 路径格式 : /bos_management_web/upload/
        map.put("current_url",
                ServletActionContext.getServletContext().getContextPath() + "/"
                        + saveDirPath + "/");

        // 向客户端写回数据
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

        
        return NONE;
    }
    
}
  
