package com.chuan.mybatis.tools;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class GetSessionFactory {
	public static SqlSessionFactory sqlSessionFactory = getInstance();

	public static SqlSessionFactory getInstance() {
		String resource = "sqlMapConfig.xml"; // 定位核心配置文件
		InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {

			e.printStackTrace();
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); // 创建 SqlSessionFactory

		return sqlSessionFactory;
	}

}
