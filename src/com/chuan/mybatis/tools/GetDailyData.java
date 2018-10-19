package com.chuan.mybatis.tools;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.session.SqlSession;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		String date = "2018-10-18";
//		getSHDailyData(date);
//		getZZDailyData(date);
		getDLDailyData(date);
	}

	public static void test() {

	}

	public static void getSHDailyData(String date) {
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

			List<SHData> list1 = gson.fromJson(jso.get("o_curinstrument"), new TypeToken<List<SHData>>() {
			}.getType());
			List<SHDataSum> list2 = gson.fromJson(jso.get("o_curproduct"), new TypeToken<List<SHDataSum>>() {
			}.getType());
			List<MetalIndex> list3 = gson.fromJson(jso.get("o_curmetalindex"), new TypeToken<List<MetalIndex>>() {
			}.getType());

//			System.out.println(list1.size());
//			System.out.println(list2.size());
//			System.out.println(list3.size());

			SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
			for (SHData item : list1) {
				if (item.getOPENPRICE() == 0) {
					continue;
				}
				item.setDate(sqlDate);
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertSHData", item);
			}
			for (SHDataSum item : list2) {
				if (item.getHIGHESTPRICE() == 0) {
					continue;
				}
				item.setDate(sqlDate);
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertSHDataSum", item);
			}
			for (MetalIndex item : list3) {
				if (item.getHIGHESTPRICE() == 0) {
					continue;
				}
				item.setDate(sqlDate);
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertMetalIndex", item);
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
			Document doc = con.get();
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
			Map<String, String> map = GetNameCodeMap.getMap();
			SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
			for (Element tr : trs) {
				SHData item = new SHData();
				Elements tds = tr.children();
				String agreement = TextTool.agreementMatch(tds.get(0).text());
				String name = map.get(TextTool.goodsCodeMatch(tds.get(0).text()).toLowerCase());
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
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertSHData", item);
//				System.out.println(tr.text() + "\r\n");
			}
			sqlSession.commit();
			sqlSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void getDLDailyData(String date) {
		Date sqlDate = Date.valueOf(date);
		String url = "http://www.dce.com.cn/publicweb/quotesdata/dayQuotesCh.html";
		Connection conn = Jsoup.connect(url);
		Map<String, String> map = new HashMap<String, String>();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sqlDate);
		map.put("dayQuotes.variety", "all");
		map.put("dayQuotes.trade_type", "0");
		map.put("year", "" + calendar.get(Calendar.YEAR));
		map.put("month", "" + calendar.get(Calendar.MONTH));
		map.put("day", "" + calendar.get(Calendar.DAY_OF_MONTH));
		SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
		try {
			Response resp = conn.header("Accept", "text/html, application/xhtml+xml, */*")
					.header("Content-Type", "application/x-www-form-urlencoded")
					.header("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0))")
					.data(map)
					.method(Method.POST)
					.execute();
			Document doc = Jsoup.parse(resp.body().replaceAll("-", "0"));
			Element table = doc.getElementsByTag("tbody").first();
			Elements trs = table.children();
			for (Element tr : trs) {
//				System.out.println(tr.text());
				SHData item = new SHData();
				Elements tds = tr.children();
				String name = tds.get(0).text();
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
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertSHData", item);
			}
//			System.out.println(doc.getElementsByTag("tbody").text());
			sqlSession.commit();
			sqlSession.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}