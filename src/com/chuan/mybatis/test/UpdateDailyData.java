package com.chuan.mybatis.test;

import com.chuan.mybatis.tools.GetDailyData;
import com.chuan.mybatis.tools.GetTables;

public class UpdateDailyData {
	public static void main(String[] args) {
		String date = "2018-10-30";
		GetTables.saveToDB(date, 1);
		GetDailyData.getSHDailyData(date);
		GetDailyData.getZZDailyData(date);
		GetDailyData.getDLDailyData(date);
	}
}
