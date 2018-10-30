package com.chuan.mybatis.tools;

import java.sql.Date;

import org.apache.ibatis.session.SqlSession;

public class DeleteDailyData {
	public static void main(String[] args) {
		deleteOneDay("2018-10-24");
	}
	public static void deleteOneDay(String strDate) {

		SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
		Date date = Date.valueOf(strDate);
		sqlSession.delete("com.chuan.mybatis/mapper.UserMapper.deleteOneDailyData", date);
		sqlSession.delete("com.chuan.mybatis/mapper.UserMapper.deleteOneDailyDataSum", date);
		sqlSession.commit();
		sqlSession.close();
	}
}
