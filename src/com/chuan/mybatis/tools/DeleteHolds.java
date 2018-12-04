package com.chuan.mybatis.tools;

import java.sql.Date;
import org.apache.ibatis.session.SqlSession;

public class DeleteHolds {
	public static void main(String[] args) {
//		deleteFromDate("2018-01-26");
		deleteOneDay("2018-12-04");
	}

	public static void deleteFromDate(String strDate) {

		SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
		Date date = Date.valueOf(strDate);
		sqlSession.delete("com.chuan.mybatis/mapper.UserMapper.deleteByDate", date);
		sqlSession.commit();
		sqlSession.close();
	}

	public static void deleteOneDay(String strDate) {

		SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
		Date date = Date.valueOf(strDate);
		sqlSession.delete("com.chuan.mybatis/mapper.UserMapper.deleteOneDay", date);
		sqlSession.commit();
		sqlSession.close();
	}
}
