package com.sxt.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sxt.dao.StallDao;
import com.sxt.po.Stall;

@Service
public class StallService {
	@Resource
	public StallDao stallDao;
	
	public void insert(Stall stall){
		stallDao.add(stall);
	}
	
	public Stall getStallById(int id) {
		return stallDao.getStallById(id);
	}
	
	public List<Stall> getStallByUid(int id) {
		return stallDao.getStallByUid(id);
	}
	
	public List<Stall> getStallByKeyword(String  keyword) {
		return stallDao.getStallByKeyword(keyword);
	}
	
	public boolean deleteStall(int id){
		return stallDao.deleteStall(id);
	}
	
	public void updateStall(Stall stall){
		stallDao.updateStall(stall);
	}

	public StallDao getStallDao() {
		return stallDao;
	}

	public void setStallDao(StallDao stallDao) {
		this.stallDao = stallDao;
	}

	
	
}
