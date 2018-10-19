package com.chuan.mybatis.tools;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.chuan.mybatis.beans.Holds;

public class GetTables {
	public static void main(String[] args) {
		String endDate = "2018-10-19";
		int totalDays = 1;
		saveToDB(endDate, totalDays);
	}
	public static void saveToDB(String endDate,int totalDays) {
//		List<String> l = new ArrayList<String>();
//		l.add("TA809");
//		getTables(l, "2018-09-03");		
		List<String> ids = GetIds.getIds();
		for (int i = 0; i < totalDays; i++) {
			String inputDate = DateTool.dateAdd(endDate, 0-i);
			if (DateTool.isWeekend(inputDate)) {
				continue ;
			}
			if (inputDate.equals("2018-01-01")) {
				break;
			}
			List<Holds> holdsList = new ArrayList<Holds>();			
			holdsList = getTables(GetAgreements.getAgreements(ids,inputDate), inputDate);
//		holdsList = getTables(l, "2018-09-03");
//		System.out.println(holdsList.size()+ "  holdsSize");
			SqlSession sqlSession = GetSessionFactory.sqlSessionFactory.openSession();
			for (Holds holds : holdsList) {
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insertHolds",holds);
			}
			sqlSession.commit();
			sqlSession.close();
		}		
//		System.out.println(dateAdd("2018-09-14", -20));
		// System.out.println(getGoodsName("al1809"));
		
	}

	public static List<Holds> getTables(List<String> agreements, String date) {
		// FileOutputStream fos = null;
		// try {
		// fos = new FileOutputStream("C:\\Users\\chuan\\desktop\\99qh.xlsx", true);
		// } catch (FileNotFoundException e1) {
		// e1.printStackTrace();
		// }
		List<Holds> holdsList = new ArrayList<Holds>();
		for (int i = 0; i < agreements.size(); i++) {
			String agreement = agreements.get(i);
			String goods = TextTool.goodsCodeMatch(agreement);
//			System.out.println(agreement);
			String pre = "http://service.99qh.com/hold2/MemberHold/GetTableHtml.aspx?date=";
			String next = "&user=99qh&goods=6&agreement=";
			String end = "&count=20&_=1537105152162";
			String url = pre + date + next + agreement + end;
			// try {
			// fos.write((agreement+"\r\n").getBytes());
			// } catch (IOException e1) {
			// e1.printStackTrace();
			// }
			try {
				Document doc = Jsoup.connect(url).timeout(2000).get();
				Elements trs = doc.select("tr");
				// System.out.println(trs.size());
				back: for (int j = 4; j < trs.size(); j++) {
					Elements tds = trs.get(j).select("td");
					List<String> tdList = new ArrayList<>();
					for (int k = 0; k < tds.size(); k++) {
						String td = tds.get(k).text();
						if (td.equals("合计")) {
							break back;
						}
						tdList.add(td);
//						System.out.print(td + " ");

						// fos.write((td+"\t").getBytes());
						// fos.flush();
					}
					for (int m = 0; m < tdList.size() / 4; m++) {
						if (!TextTool.isEmpty(tdList.get(4*m))) {
							Holds hold = new Holds();
							hold.setRate(Integer.valueOf(tdList.get(4 * m)));
							hold.setCompName(tdList.get(4 * m + 1));
							hold.setValue(Integer.valueOf(tdList.get(4 * m + 2)));
							hold.setIncrease(Integer.valueOf(tdList.get(4 * m + 3)));
							hold.setAgreement(agreement);
							hold.setDate(Date.valueOf(date));
							hold.setGoods(goods);
							switch (m) {
							case 0:
								hold.setType("成交量");
								break;
							case 1:
								hold.setType("多仓量");
								break;
							case 2:
								hold.setType("空仓量");
								break;
							}
							holdsList.add(hold);
						}
					}
//					System.out.println();
					// fos.write("\r\n".getBytes());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return holdsList;
	}
}
