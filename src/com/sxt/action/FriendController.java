package com.sxt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.sxt.dao.FriendDao;
import com.sxt.po.Friend;
import com.sxt.po.Good;
import com.sxt.po.User;
import com.sxt.service.FriendService;
import com.sxt.service.GoodService;
import com.sxt.service.UserService;

@Controller
@RequestMapping("/friends")
public class FriendController  implements ServletContextAware{
	
	private ServletContext servletContext;

	@Resource
	public FriendService friendService;
	
	//返回1删除成功，返回0删除失败
	@RequestMapping(value = "/delete_friends.do",method = RequestMethod.GET)
	@ResponseBody
	public Object deleteFriends(int[] ids) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		boolean result=friendService.deleteFriends(ids);
		if(result){
			map.put("code", "1");
		}
		else{
			map.put("code", "0");
		}
		return map;
	}
	
	@RequestMapping(value = "/add_friend.do")
	@ResponseBody
	public Object addFriend(Friend friend) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("code", "1");
		friendService.insert(friend);
		return map;
	}
	/*
	@RequestMapping(value = "/get_mygoods.do")
	@ResponseBody
	public Object get_mygoods(int uid) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		List<Good> goods=goodService.getGoodById(uid);
		map.put("code", "1");
		map.put("goods", goods);
		return map;
	}
	
	@RequestMapping(value = "/get_goods.do")
	@ResponseBody
	public Object get_goods(Integer startIndex,Integer lastIndex) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		List<Good> goods=goodService.getGood(startIndex, lastIndex);
		map.put("code", "1");
		map.put("goods", goods);
		return map;
	}
	
	
	//上传商品图片  返回值为0：不成功，返回值为1：成功
		@RequestMapping(value = "/upload_goodpic.do", method = RequestMethod.POST)
		@ResponseBody
		public Object upload_portrait(@RequestParam("file") CommonsMultipartFile file) throws IOException{
			Map<String, Object> map = new HashMap<String, Object>(1);
			
			if (!file.isEmpty()) {
				   String path = "D:\\picture\\good";  //获取本地存储路径
				   System.out.println(path);
				   String fileName = file.getOriginalFilename();
				   String fileType = fileName.substring(fileName.lastIndexOf("."));
				   System.out.println(fileType); 
				   File file2 = new File(path,fileName); //新建一个文件
				   try {
					    file.getFileItem().write(file2); //将上传的文件写入新建的文件中
				   } catch (Exception e) {
					    e.printStackTrace();
				   }
				   map.put("code", "1");
				   
				   return map;
				}else{
					map.put("code", "0");
					return map;
				}	
		}
		
		@RequestMapping("/download_goodpic.do")
	    public void download(String filename,HttpServletResponse response) throws IOException {
	    	System.out.println(filename);
	    	File file=new File("D:\\picture\\good\\"+filename);
	        HttpHeaders headers = new HttpHeaders(); 
	        String fileName=new String(filename.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
	        InputStream in = new FileInputStream(file);
	 
	        OutputStream os = response.getOutputStream();
	 
	        byte[] b = new byte[1024 * 1024];
	        int length;
	        while ((length = in.read(b)) > 0) {
	            os.write(b, 0, length);
	        }
	        in.close();
	       // return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);    
	    }
*/
	//实现上传的文件存储在本地必须实现
	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext=arg0;
	}
}
