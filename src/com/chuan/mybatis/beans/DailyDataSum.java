package com.chuan.mybatis.beans;

import java.sql.Date;

public class DailyDataSum {
	String PRODUCTID;
	Integer PRODUCTSORTNO;
	Date date;
	String PRODUCTNAME;
	Double HIGHESTPRICE;
	Double LOWESTPRICE;
	Double AVGPRICE;
	Integer VOLUME;
	Integer OPENINTEREST;
	Double TURNOVER;
	Double YEARVOLUME;
	Double YEARTURNOVER;
	public Integer getOPENINTEREST() {
		return OPENINTEREST;
	}
	public void setOPENINTEREST(Integer oPENINTEREST) {
		OPENINTEREST = oPENINTEREST;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPRODUCTID() {
		return PRODUCTID;
	}
	public void setPRODUCTID(String pRODUCTID) {
		PRODUCTID = pRODUCTID;
	}
	public Integer getPRODUCTSORTNO() {
		return PRODUCTSORTNO;
	}
	public void setPRODUCTSORTNO(Integer pRODUCTSORTNO) {
		PRODUCTSORTNO = pRODUCTSORTNO;
	}
	public String getPRODUCTNAME() {
		return PRODUCTNAME;
	}
	public void setPRODUCTNAME(String pRODUCTNAME) {
		PRODUCTNAME = pRODUCTNAME;
	}
	public Double getHIGHESTPRICE() {
		return HIGHESTPRICE;
	}
	public void setHIGHESTPRICE(Double hIGHESTPRICE) {
		HIGHESTPRICE = hIGHESTPRICE;
	}
	public Double getLOWESTPRICE() {
		return LOWESTPRICE;
	}
	public void setLOWESTPRICE(Double lOWESTPRICE) {
		LOWESTPRICE = lOWESTPRICE;
	}
	public Double getAVGPRICE() {
		return AVGPRICE;
	}
	public void setAVGPRICE(Double aVGPRICE) {
		AVGPRICE = aVGPRICE;
	}
	public Integer getVOLUME() {
		return VOLUME;
	}
	public void setVOLUME(Integer vOLUME) {
		VOLUME = vOLUME;
	}
	public Double getTURNOVER() {
		return TURNOVER;
	}
	public void setTURNOVER(Double tURNOVER) {
		TURNOVER = tURNOVER;
	}
	public Double getYEARVOLUME() {
		return YEARVOLUME;
	}
	public void setYEARVOLUME(Double yEARVOLUME) {
		YEARVOLUME = yEARVOLUME;
	}
	public Double getYEARTURNOVER() {
		return YEARTURNOVER;
	}
	public void setYEARTURNOVER(Double yEARTURNOVER) {
		YEARTURNOVER = yEARTURNOVER;
	}
	@Override
	public String toString() {
		return "date: "+ date+"name: "+ PRODUCTNAME + "high: "+ HIGHESTPRICE
				+ "low: " + LOWESTPRICE + "avgPirce: "+AVGPRICE+"volume: "
				+ VOLUME + "turnover: "+ TURNOVER+"openinterest: "+ OPENINTEREST;
	}
}
