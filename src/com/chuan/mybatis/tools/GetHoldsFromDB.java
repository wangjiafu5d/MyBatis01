package com.chuan.mybatis.tools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.chuan.mybatis.beans.Holds;

public class GetHoldsFromDB {
	public static void main(String[] args) {
		String resource = "sqlMapConfig.xml";           // 定位核心配置文件
        InputStream inputStream = null;
		try {
			inputStream = Resources.getResourceAsStream(resource);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);    // 创建 SqlSessionFactory

        SqlSession sqlSession = sqlSessionFactory.openSession();    // 获取到 SqlSession

        // 调用 mapper 中的方法：命名空间 + id
        Map<String,Object> map = new HashMap<>();
        map.put("goods", "ni");
        map.put("date", Date.valueOf("2018-09-14"));
        List<Holds> holdsList = sqlSession.selectList("com.chuan.mybatis/mapper.UserMapper.findByGoodsAndDate",map);

        System.out.println(holdsList.size());
        for (Holds hold : holdsList){
            System.out.println(hold);
        }
	}
}
