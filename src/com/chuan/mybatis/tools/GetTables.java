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
//		List<String> l = new ArrayList<String>();
//		l.add("TA809");
//		getTables(l, "2018-09-03");
		String date = "2018-09-19";
		List<String> ids = GetIds.getIds();
		for (int i = 0; i < 1; i++) {
			String inputDate = dateAdd(date, 0-i);
			if (isWeekend(inputDate)) {
				continue ;
			}
			List<Holds> holdsList = new ArrayList<Holds>();			
			holdsList = getTables(GetAgreements.getAgreements(ids,inputDate), inputDate);
//		holdsList = getTables(l, "2018-09-03");
//		System.out.println(holdsList.size()+ "  holdsSize");
			String resource = "sqlMapConfig.xml"; // 定位核心配置文件
			InputStream inputStream = null;
			try {
				inputStream = Resources.getResourceAsStream(resource);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream); // 创建 SqlSessionFactory
			
			SqlSession sqlSession = sqlSessionFactory.openSession(); // 获取到 SqlSession
			for (Holds holds : holdsList) {
				sqlSession.insert("com.chuan.mybatis/mapper.UserMapper.insert",holds);
			}
			sqlSession.commit();
			if (inputDate.equals("2018-01-01")) {
				break;
			}
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
			String goods = getGoodsName(agreement);
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
						if (!isEmpty(tdList.get(4*m))) {
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

	private static String getGoodsName(String agreement) {
		String goodsName = "";
		String reg = "^[a-zA-Z]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(agreement);
		if (m.find()) {
			goodsName = m.group(0);
		} else {
			System.out.println("未匹配到商品名称");
		}
		return goodsName;
	}

	private static boolean isEmpty(String string) {
		String reg = "[\\da-z\\u2E80-\\u9FFF]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(string);
		if (m.find()) {
			return false;
		} else {
			// System.out.print("一个空白字符串");
			return true;
		}
	}
	public static String dateAdd(String date , int add) {
		String resultDate = "";
		try {
			java.util.Date sdate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sdate);
			calendar.add(Calendar.DATE, add);
			sdate = calendar.getTime();
			resultDate = new SimpleDateFormat("yyyy-MM-dd").format(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}
	public static boolean isWeekend(String bDate)  {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date bdate = null;
		try {
			bdate = format1.parse(bDate);
		} catch (ParseException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
        Calendar cal = Calendar.getInstance();
        cal.setTime(bdate);
        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else{
            return false;
        }
 
 }
}
