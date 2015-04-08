package com.sxt.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Friend {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private int sellUid;
	private int buyUid;

	public Friend(int id) {
		super();
		this.id = id;
	}
	public Friend() {
		super();
	}
	public int getSellUid() {
		return sellUid;
	}
	public void setSellUid(int sellUid) {
		this.sellUid = sellUid;
	}
	public int getBuyUid() {
		return buyUid;
	}
	public void setBuyUid(int buyUid) {
		this.buyUid = buyUid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
