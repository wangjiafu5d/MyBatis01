package com.chuan.mybatis.beans;

public class AnalysisResult {
	public String date;
	public Integer volumeTopTen;
	public Integer volumeTopTwenty;
	public Integer sellTopTen;
	public Integer buyTopTen;
	public Integer sellTopTwenty;
	public Integer buyTopTwenty;
	public Integer YAVolume;
	public Integer YABuy;
	public Integer YASell;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Integer getVolumeTopTen() {
		return volumeTopTen;
	}
	public void setVolumeTopTen(Integer volumeTopTen) {
		this.volumeTopTen = volumeTopTen;
	}
	public Integer getVolumeTopTwenty() {
		return volumeTopTwenty;
	}
	public void setVolumeTopTwenty(Integer volumeTopTwenty) {
		this.volumeTopTwenty = volumeTopTwenty;
	}
	public Integer getSellTopTen() {
		return sellTopTen;
	}
	public void setSellTopTen(Integer sellTopTen) {
		this.sellTopTen = sellTopTen;
	}
	public Integer getBuyTopTen() {
		return buyTopTen;
	}
	public void setBuyTopTen(Integer buyTopTen) {
		this.buyTopTen = buyTopTen;
	}
	public Integer getSellTopTwenty() {
		return sellTopTwenty;
	}
	public void setSellTopTwenty(Integer sellTopTwenty) {
		this.sellTopTwenty = sellTopTwenty;
	}
	public Integer getBuyTopTwenty() {
		return buyTopTwenty;
	}
	public void setBuyTopTwenty(Integer buyTopTwenty) {
		this.buyTopTwenty = buyTopTwenty;
	}
	public Integer getYAVolume() {
		return YAVolume;
	}
	public void setYAVolume(Integer yAVolume) {
		YAVolume = yAVolume;
	}
	public Integer getYABuy() {
		return YABuy;
	}
	public void setYABuy(Integer yABuy) {
		YABuy = yABuy;
	}
	public Integer getYASell() {
		return YASell;
	}
	public void setYASell(Integer yASell) {
		YASell = yASell;
	}
	@Override
	public String toString() {
		
		return "日期： " + date + " "
				+ "成交前十： " + volumeTopTen + " "
				+ "成交二十： " + volumeTopTwenty + " "
				+ "多头前十： " + buyTopTen + " "
				+ "多头二十： " + buyTopTwenty + " "
				+ "空头前十： " + sellTopTen + " "
				+ "空头二十： " + sellTopTwenty + " "			
				+ "永安成交： " + YAVolume
				+ "永安多仓： " + YABuy
				+ "永安空仓： " + YASell;
	}
}
