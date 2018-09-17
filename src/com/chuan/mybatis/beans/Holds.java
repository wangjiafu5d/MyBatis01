package com.chuan.mybatis.beans;

import java.sql.Date;

public class Holds {
	private Integer id;
	private String goods;
	private Date date;
	private Integer rate;
	private String compName;
	private Integer value;
	private Integer increase;
	private String type;
	private String agreement;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getGoods() {
		return goods;
	}
	public void setGoods(String goods) {
		this.goods = goods;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public String getCompName() {
		return compName;
	}
	public void setCompName(String compName) {
		this.compName = compName;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getIncrease() {
		return increase;
	}
	public void setIncrease(Integer increase) {
		this.increase = increase;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	@Override
	public String toString() {
		return 	"id： " + id + " "
				+ "品种： " + goods + " "
				+ "日期： " + date + " "
				+ "排名： " + rate + " "
				+ "公司： " + compName + " "
				+ "成交量： " + value + " "
				+ "增减： " + increase + " "
				+ "数据种类：" + type + " "
				+ "期货合约：" + agreement;
	}
}
