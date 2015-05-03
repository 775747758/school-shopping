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
	
	//����ֵ��-1��ʾ������
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

	public User getUserByUnameAndPassword(String uname, String password) {
		Query query = this.getSession().createQuery("from User where uname=? and password=?");
		query.setParameter(0, uname);
		query.setParameter(1, password);
		List<User> users = query.list();
		if(users.size()>0)
		{
			return users.get(0);
		}
		return null;
	}
	
	public void updateUser(User user){
		this.getSession().update(user);
	}

	
}
