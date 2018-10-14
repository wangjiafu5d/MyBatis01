package com.chuan.mybatis.beans;

import java.sql.Date;

public class SpotAndFuturePrice {
	private Integer id = null;
	private String goods = "";
	private String spotPrice = "";
	private String mainContractPrice = "";
	private Date date = null;
	private String agreement = "";
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	public String getGoods() {
		return goods;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public String getSpotPrice() {
		return spotPrice;
	}
	public void setSpotPrice(String spotPrice) {
		this.spotPrice = spotPrice;
	}
	public String getMainContractPrice() {
		return mainContractPrice;
	}
	public void setMainContractPrice(String mainContractPrice) {
		this.mainContractPrice = mainContractPrice;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
