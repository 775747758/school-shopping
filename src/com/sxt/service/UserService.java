package com.sxt.service;

import javax.annotation.Resource;






import org.springframework.stereotype.Service;

import com.sxt.dao.UserDao;
import com.sxt.po.User;

@Service
public class UserService {
	@Resource
	public UserDao userDao;
	
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

	public boolean login(String uanme, String password) {
		System.out.println("service:"+uanme);
		return userDao.login(uanme, password);
	}
	
	public User getUserByUname(String uname){
		return userDao.getUserByUname(uname);
	}
	
	public User getUserById(int  id){
		return userDao.getUserById(id);
	}
	
	
}
