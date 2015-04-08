package com.sxt.dao;

import java.util.List;

import javax.annotation.Resource;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.schoolshopping.entity.FriendVo;
import com.sxt.po.Friend;
import com.sxt.po.Good;
import com.sxt.po.User;

@Component
public class FriendDao extends HibernateDaoSupport{
	@Resource
	private HibernateTemplate hibernateTemplate;
	
	@Resource
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		this.setSessionFactory(sessionFactory);
	}
	
	public void insert(Friend friend){
		this.hibernateTemplate.save(friend);
	}
	
	public List<FriendVo> getFriendVo(int  buyId) {
		Query query = this.getSession().createQuery("select realName,gender from friend,user where friend.sellUid=user.id and friend.buyUid=?");
		query.setParameter(0, buyId);
		List<FriendVo> friends = query.list();
		return friends;
		
	}
	public boolean deleteFriends(int ...id){
		boolean result=true;
		for(int i=0;i<id.length;i++){
			Query query = this.getSession().createQuery("delete from Friend where id=?");
			query.setParameter(0, id[i]);
			//1表示成功
			int code=query.executeUpdate();
			if(code<1){
				result=false;
			}
		}
		return result;
	}
	
	public boolean isMyFriend(int buyUid,int sellUid){
		Query query = this.getSession().createQuery("from Friend where buyUid=? and sellUid=?");
		query.setParameter(0, buyUid);
		query.setParameter(1, sellUid);
		List<Friend> users = query.list();
		if(users.size()>0)
		{
			return true;
		}
		else {
			return false;
		}
	}
	
}
