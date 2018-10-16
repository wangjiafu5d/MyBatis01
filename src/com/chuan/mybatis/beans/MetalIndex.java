package com.chuan.mybatis.beans;

import java.sql.Date;

public class MetalIndex {
	String TRADINGDAY;
	Date date;
	Double LASTPRICE;
	Double OPENPRICE;
	Double CLOSEPRICE;
	Double PRECLOSEPRICE;
	Double UPDOWN;
	Double UPDOWN1;
	Double UPDOWN2;
	Double HIGHESTPRICE;
	Double LOWESTPRICE;
	Double AVGPRICE;
	Double SETTLEMENTPRICE;
	public String getTRADINGDAY() {
		return TRADINGDAY;
	}
	public void setTRADINGDAY(String tRADINGDAY) {
		TRADINGDAY = tRADINGDAY;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getLASTPRICE() {
		return LASTPRICE;
	}
	public void setLASTPRICE(Double lASTPRICE) {
		LASTPRICE = lASTPRICE;
	}
	public Double getOPENPRICE() {
		return OPENPRICE;
	}
	public void setOPENPRICE(Double oPENPRICE) {
		OPENPRICE = oPENPRICE;
	}
	public Double getCLOSEPRICE() {
		return CLOSEPRICE;
	}
	public void setCLOSEPRICE(Double cLOSEPRICE) {
		CLOSEPRICE = cLOSEPRICE;
	}
	public Double getPRECLOSEPRICE() {
		return PRECLOSEPRICE;
	}
	public void setPRECLOSEPRICE(Double pRECLOSEPRICE) {
		PRECLOSEPRICE = pRECLOSEPRICE;
	}
	public Double getUPDOWN() {
		return UPDOWN;
	}
	public void setUPDOWN(Double uPDOWN) {
		UPDOWN = uPDOWN;
	}
	public Double getUPDOWN1() {
		return UPDOWN1;
	}
	public void setUPDOWN1(Double uPDOWN1) {
		UPDOWN1 = uPDOWN1;
	}
	public Double getUPDOWN2() {
		return UPDOWN2;
	}
	public void setUPDOWN2(Double uPDOWN2) {
		UPDOWN2 = uPDOWN2;
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
	public Double getSETTLEMENTPRICE() {
		return SETTLEMENTPRICE;
	}
	public void setSETTLEMENTPRICE(Double sETTLEMENTPRICE) {
		SETTLEMENTPRICE = sETTLEMENTPRICE;
	}
	
}
