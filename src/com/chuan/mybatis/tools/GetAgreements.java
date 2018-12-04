package com.chuan.mybatis.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GetAgreements {
	public static void main(String[] args) {
//		List<String> l = new ArrayList<>();
//		l.add("6");
//		l.add("7");
		List<String> result = getAgreements(GetIds.getIds(), "2018-09-14");
		for (String string : result) {
			System.out.println(string);
		}
		System.out.println(result.size());
	}

	public static List<String> getAgreements(List<String> ids,String date) {
		List<String> agreements = new ArrayList<String>();
//		int count = 0;
		CountDownLatch countDownLatch = new CountDownLatch(ids.size());
		for (int i = 0; i < ids.size(); i++) {
			String goodsId = ids.get(i);
			MyThread t = new MyThread();
			t.set(goodsId, date, agreements, countDownLatch);
			t.run();
		}
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		System.out.println("count: "+count +"  "+ result.size());
		return agreements;
	}

	
}
