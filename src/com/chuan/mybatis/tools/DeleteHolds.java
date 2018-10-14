package com.chuan.mybatis.tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DeleteHolds {
	public static void main(String[] args) {
//		deleteFromDate("2018-01-26");
		deleteOneDay("2018-09-25");
	}
	public static void deleteFromDate(String strDate) {
		String resource = "sqlMapConfig.xml"; // 定位核心配置文件
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); // 创建 SqlSessionFactory
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Date date = Date.valueOf(strDate);
		sqlSession.delete("com.chuan.mybatis/mapper.UserMapper.deleteByDate",date);
		sqlSession.commit();
	}
	public static void deleteOneDay(String strDate) {
		String resource = "sqlMapConfig.xml"; // 定位核心配置文件
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); // 创建 SqlSessionFactory
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Date date = Date.valueOf(strDate);
		sqlSession.delete("com.chuan.mybatis/mapper.UserMapper.deleteOneDay",date);
		sqlSession.commit();
	}
}
