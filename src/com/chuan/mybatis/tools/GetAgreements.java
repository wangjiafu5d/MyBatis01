package com.chuan.mybatis.tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetAgreements {
	public static void main(String[] args) {
//		List<String> l = new ArrayList<>();
//		l.add("6");
//		l.add("7");
		List<String> result = getAgreements(GetIds.getIds(),"2018-09-14");
		for (String string : result) {
			System.out.println(string);
		}
		System.out.println(result.size());
	}
	public static List<String> getAgreements(List<String> ids,String date) {
		List<String> agreements = new ArrayList<String>();
//		int count = 0;
		for (int i = 0; i < ids.size(); i++) {
		 String goodsId = ids.get(i);
		 String pre = "http://service.99qh.com/hold2/MemberHold/GetAgreementInfo.aspx?date=";
			String next = "&goodsid=";
			Document doc =null;
			try {
				 doc = Jsoup.connect(pre + date + next + goodsId ).timeout(1000).get();
			} catch (IOException e) {
				System.out.println("timeout");
				e.printStackTrace();
			}
			Elements agreementCodes = doc.select("AgreementCode");		
			if (agreementCodes!=null&&agreementCodes.size()>0) {	
				for (int j = 0; j < agreementCodes.size(); j++) {
					String agreementCode = agreementCodes.get(j).text();
					if (agreementCode.length()>4) {
//						System.out.print(agreementCode+"  ");
						agreements.add(agreementCode);
					}
					
				}
//					count++;
			}
//			System.out.println();
		}
			
		
//		System.out.println("count: "+count +"  "+ result.size());
		return agreements;
	}
}
