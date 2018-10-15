package com.chuan.mybatis.tools;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.chuan.mybatis.beans.Holds;

public class GetHoldsFromDB {
	public static void main(String[] args) {
		getHolds("ni", "2018-09-14");
	}
	public static List<Holds> getHolds(String goods,String date){
	
        SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();    // 获取到 SqlSession

        // 调用 mapper 中的方法：命名空间 + id
        Map<String,Object> map = new HashMap<>();
        map.put("goods", goods);
        map.put("date", Date.valueOf(date));
        List<Holds> holdsList = sqlSession.selectList("com.chuan.mybatis/mapper.UserMapper.findByGoodsAndDate",map);
        sqlSession.commit();
        sqlSession.close();
        
//        System.out.println(holdsList.size());
//        for (Holds hold : holdsList){
//            System.out.println(hold);
//        }
        return holdsList;
	}
}
