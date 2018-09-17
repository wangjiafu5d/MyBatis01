package com.chuan.mybatis.tools;

import java.util.HashMap;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

public class TestHashMap {
	public static void main(String[] args) {
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("aaa", 111);
		hashMap.put("sss", null);
		System.out.println(hashMap.get("ssss")==null);
		System.out.println(hashMap.get("sss")==null);
	}
}
