package com.sxt.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Good {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String goodName;
	private String type;
	private String price;
	private int isAdjust;//�Ƿ����С��
	private String newLevel;//�¾ɳ̶�
	private String introduction;//���
	private int uid;//�û�id
	
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGoodName() {
		return goodName;
	}
	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getIsAdjust() {
		return isAdjust;
	}
	public void setIsAdjust(int isAdjust) {
		this.isAdjust = isAdjust;
	}
	public String getNewLevel() {
		return newLevel;
	}
	public void setNewLevel(String newLevel) {
		this.newLevel = newLevel;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", goodName=" + goodName + ", type=" + type
				+ ", price=" + price + ", isAdjust=" + isAdjust + ", newLevel="
				+ newLevel + ", introduction=" + introduction + "]";
	}
	
	
}
