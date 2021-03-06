package com.sxt.service;

import java.util.List;

import javax.annotation.Resource;













import org.hibernate.Query;
import org.springframework.stereotype.Service;

import com.schoolshopping.entity.GoodVo;
import com.sxt.dao.GoodDao;
import com.sxt.dao.UserDao;
import com.sxt.po.Good;
import com.sxt.po.User;

@Service
public class GoodService {
	@Resource
	public GoodDao goodDao;
	
	public void add(Good good){
		goodDao.addGood(good);
	}

	public GoodDao getGoodDao() {
		return goodDao;
	}

	public void setGoodDao(GoodDao goodDao) {
		this.goodDao = goodDao;
	}

	public List<Good> getGoodById(int  uid) {
		return goodDao.getGoodById(uid);
	}
	
	public List<Good> getGood(int startIndex,int lastIndex) {
		return goodDao.getGood(startIndex, lastIndex);
	}
	
	public List<Good> getGood(int uid,int startIndex,int lastIndex) {
		return goodDao.getGoodById(uid, startIndex, lastIndex);
	}
	
	public List<Good> getTwoGoodByType(String type) {
		return goodDao.getTwoGoodByType(type);
	}
	
	public List<GoodVo> getGoods(int type,String city,int isAdjust,String keyword,int minNewLevel,int maxNewLevel, Integer startIndex, Integer lastIndex) {
		return goodDao.getGoods(type, city, isAdjust, keyword, minNewLevel, maxNewLevel,startIndex,lastIndex);
	}
	
}
