package com.yuanlrc.base.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.Result;
import com.yuanlrc.base.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * 公用的上传类
 * @author Administrator
 *
 */
@RequestMapping("/upload")
@Controller
public class UploadController {

	@Value("${ylrc.upload.photo.sufix}")
	private String uploadPhotoSufix;
	
	@Value("${ylrc.upload.photo.maxsize}")
	private long uploadPhotoMaxSize;
	
	@Value("${ylrc.upload.photo.path}")
	private String uploadPhotoPath;//文件保存位置
	
	private Logger log = LoggerFactory.getLogger(UploadController.class);
	
	/**
	 * 图片统一上传类
	 * @param photo
	 * @return
	 */
	@RequestMapping(value="/upload_photo",method=RequestMethod.POST)
	@ResponseBody
	public Result<String> uploadPhoto(@RequestParam(name="photo",required=true)MultipartFile photo){
		Result<String> result = uploadPhotoMultipartFile(photo);
		return  result;
	}
	/**
	 * 多图片上传
	 * @param photo
	 * @return
	 */
	@RequestMapping(value="/home_upload_photo",method=RequestMethod.POST)
	@ResponseBody
	public Result<String> homeUploadPhoto(@RequestParam(name="file")MultipartFile photo){
		Result<String> result = uploadPhotoMultipartFile(photo);
		return  result;
	}

    /**
     * 富文本
     * @param photo
     * @return
     */
    @RequestMapping(value="/editor_upload",method=RequestMethod.POST)
    @ResponseBody
    public String editorUpload(@RequestParam(name="file")MultipartFile photo){

        JSONObject jreq = new JSONObject();
        jreq.put("code", 0);
        jreq.put("msg", "");
        JSONObject data = new JSONObject();
        jreq.put("data", data);

        //判断文件类型是否是图片
        String originalFilename = photo.getOriginalFilename();
        //获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());
        if(!uploadPhotoSufix.contains(suffix.toLowerCase())){
            jreq.put("code", -1);
            jreq.put("msg", "图片格式不正确");
        }
        if(photo.getSize()/1024 > uploadPhotoMaxSize){
            jreq.put("code", -1);
            jreq.put("msg", "图片太大了");
        }
        //准备保存文件
        File filePath = new File(uploadPhotoPath);
        if(!filePath.exists()){
            //若不存在文件夹，则创建一个文件夹
            filePath.mkdir();
        }
        //判断当天日期的文件夹是否存在，若不存在，则创建
        if(!filePath.exists()){
            //若不存在文件夹，则创建一个文件夹
            filePath.mkdir();
        }
        String filename = StringUtil.getFormatterDate(new Date(), "yyyyMMdd") + "/" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString() + suffix;
        try {
            new File(uploadPhotoPath+"/"+filename).getParentFile().mkdirs();
            photo.transferTo(new File(uploadPhotoPath+"/"+filename));
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        data.put("src", "/photo/view?filename=" + filename);
        log.info("图片上传成功，保存位置：" + uploadPhotoPath + filename);
        return jreq.toString();
    }


    public Result<String> uploadPhotoMultipartFile(MultipartFile photo){
		//判断文件类型是否是图片
		String originalFilename = photo.getOriginalFilename();
		//获取文件后缀
		String suffix = originalFilename.substring(originalFilename.lastIndexOf("."),originalFilename.length());
		if(!uploadPhotoSufix.contains(suffix.toLowerCase())){
			return Result.error(CodeMsg.UPLOAD_PHOTO_SUFFIX_ERROR);
		}
		if(photo.getSize()/1024 > uploadPhotoMaxSize){
			CodeMsg codeMsg = CodeMsg.UPLOAD_PHOTO_ERROR;
			codeMsg.setMsg("图片大小不能超过" + (uploadPhotoMaxSize/1024) + "M");
			return Result.error(codeMsg);
		}
		//准备保存文件
		File filePath = new File(uploadPhotoPath);
		if(!filePath.exists()){
			//若不存在文件夹，则创建一个文件夹
			filePath.mkdir();
		}
		//判断当天日期的文件夹是否存在，若不存在，则创建
		if(!filePath.exists()){
			//若不存在文件夹，则创建一个文件夹
			filePath.mkdir();
		}
		String filename = StringUtil.getFormatterDate(new Date(), "yyyyMMdd") + "/" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString() + suffix;
		try {
			new File(uploadPhotoPath+"/"+filename).getParentFile().mkdirs();
			photo.transferTo(new File(uploadPhotoPath+"/"+filename));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("图片上传成功，保存位置：" + uploadPhotoPath + filename);
		return Result.success(filename);
	}
}
