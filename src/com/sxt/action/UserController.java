package com.sxt.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.school.shopping.rongyun.Token;
import com.school.shopping.util.Constant;
import com.school.shopping.util.FileHelper;
import com.sxt.po.Good;
import com.sxt.po.User;
import com.sxt.service.FriendService;
import com.sxt.service.GoodService;
import com.sxt.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController implements ServletContextAware {

	private ServletContext servletContext;

	@Resource
	public UserService userService;
	
	@Resource
	public GoodService goodService;
	
	@Resource
	public FriendService friendService;

	private static final Logger logger = Logger.getLogger("stdout");


	//code=1:成功；code=0：用户名或密码错误;
	@RequestMapping(value = "/login.do",method = RequestMethod.POST)
	@ResponseBody
	public Object login(String uname, String password,String deviceId) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		User user=userService.login(uname, password,deviceId);
		if(user==null){
			map.put("code", "0");
			logger.info("用户名或密码错误");
			return map;
		}
		String token=Token.getRYToken(uname, user.getRealName());
		if(token!=null&&!"".equals(token)&&user!=null){
			map.put("code", "1");
			map.put("user", user);
			map.put("token", token);
			logger.info("登陆成功");
			return map;
		}
		else{
			logger.info("没有从融云获取到token");
			map.put("code", "0");
		}
		return map;
	}
	
	
	@RequestMapping(value = "/checkLoginState.do",method = RequestMethod.POST)
	@ResponseBody
	public Object checkLoginState(int id,String deviceId) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		if(userService.checkLoginState(id, deviceId)){
			map.put("code", "1");
			logger.info("验证通过");
		}
		else{
			logger.info("验证失败，已经在其他设备登录");
			map.put("code", "0");
		}
		return map;
	}
	
	@RequestMapping(value = "/gettoken.do")
	@ResponseBody
	public Object getToken(int id) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		User user=userService.getUserById(id);
		String tokenString=Token.getRYToken(user.getUname(), user.getRealName());
		if(tokenString!=null){
			map.put("token", Token.getRYToken(user.getUname(), user.getRealName()));
			map.put("code", "1");
		}
		else {
			map.put("code", "0");
		}
		return map;
	}

	@RequestMapping(value = "/user_info.do")
	@ResponseBody
	public Object user_info(String uname) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		User user = userService.getUserByUname(uname);
		if (user != null) {
			map.put("realName", user.getRealName());
			map.put("gender", user.getGender());
			map.put("qq", user.getQq());
			map.put("phone", user.getPhone());
			map.put("school", user.getSchool());
			return map;
		}

		return null;
	}

	
	@RequestMapping(value = "/get_oneinfo.do")
	@ResponseBody
	public Object getOneInfo(int myId,int id,int startIndex,int lastIndex) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		User user = userService.getUserById(id);
		List<Good> goods=goodService.getGood(id, startIndex, lastIndex);
		if(user!=null){
			if(goods==null||goods.size()==0){
				map.put("code", "0");
			}
			else{
				map.put("code", "1");
			}
			
		}
		else{
			map.put("code", "-1");
		}
		map.put("user", user);
		map.put("goods", goods);
		if(friendService.isMyFriend(myId, id)){
			map.put("isFriend", "1");
		}
		else{
			map.put("isFriend", "0");
		}
		return map;
	}

	@RequestMapping(value = "/regster.do", method = RequestMethod.POST)
	@ResponseBody
	public Object regster(
			@RequestParam("file") CommonsMultipartFile file,User user) throws IOException {

		Map<String, Object> map = new HashMap<String, Object>(1);
		String token=userService.register(file, user, Constant.PATH_USER_PORTRAIT);
		if(token!=null){
			int uid=userService.getUID(user.getUname());
			map.put("code", "1");
			map.put("uid", uid);
			map.put("token", token);
			logger.info("注册成功  "+"uid: "+uid);
			return map;
		}
		else {
			logger.info("注册失败  ");
			map.put("code", "0");
			return map;
		}
	}
	@RequestMapping("/download_portrait.do")
	public void download(String filename, HttpServletResponse response)
			throws IOException {
		System.out.println(filename);
		File file = new File("D:\\picture\\user_portrait\\" + filename);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");// Ϊ�˽�����������������
		InputStream in = new FileInputStream(file);

		OutputStream os = response.getOutputStream();

		byte[] b = new byte[1024 * 1024];
		int length;
		while ((length = in.read(b)) > 0) {
			os.write(b, 0, length);
		}
		in.close();
		// return new
		// ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}

}
