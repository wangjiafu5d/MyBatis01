package com.chuan.mybatis.test;

import com.chuan.mybatis.tools.GetDailyData;
import com.chuan.mybatis.tools.GetTables;

public class UpdateDailyData {
	public static void main(String[] args) {
		long str = System.currentTimeMillis();
		String date = "2018-12-04";
		GetTables.saveToDB(date, 1);
		GetDailyData.getSHDailyData(date);
		GetDailyData.getZZDailyData(date);
		GetDailyData.getDLDailyData(date);
		System.out.println((System.currentTimeMillis()-str)/1000);
	}
}
