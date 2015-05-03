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

import com.schoolshopping.entity.GoodVo;
import com.sxt.po.Good;
import com.sxt.po.User;
import com.sxt.service.GoodService;
import com.sxt.service.UserService;

@Controller
@RequestMapping("/goods")
public class GoodController  implements ServletContextAware{
	
	private ServletContext servletContext;

	@Resource
	public GoodService goodService;
	
	@RequestMapping(value = "/add_good.do")
	@ResponseBody
	public Object addGood(Good good) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("code", "1");
		goodService.add(good);
		return map;
	}
	
	@RequestMapping(value = "/get_mygoods.do")
	@ResponseBody
	public Object get_mygoods(int uid) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		List<Good> goods=goodService.getGoodById(uid);
		map.put("code", "1");
		map.put("goods", goods);
		return map;
	}
	
	//code如果为0：是数据为空0个
	@RequestMapping(value = "/get_goods.do")
	@ResponseBody
	public Object get_goods(Integer startIndex,Integer lastIndex) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		List<Good> goods=goodService.getGood(startIndex, lastIndex);
		if(goods==null||goods.size()==0){
			map.put("code", "0");
		}
		else{
			map.put("code", "1");
		}
		map.put("goods", goods);
		return map;
	}
	
		//code如果为0：是数据为空0个
		@RequestMapping(value = "/get_goods_home.do")
		@ResponseBody
		public Object get_goods_home() {
			Map<String, Object> map = new HashMap<String, Object>(1);
			String[] typeAry={"衣服","书籍","数码","杂货铺"};
			boolean isError=false;
			for(int i=0;i<typeAry.length;i++){
				List<Good> goods=goodService.getTwoGoodByType(typeAry[i]);
				map.put(typeAry[i], goods);
				if(goods==null||goods.size()==0){
					isError=true;
				}
			}
			if(isError){
				map.put("code", "0"); 
			}
			else{
				map.put("code", "1");
			}
			return map;
		}

		@RequestMapping(value = "/get_goods_by_type.do")
		@ResponseBody
		public Object get_goods_by_type(
				@RequestParam(value = "minNewLevel", defaultValue ="0") int minNewLevel,
				@RequestParam(value = "maxNewLevel", defaultValue = "10") int maxNewLevel,
				@RequestParam(value = "type", defaultValue = "-1") int type,
				@RequestParam(value = "isAdjust", defaultValue = "-1") int isAdjust,
				@RequestParam(value = "city", defaultValue ="") String city,
				@RequestParam(value = "keyword", defaultValue ="") String keyword,
				@RequestParam(value = "startIndex", defaultValue ="0") Integer startIndex,
				@RequestParam(value = "lastIndex", defaultValue ="20") Integer lastIndex
				) {
			System.out.println("keyword11::"+keyword);
			Map<String, Object> map = new HashMap<String, Object>(1);
			List<GoodVo> goods=goodService.getGoods(type, city, isAdjust, keyword, minNewLevel, maxNewLevel,startIndex,lastIndex);
			
			if(goods==null||goods.size()==0){
				map.put("code", "0");
			}
			else{
				map.put("code", "1");
			}
			map.put("goods", goods);
			return map;
		}
		
		
		@RequestMapping(value = "/upload_goodpic.do", method = RequestMethod.POST)
		@ResponseBody
		public Object upload_portrait(@RequestParam("file") CommonsMultipartFile file) throws IOException{
			Map<String, Object> map = new HashMap<String, Object>(1);
			
			if (!file.isEmpty()) {
				   String path = "D:\\picture\\good"; 
				   System.out.println(path);
				   String fileName = file.getOriginalFilename();
				   String fileType = fileName.substring(fileName.lastIndexOf("."));
				   System.out.println(fileType); 
				   File file2 = new File(path,fileName); 
				   try {
					    file.getFileItem().write(file2); 
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
	        String fileName=new String(filename.getBytes("UTF-8"),"iso-8859-1");//Ϊ�˽�����������������  
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
	
	public GoodService getGoodService() {
		return goodService;
	}

	public void setGoodService(GoodService goodService) {
		this.goodService = goodService;
	}
	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext=arg0;
	}
	

	
}
