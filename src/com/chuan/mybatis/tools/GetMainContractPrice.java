package com.chuan.mybatis.tools;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chuan.mybatis.beans.Holds;
import com.chuan.mybatis.beans.SpotAndFuturePrice;

public class GetMainContractPrice {

	public static void main(String[] args) {
		String endDate = "2018-10-12";
		int totalDays = 700;
		saveToDB(endDate, totalDays);
	}

	private static String getURL(String day) {
		String url = "";
		String pre = "http://www.100ppi.com/sf/day-";
		String end = ".html";
		url = pre + day + end;
		return url;
	}

	public static List<SpotAndFuturePrice> getInf(String day) {
		List<SpotAndFuturePrice> resultList = new ArrayList<SpotAndFuturePrice>();
		String goods = "";
		String spotPrice = "";
		String mainContractPrice = "";
		String agreement = "";
		Date sqlDate = Date.valueOf(day);
		Document doc = null;
		try {
			doc = Jsoup.connect(getURL(day)).timeout(5000).get();
		} catch (IOException e) {
			System.out.println(day + " : timeout");
			e.printStackTrace();
		}
		Element table = doc.getElementById("fdata");
		Elements trs = table.children().first().children();
//		System.out.println(trs.size());
		int count = 0;
		for (int i = 0; i < trs.size(); i++) {
			if (trs.get(i).children().size() == 8) {
//				System.out.println(trs.get(i).text()+"\r\n");
				Elements tds = trs.get(i).children();
				goods = tds.get(0).text().replaceAll(" ", "");
				spotPrice = tds.get(1).text().replaceAll(" ", "");
				mainContractPrice = tds.get(6).text().replaceAll(" ", "");
				agreement = tds.get(5).text().replaceAll(" ", "");
				if (goods.equals("玻璃")) {
//					System.out.println(spotPrice.length());
					spotPrice = BigDecimal.valueOf(Double.valueOf(spotPrice).doubleValue())
							.multiply(BigDecimal.valueOf(80.0)).toString();
				}
				if (goods.equals("鸡蛋")) {
					spotPrice = BigDecimal.valueOf(Double.valueOf(spotPrice).doubleValue())
							.multiply(BigDecimal.valueOf(500.0)).toString();
				}
//				System.out.println(goods + " " + spotPrice + " " + mainContractPrice + " " + agreement);
				SpotAndFuturePrice spotAndFuturePrice = new SpotAndFuturePrice();
				spotAndFuturePrice.setGoods(goods);
				spotAndFuturePrice.setSpotPrice(TextTool.priceMatch(spotPrice));
				spotAndFuturePrice.setMainContractPrice(TextTool.priceMatch(mainContractPrice));
				spotAndFuturePrice.setAgreement(TextTool.agreementMatch(agreement));
				spotAndFuturePrice.setDate(sqlDate);
				resultList.add(spotAndFuturePrice);
				count++;
			}

		}
//		System.out.println("合约个数： " + count + " list: " + resultList.size());
		return resultList;
	}

	public static void saveToDB(String endDate, int totalDays) {
		for (int i = 0; i < totalDays; i++) {
			String inputDate = DateTool.dateAdd(endDate, 0 - i);

			if (DateTool.isWeekend(inputDate)) {
				continue;
			}
			if (inputDate.equals("2015-05-01")) {
				break;
			}
			List<SpotAndFuturePrice> infList = new ArrayList<SpotAndFuturePrice>();
			infList = getInf(inputDate);														// SqlSessionFactory

			SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession(); // 获取到 SqlSession
			for (SpotAndFuturePrice item : infList) {
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertPrice", item);
			}
			sqlSession.commit();
			sqlSession.close();
		}
	}

}
