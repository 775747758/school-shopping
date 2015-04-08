package com.sxt.service;

import java.util.List;

import javax.annotation.Resource;











import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.sxt.dao.FriendDao;
import com.sxt.dao.GoodDao;
import com.sxt.dao.UserDao;
import com.sxt.po.Friend;
import com.sxt.po.Good;
import com.sxt.po.User;

@Service
public class FriendService {
	@Resource
	public FriendDao friendDao;
	
	
	
	public boolean deleteFriends(int ...id){
		return friendDao.deleteFriends(id);
	}
	
	public void insert(Friend friend){
		friendDao.insert(friend);
	}
	
	public boolean isMyFriend(int buyUid,int sellUid){
		return friendDao.isMyFriend(buyUid, sellUid);
	}
	
}
