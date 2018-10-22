package com.chuan.mybatis.beans;

public class AgreementInf {
	String id = "";
	String code = "";
	String name = "";
	Integer oneHandWeight = Integer.valueOf(0);
	Double marginRate = Double.valueOf(0.0);
	Double GJRate = Double.valueOf(0.0);
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOneHandWeight() {
		return oneHandWeight;
	}
	public void setOneHandWeight(Integer oneHandWeight) {
		this.oneHandWeight = oneHandWeight;
	}
	public Double getMarginRate() {
		return marginRate;
	}
	public void setMarginRate(Double marginRate) {
		this.marginRate = marginRate;
	}
	public Double getGJRate() {
		return GJRate;
	}
	public void setGJRate(Double gjRate) {
		this.GJRate = gjRate;
	}
	@Override
	public String toString() {		
		return "id: "+ id + "code: "+ code+ "name: "+ name + "一手： " + oneHandWeight
				+ "基础保证金率： "+ marginRate + "国金保证金率： "+ GJRate;
	}
	
}
