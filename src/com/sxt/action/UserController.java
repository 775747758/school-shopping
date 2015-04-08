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

	@RequestMapping(value = "/regster.do")
	@ResponseBody
	public Object reg(User user) {
		System.out.println("UserController.reg()");

		Map<String, Object> map = new HashMap<String, Object>(1);
		if (!userService.isHave(user.getUname())) {
			userService.add(user.getUname(), user.getPassword());
			map.put("code", "1");
			map.put("uid", userService.getUID(user.getUname()));
		} else {
			map.put("code", "0");
		}
		return map;
	}

	// ��¼ ����ֵΪ0�����ɹ�������ֵΪ1���ɹ�
	@RequestMapping(value = "/login.do")
	@ResponseBody
	public Object login(String uname, String password) {
		System.out.println("usercontroler:" + uname);
		Map<String, Object> map = new HashMap<String, Object>(1);
		if (userService.login(uname, password)) {
			map.put("code", "1");
			map.put("user", userService.getUserByUname(uname));
		} else {
			map.put("code", "0");
		}
		return map;
	}

	// �����û���ϸ��Ϣ
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

	
	/*
	 * �����û���ϸ��Ϣ   �Լ�ָ����Χ����Ʒ   �Լ��Ƿ��ע
	 * ����ֵ 0����Ʒ����Ϊ0��1��������-1��û�д��û�
	 */
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

	// �ϴ�ͼƬ ����ֵΪ0�����ɹ�������ֵΪ1���ɹ�
	@RequestMapping(value = "/upload_portrait.do", method = RequestMethod.POST)
	@ResponseBody
	public Object upload_portrait(
			@RequestParam("file") CommonsMultipartFile file) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>(1);

		if (!file.isEmpty()) {
			String path = this.servletContext
					.getRealPath("/picture/user_portrait"); // ��ȡ���ش洢·��
			System.out.println(path);
			String fileName = file.getOriginalFilename();
			String fileType = fileName.substring(fileName.lastIndexOf("."));
			System.out.println(fileType);
			File file2 = new File(path, fileName); // �½�һ���ļ�
			try {
				file.getFileItem().write(file2); // ���ϴ����ļ�д���½����ļ���
			} catch (Exception e) {
				e.printStackTrace();
			}
			map.put("code", "1");

			return map;
		} else {
			map.put("code", "0");
			return map;
		}
	}

	/*
	 * @RequestMapping("/download_portrait.do") public ResponseEntity<byte[]>
	 * download(String filename) throws IOException {
	 * System.out.println(filename); File file=new
	 * File("D:\\picture\\user_portrait\\"+filename); HttpHeaders headers = new
	 * HttpHeaders(); String fileName=new
	 * String(filename.getBytes("UTF-8"),"iso-8859-1");//Ϊ�˽������������������
	 * System.out.println(fileName);
	 * headers.setContentDispositionFormData("attachment", fileName);
	 * headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); //
	 * headers.setExpires(System.currentTimeMillis()+3600*1000);
	 * //headers.setCacheControl("public"); return new
	 * ResponseEntity<byte[]>(FileUtils
	 * .readFileToByteArray(file),headers,HttpStatus.CREATED); }
	 */

	@RequestMapping("/download_portrait.do")
	public void download(String filename, HttpServletResponse response)
			throws IOException {
		System.out.println(filename);
		File file = new File("D:\\picture\\user_portrait\\" + filename);
		HttpHeaders headers = new HttpHeaders();
		String fileName = new String(filename.getBytes("UTF-8"), "iso-8859-1");// Ϊ�˽������������������
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

	// ʵ���ϴ����ļ��洢�ڱ��ر���ʵ��
	@Override
	public void setServletContext(ServletContext arg0) {
		this.servletContext = arg0;
	}

}
