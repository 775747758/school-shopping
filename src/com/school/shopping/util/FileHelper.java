package com.school.shopping.util;

import java.io.File;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mysql.jdbc.log.Log;

public class FileHelper {
	
	private static final Logger logger = Logger.getLogger("stdout");
	public static void uploadFile(CommonsMultipartFile file,String savePath){
		String fileName = file.getOriginalFilename();
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		System.out.println(fileType);
		File file2 = new File(savePath, fileName);
		try {
			file.getFileItem().write(file2);
			logger.info("成功上传文件:"+fileName+"到"+savePath);
		} catch (Exception e) {
			logger.info("上传文件失败:"+fileName+"到"+savePath);
		}
		
	}

}
