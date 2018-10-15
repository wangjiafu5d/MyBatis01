package com.chuan.mybatis.tools;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.chuan.mybatis.beans.MetalIndex;
import com.chuan.mybatis.beans.SHData;
import com.chuan.mybatis.beans.SHDataSum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GetDailyData {
	public static void main(String[] args) {
		test();
	}

	public static void test() {
		String url = "http://www.shfe.com.cn/data/dailydata/kx/kx20181011.dat";
		try {
			Document doc = Jsoup.connect(url).get();
			System.out.println(doc.select("body").text());
			Gson gson = new GsonBuilder()
					.registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
					.registerTypeAdapter(int.class, new IntegerDefault0Adapter())
					.create();
			JsonObject jso = new JsonParser().parse(TextTool.replaceSpecialStr(doc.select("body").text()))
					.getAsJsonObject();
			System.out.println(jso.get("o_curinstrument").getAsJsonArray().get(0).getAsJsonObject().get("PRODUCTNAME"));
			System.out.println(jso.get("o_curproduct").getAsJsonArray().size());
			System.out.println(jso.get("o_curmetalindex"));
			List<SHData> list1 = gson.fromJson(jso.get("o_curinstrument"), new TypeToken<List<SHData>>() {
			}.getType());
			List<SHDataSum> list2 = gson.fromJson(jso.get("o_curproduct"), new TypeToken<List<SHDataSum>>() {
			}.getType());
			List<MetalIndex> list3 = gson.fromJson(jso.get("o_curmetalindex"), new TypeToken<List<MetalIndex>>() {
			}.getType());
			System.out.println(list1.size());
			System.out.println(list2.size());
			System.out.println(list3.size());
			
			SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession(); // 获取到 SqlSession
			for (SHData item : list1) {
				if (item.getOPENPRICE()==0) {
					continue ;
				}
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertSHData", item);
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
