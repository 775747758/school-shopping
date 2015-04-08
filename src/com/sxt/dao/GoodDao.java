package com.sxt.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import com.sxt.po.Good;
import com.sxt.po.User;

@Component
public class GoodDao extends HibernateDaoSupport{
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	public void addGood(Good good){
		this.hibernateTemplate.save(good);
	}
	
	public List<Good> getGoodById(int  uid) {
		Query query = this.getSession().createQuery("from Good where uid=?");
		query.setParameter(0, uid);
		List<Good> goods = query.list();
		return goods;
	}
	
	public List<Good> getGoodById(int uid,int startIndex,int lastIndex) {
		Query query = this.getSession().createQuery("from Good where uid=?");
		query.setParameter(0, uid);
		List<Good> goods = query.list();
		if(lastIndex+1>goods.size()){
			return goods.subList(startIndex, goods.size());
		}
		return goods.subList(startIndex, lastIndex+1);
	}
	
	public List<Good> getGood(int startIndex,int lastIndex) {
		Query query = this.getSession().createQuery("from Good");
		List<Good> goods = query.list();
		if(lastIndex+1>goods.size()){
			return goods.subList(startIndex, goods.size());
		}
		return goods.subList(startIndex, lastIndex+1);
	}
	
	
}
