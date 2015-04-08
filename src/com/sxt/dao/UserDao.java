package com.sxt.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import com.sxt.po.User;

@Component
public class UserDao extends HibernateDaoSupport{
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	public void add(User u){
		System.out.println("UserDao.add()");
		this.hibernateTemplate.save(u);
	}
	
	public boolean isHave(String uname) {
		Query query = this.getSession().createQuery("from User where uname=?");
		query.setParameter(0, uname);
		List<User> users = query.list();
		if(users.size()>0)
		{
			return true;
		}
		else {
			return false;
		}
	}
	
	public User getUserByUname(String uname) {
		Query query = this.getSession().createQuery("from User where uname=?");
		query.setParameter(0, uname);
		List<User> users = query.list();
		if(users.size()>0)
		{
			return users.get(0);
		}
		return null;
	}
	
	public User getUserById(int id) {
		Query query = this.getSession().createQuery("from User where id=?");
		query.setParameter(0, id);
		List<User> users = query.list();
		if(users.size()>0) 
		{
			return users.get(0);
		}
		return null;
	}
	
	//返回值：-1表示不存在
	public int getUID(String uname) {
		Query query = this.getSession().createQuery("select id from User where uname=?");
		query.setParameter(0, uname);
		List<Integer> users = query.list();
		if(users.size()>0)
		{
			return users.get(0).intValue();
		}
		else {
			return -1;
		}
	}

	public boolean login(String uanme, String password) {
		System.out.println(uanme);
		System.out.println(password);
		Query query = this.getSession().createQuery("select password from User where uname=?");
		query.setParameter(0, uanme);
		List<String> users = query.list();
		System.out.println(users.size()+"");
		if(users.size()>0)
		{
			System.out.println("users.size()>0");
			System.out.println(users.get(0)+"");
			
			if(users.get(0).equals(password)){
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	

	
}
