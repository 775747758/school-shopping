package com.sxt.service;

import java.util.logging.Logger;

import javax.annotation.Resource;









import javax.management.RuntimeErrorException;
import javax.servlet.jsp.jstl.core.Config;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.school.shopping.rongyun.Token;
import com.school.shopping.util.Constant;
import com.school.shopping.util.FileHelper;
import com.sxt.dao.UserDao;
import com.sxt.po.User;

@Service
public class UserService {
	@Resource
	public UserDao userDao;
	
	private static final Logger logger = Logger.getLogger("stdout");
	
	public void add(String uname,String password){
		System.out.println("UserService.add()");
		User u = new User();
		u.setUname(uname);
		u.setPassword(password);
		userDao.add(u);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public boolean isHave(String uname)
	{
		return userDao.isHave(uname);
	}
	
	public int getUID(String uname) {
		return userDao.getUID(uname);
	}

	public User login(String uname, String password,String deviceId) {
		User user=userDao.getUserByUnameAndPassword(uname, password);
		if(user==null){
			return null;
		}
		if(!deviceId.equals(user.getDeviceId())){
			user.setDeviceId(deviceId);
			userDao.updateUser(user);
		}
		return user;
	}
	
	public User getUserByUname(String uname){
		return userDao.getUserByUname(uname);
	}
	
	public User getUserById(int  id){
		return userDao.getUserById(id);
	}
	@Transactional
	public String  register(CommonsMultipartFile file,User user,String savePath) {
		if(file.isEmpty()){
			logger.info("文件为空");
			return null;
		}
		if(userDao.isHave(user.getUname())){
			logger.info("用户："+user.getUname()+"已经存在！");
			return null;
		}
		String token=Token.getRYToken(user.getUname(), user.getRealName());
		if(token==null||"".equals(token)){
			logger.info("没有从融云获取到token");
			return null;
		}
		userDao.add(user);
		FileHelper.uploadFile(file,savePath);
		return token;
	}
	
	public boolean checkLoginState(int id,String deviceId){
		User user=userDao.getUserById(id);
		if(user!=null){
			if(deviceId.equals(user.getDeviceId())){
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
	
}
