package com.sxt.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import com.sxt.po.Stall;
import com.sxt.po.User;

@Component
public class StallDao extends HibernateDaoSupport{
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	public void add(Stall s){
		this.hibernateTemplate.save(s);
	}
	
	
	
	public Stall getStallById(int id) {
		Query query = this.getSession().createQuery("from Stall where id=?");
		query.setParameter(0, id);
		List<Stall> stalls = query.list();
		if(stalls.size()>0)
		{
			return stalls.get(0);
		}
		return null;
	}
	
	public List<Stall> getStallByUid(int id) {
		Query query = this.getSession().createQuery("from Stall where uid=?");
		query.setParameter(0, id);
		List<Stall> stalls = query.list();
		return stalls;
	}
	
	public List<Stall> getStallByKeyword(String  keyword) {
		Query query = this.getSession().createQuery("from stall where theme like '%?%' or introduction like '%?%' ");
		query.setParameter(0, keyword);
		query.setParameter(1, keyword);
		List<Stall> stalls = query.list();
		return stalls;
	}
	
	public boolean deleteStall(int id){
		boolean result=true;
			Query query = this.getSession().createQuery("delete from Stall where id=?");
			query.setParameter(0, id);
			//1表示成功
			int code=query.executeUpdate();
			if(code<1){
				result=false;
			}
		return result;
	}
	
	public void updateStall(Stall stall){
		this.getSession().update(stall);
	}

}
