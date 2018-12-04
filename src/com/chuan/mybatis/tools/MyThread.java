package com.chuan.mybatis.tools;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MyThread implements Runnable{
	CountDownLatch cdl;
	@Override
	public void run() {
		cdl.countDown();
	}
	public void set(String goodsId, String date,List<String> agreements,CountDownLatch cdl) {		
		// System.out.println(ids.size());
		this.cdl = cdl;
		String pre = "http://service.99qh.com/hold2/MemberHold/GetAgreementInfo.aspx?date=";
		String next = "&goodsid=";
		Document doc =null;
		try {				
//			System.out.println(pre + date + next + goodsId);
			doc = Jsoup.connect(pre + date + next + goodsId ).timeout(5000).get();
//			System.out.println(doc==null);
		} catch (IOException e) {
			System.out.println("timeout");
			e.printStackTrace();
		} 
		Elements agreementCodes = doc.select("AgreementCode");		
		if (agreementCodes!=null&&agreementCodes.size()>0) {	
			for (int j = 0; j < agreementCodes.size(); j++) {
				String agreementCode = agreementCodes.get(j).text();
				if (agreementCode.length()>4) {
//				System.out.print(agreementCode+"  ");
					agreements.add(agreementCode);
				}
				
			}
//			count++;
		}
//	System.out.println();
	}

}
