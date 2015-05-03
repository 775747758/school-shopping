package com.schoolshopping.entity;

public class GoodVo {

	
	private int id;
	private String goodName;
	private int type;
	private String price;
	private int isAdjust;//�Ƿ����С��
	private int newLevel;//�¾ɳ̶�
	private String introduction;//���
	private int uid;//�û�id
	private String latitude;
	private String longitude;
	private String uname;
	
	
	
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	public GoodVo(int id, String goodName, int type, String price,
			int isAdjust, int newLevel, String introduction, int uid,
			String latitude, String longitude, String uname) {
		super();
		this.id = id;
		this.goodName = goodName;
		this.type = type;
		this.price = price;
		this.isAdjust = isAdjust;
		this.newLevel = newLevel;
		this.introduction = introduction;
		this.uid = uid;
		this.latitude = latitude;
		this.longitude = longitude;
		this.uname = uname;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
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
	public int getNewLevel() {
		return newLevel;
	}
	public void setNewLevel(int newLevel) {
		this.newLevel = newLevel;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
}
