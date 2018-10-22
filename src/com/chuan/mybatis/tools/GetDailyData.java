package com.chuan.mybatis.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chuan.mybatis.beans.MetalIndex;
import com.chuan.mybatis.beans.AgreementInf;
import com.chuan.mybatis.beans.DailyData;
import com.chuan.mybatis.beans.DailyDataSum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GetDailyData {
	public static Map<String, Integer> weightMap = getOneHandWeight();
	public static int ONEHUNDREDMILLION = 100000000;
	public static String date = "2018-10-22";
	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		CountDownLatch countDownLatch = new CountDownLatch(3);
		new Thread() {
			public void run() {
				getSHDailyData(date);
				countDownLatch.countDown();
			}; 
		}.start();
		new Thread() {
			public void run() {
				getZZDailyData(date);	
				countDownLatch.countDown();
			}; {};
		}.start();
		new Thread() {
			public void run() {
				getDLDailyData(date);	
				countDownLatch.countDown();
			}; {};
		}.start();
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		Long end = System.currentTimeMillis();
		System.out.println((end-start)/1000.0);
	}

	public static void test() {

	}

	public static void getSHDailyData(String date) {
		Map<String,DailyDataSum> agreementInfMap = new HashMap<String,DailyDataSum>();
		String url = "http://www.shfe.com.cn/data/dailydata/kx/kx" + date.replaceAll("-", "") + ".dat";
		Date sqlDate = Date.valueOf(date);
		try {
			Document doc = Jsoup.connect(url).get();
//			System.out.println(doc.select("body").text());
			Gson gson = new GsonBuilder().registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
					.registerTypeAdapter(int.class, new IntegerDefault0Adapter())
					.registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
					.registerTypeAdapter(double.class, new DoubleDefault0Adapter()).create();
			JsonObject jso = new JsonParser().parse(TextTool.replaceSpecialStr(doc.select("body").text()))
					.getAsJsonObject();

//			System.out.println(jso.get("o_curinstrument").getAsJsonArray().get(0).getAsJsonObject().get("PRODUCTNAME"));
//			System.out.println(jso.get("o_curproduct").getAsJsonArray().size());
//			System.out.println(jso.get("o_curmetalindex"));

			List<DailyData> list1 = gson.fromJson(jso.get("o_curinstrument"), new TypeToken<List<DailyData>>() {
			}.getType());
//			List<DailyDataSum> list2 = gson.fromJson(jso.get("o_curproduct"), new TypeToken<List<DailyDataSum>>() {
//			}.getType());
			List<MetalIndex> list3 = gson.fromJson(jso.get("o_curmetalindex"), new TypeToken<List<MetalIndex>>() {
			}.getType());

//			System.out.println(list1.size());
//			System.out.println(list2.size());
//			System.out.println(list3.size());

			SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
			for (DailyData item : list1) {
				if (item.getOPENPRICE() == 0) {
					continue;
				}
				item.setDate(sqlDate);
				agreementInfMap = dailyDataSum(agreementInfMap, item);
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertDailyData", item);
			}
//			for (DailyDataSum item : list2) {
//				if (item.getHIGHESTPRICE() == 0) {
//					continue;
//				}
//				
//				item.setDate(sqlDate);
//				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertDailyDataSum", item);
//			}
			for (MetalIndex item : list3) {
				if (item.getHIGHESTPRICE() == 0) {
					continue;
				}
				item.setDate(sqlDate);
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertMetalIndex", item);
			}
			for (Entry<String, DailyDataSum> entry : agreementInfMap.entrySet()) {
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertDailyDataSum", entry.getValue());
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void getZZDailyData(String date) {
		String url = "http://www.czce.com.cn/";
		Date sqlDate = Date.valueOf(date);
		Map<String,DailyDataSum> agreementInfMap = new HashMap<String,DailyDataSum>();
		try {
			Connection con = Jsoup.connect(url);
			con.header("Accept", "text/html, application/xhtml+xml, */*");
			con.header("Content-Type", "application/x-www-form-urlencoded");
			con.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))");

			Response resp = con.method(Method.GET).execute();
			Map<String, String> cookies = resp.cookies();
			cookies.put("UM_distinctid", "16663782952154-024baca50ed513-143c7340-15f900-166637829532f3");
			cookies.put("CNZZDATA1264458526", "630591218-1539265702-%7C1539928308");
//			for (Entry<String, String> entry : cookies.entrySet()) {
//				System.out.println("key: " + entry.getKey() + "   " + "value: " + entry.getValue());
//			}
			con.get();
			String url2 = "http://www.czce.com.cn/cn/DFSStaticFiles/Future/2018/" + date.replaceAll("-", "")
					+ "/FutureDataDaily.htm";
			Response resp2 = Jsoup.connect(url2).header("Accept", "text/html, application/xhtml+xml, */*")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))")
					.cookies(cookies).method(Method.GET).execute();
			Document doc2 = Jsoup.parse(resp2.body().replaceAll(",", ""));
			Elements table = doc2.getElementsByTag("table");
//		     System.out.println(table.first());
			Elements trs = table.first().children().last().children();
//			System.out.println(trs.size());
			Map<String, String> nameCodeMap = GetNameCodeMap.getMap();
			SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
			for (Element tr : trs) {
				DailyData item = new DailyData();
				Elements tds = tr.children();
				String agreement = TextTool.agreementMatch(tds.get(0).text());
				String name = nameCodeMap.get(TextTool.goodsCodeMatch(tds.get(0).text()).toLowerCase());
				if (agreement.equals("") || tds.size() < 12) {
					continue;
				}
				item.setDate(sqlDate);
				item.setPRODUCTNAME(name);
				item.setDELIVERYMONTH(agreement);
				item.setOPENPRICE(Double.valueOf(tds.get(2).text()));
				item.setHIGHESTPRICE(Double.valueOf(tds.get(3).text()));
				item.setLOWESTPRICE(Double.valueOf(tds.get(4).text()));
				item.setCLOSEPRICE(Double.valueOf(tds.get(5).text()));
				item.setSETTLEMENTPRICE(Double.valueOf(tds.get(6).text()));
				item.setVOLUME(Integer.valueOf(tds.get(9).text()));
				item.setOPENINTEREST(Integer.valueOf(tds.get(10).text()));
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertDailyData", item);
//				System.out.println(tr.text() + "\r\n");
				agreementInfMap = dailyDataSum(agreementInfMap, item);
			}
			for (Entry<String, DailyDataSum> entry : agreementInfMap.entrySet()) {
//				System.out.println("-------");
//				System.out.println(entry.toString());
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertDailyDataSum", entry.getValue());
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getDLDailyData(String date) {
		Map<String,DailyDataSum> agreementInfMap = new HashMap<String,DailyDataSum>();
		Date sqlDate = Date.valueOf(date);
		String url = "http://www.dce.com.cn/publicweb/quotesdata/dayQuotesCh.html";
		Connection conn = Jsoup.connect(url);
		Map<String, String> postInfMap = new HashMap<String, String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sqlDate);
		postInfMap.put("dayQuotes.variety", "all");
		postInfMap.put("dayQuotes.trade_type", "0");
		postInfMap.put("year", "" + calendar.get(Calendar.YEAR));
		postInfMap.put("month", "" + calendar.get(Calendar.MONTH));
		postInfMap.put("day", "" + calendar.get(Calendar.DAY_OF_MONTH));
		SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
		try {
			Response resp = conn.header("Accept", "text/html, application/xhtml+xml, */*")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))")
					.data(postInfMap)
					.method(Method.POST)
					.execute();
			Document doc = Jsoup.parse(resp.body().replaceAll("-", "0"));
			Element table = doc.getElementsByTag("tbody").first();
//			System.out.println(resp.body());
			Elements trs = table.children();
			for (Element tr : trs) {
//				System.out.println(tr.text());
				DailyData item = new DailyData();
				Elements tds = tr.children();
				String name = TextTool.getChinese(tds.get(0).text());
				if (name.equals("纤维板")||name.equals("胶合板")) {					
					continue ;
				}
				String agreement = TextTool.agreementMatch(tds.get(1).text());
				if (agreement.equals("") || tds.size() < 12) {
					continue;
				}
				item.setDate(sqlDate);
				item.setPRODUCTNAME(name);
				item.setDELIVERYMONTH(agreement);
				item.setOPENPRICE(Double.valueOf(tds.get(2).text()));
				item.setHIGHESTPRICE(Double.valueOf(tds.get(3).text()));
				item.setLOWESTPRICE(Double.valueOf(tds.get(4).text()));
				item.setCLOSEPRICE(Double.valueOf(tds.get(5).text()));
				item.setSETTLEMENTPRICE(Double.valueOf(tds.get(7).text()));
				item.setVOLUME(Integer.valueOf(tds.get(10).text()));
				item.setOPENINTEREST(Integer.valueOf(tds.get(11).text()));
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertDailyData", item);
				agreementInfMap = dailyDataSum(agreementInfMap, item);
			}
//			System.out.println(doc.getElementsByTag("tbody").text());
			for (Entry<String, DailyDataSum> entry : agreementInfMap.entrySet()) {
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertDailyDataSum", entry.getValue());
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static Map<String,DailyDataSum> dailyDataSum(Map<String,DailyDataSum> map,DailyData item) {
		Date date = item.getDate();
		String name = item.getPRODUCTNAME();
		Double highPrice = item.getHIGHESTPRICE();
		Double lowPrice = item.getLOWESTPRICE();
		Double avgPrice = item.getSETTLEMENTPRICE();
		Integer volume = item.getVOLUME();
		Integer openinterst = item.getOPENINTEREST();
		Integer weight = weightMap.get(name);
		DailyDataSum value = map.get(name);
		if (value==null) {
			 value = new DailyDataSum();			
			 value.setHIGHESTPRICE(highPrice);
			 value.setLOWESTPRICE(lowPrice);
			 value.setAVGPRICE(avgPrice);
			 value.setVOLUME(volume);
			 value.setOPENINTEREST(openinterst);
			 BigDecimal sum = new BigDecimal(weight).multiply(new BigDecimal(volume))
					 .multiply(new BigDecimal(avgPrice)).divide(new BigDecimal(ONEHUNDREDMILLION));
			 value.setTURNOVER(sum.doubleValue());
		}else {
			if (highPrice>value.getHIGHESTPRICE()) {
				value.setHIGHESTPRICE(highPrice);
			}if (value.getLOWESTPRICE()==0||(lowPrice<value.getLOWESTPRICE()&&lowPrice!=0)) {
				value.setLOWESTPRICE(lowPrice);
			}
			value.setAVGPRICE((value.getAVGPRICE()*value.getVOLUME()+ avgPrice*volume)
					/(value.getVOLUME()+volume+0.000001));
			value.setVOLUME(volume+value.getVOLUME());
			value.setOPENINTEREST(openinterst + value.getOPENINTEREST());
			BigDecimal sum = new BigDecimal(weight).multiply(new BigDecimal(volume))
					 .multiply(new BigDecimal(avgPrice)).divide(new BigDecimal(ONEHUNDREDMILLION));
			value.setTURNOVER(value.getTURNOVER()+sum.doubleValue());
		}
		 value.setPRODUCTNAME(name);
		 value.setDate(date);
		 map.put(name, value);
		 return map;
	}
	public static Map<String, Integer> getOneHandWeight() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		List<AgreementInf> list = new ArrayList<AgreementInf>();
		StringBuffer sb = new StringBuffer();
		 BufferedReader br = null;		   
		    try {
				br = new BufferedReader(new InputStreamReader(Resources.getResourceAsStream("com\\chuan\\mybatis\\assets\\agreementJsonInf.txt"),"utf-8"));
				String line = null;
			    while ((line = br.readLine())!=null) {	    	
			    	sb.append(line);
			    }
			    br.close();
			} catch (FileNotFoundException e) {
				System.out.println("文件输入流出错");
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		Gson gson = new Gson();
		list = gson.fromJson(sb.toString(), new TypeToken<List<AgreementInf>>() {}.getType());
		for (AgreementInf agreementInf : list) {
			map.put(agreementInf.getName(), agreementInf.getOneHandWeight());
		}
		return map;
	}
}
