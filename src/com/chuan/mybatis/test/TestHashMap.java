package com.chuan.mybatis.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

import org.apache.ibatis.io.Resources;

public class TestHashMap {
	public static void main(String[] args) {
		HashMap<String, Integer> hashMap = new HashMap<>();
		hashMap.put("aaa", 111);
		hashMap.put("sss", null);
		System.out.println(hashMap.get("ssss")==null);
		System.out.println(hashMap.get("sss")==null);
		List<String> list = new ArrayList<String>(); 
	    File file = new File("com\\chuan\\mybatis\\assets\\hy.xlsx");
	    BufferedReader br = null;
	   
	    try {
			br = new BufferedReader(new InputStreamReader(Resources.getResourceAsStream("com\\chuan\\mybatis\\assets\\hy.xlsx"),"utf-8"));
			String line = null;
		    while ((line = br.readLine())!=null) {		    	
		    	list.add(line.split("	")[0]);
		    	System.out.println(line.split("	")[0]);
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			System.out.println("文件输入流出错");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//	    System.out.println(list.size());
	}
}
