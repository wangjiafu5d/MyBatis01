package com.chuan.mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.chuan.mybatis.beans.Holds;

public class Test {
	public static void main(String[] args) {
		try {
			new Test().ceshi();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	public void ceshi() throws IOException {
        /**
         *  1、获得 SqlSessionFactory
         *  2、获得 SqlSession
         *  3、调用在 mapper 文件中配置的 SQL 语句
         */
        String resource = "sqlMapConfig.xml";           // 定位核心配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);    // 创建 SqlSessionFactory

        SqlSession sqlSession = sqlSessionFactory.openSession();    // 获取到 SqlSession

        // 调用 mapper 中的方法：命名空间 + id
        List<Holds> holdsList = sqlSession.selectList("com.chuan.mybatis/mapper.UserMapper.findAll");

        for (Holds hold : holdsList){        	
            System.out.println(hold);
        }
        
        Holds holds = new Holds();
        holds.setGoods("cu");
        Date date = Date.valueOf("2018-08-31");
        holds.setDate(date);
        holds.setRate(4);
        holds.setCompName("中信期货");
        holds.setValue(35880);
        holds.setIncrease(-7239);
        holds.setType("多仓");
        holds.setAgreement("cu1811");
        sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insert",holds);
//        sqlSession.commit();
    }
}
