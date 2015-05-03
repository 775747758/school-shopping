package com.sxt.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;

import com.schoolshopping.entity.GoodVo;
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
		Query query = this.getSession().createQuery("from Good order by id desc");
		List<Good> goods = query.list();
		if(lastIndex+1>goods.size()){
			return goods.subList(startIndex, goods.size());
		}
		return goods.subList(startIndex, lastIndex+1);
	}
	
	public List<Good> getTwoGoodByType(String type) {
		Query query = this.getSession().createQuery("from Good  where type=? order by id desc");
		query.setParameter(0, type);
		List<Good> goods = query.list();
		if(goods.size()>=2){
			return goods.subList(0, 2);
		}
		else{
			return goods.subList(0, goods.size());
		}
		
	}
	
	private final static String sql_getGoods="select distinct new com.schoolshopping.entity.GoodVo(g.id,g.goodName,g.type,g.price,g.newLevel,g.isAdjust,g.introduction,g.uid, u.longitude,u.latitude,u.uname ) from Good as g,User as u ";
	
	public List<GoodVo> getGoods(int type,String city,int isAdjust,String keyword,int minNewLevel,int maxNewLevel, int startIndex, int lastIndex) {
		
		StringBuilder stringBuilder=new StringBuilder(512);
		stringBuilder.append(sql_getGoods);
		stringBuilder.append(" where ").append("newLevel>=").append(minNewLevel).append(" and ").append("newLevel<=").append(maxNewLevel);
		if(type!=-1){
			stringBuilder.append(" and ").append("type=").append(type);
		}
		if(city!=null&&!"".equals(city)){
			stringBuilder.append(" and g.uid=u.id and u.city=").append(city);
		}
		if(isAdjust!=-1){
			stringBuilder.append(" and isAdjust=").append(isAdjust);
		}
		System.out.println("keyword::"+keyword);
		if(keyword!=null&&!"".equals(keyword)){
			stringBuilder.append(" and g.goodName like '%").append(keyword).append("%' or g.introduction like '%").append(keyword).append("%'");
		}
		stringBuilder.append(" order by g.id desc");
		System.out.println(stringBuilder.toString());
		Query query = this.getSession().createQuery(stringBuilder.toString());
		if(startIndex>=0&&lastIndex>=0&&lastIndex>startIndex){
			query.setFirstResult(startIndex);
			query.setMaxResults(lastIndex);
		}
		else{
			query.setFirstResult(0);
			query.setMaxResults(20);
		}
		List<GoodVo> list=query.list();
		return list;
		
	}
	
	
}
