package com.chuan.mybatis.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.io.Resources;

public class GetNameCodeMap {
	public static void main(String[] args) {
		Map<String, String> map = getMap();
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println("code: " + entry.getKey() + "  name:" + entry.getValue());
		}
	}

	public static Map<String, String> getMap() {
		Map<String, String> map = new HashMap<String, String>();
		BufferedReader br = null;

		try {
			br = new BufferedReader(new InputStreamReader(
					Resources.getResourceAsStream("com\\chuan\\mybatis\\assets\\hy.xlsx"), "utf-8"));
			String line = null;
			while ((line = br.readLine()) != null) {
				map.put(line.split("	")[2], line.split("	")[1]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("文件输入流出错");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//	    System.out.println(list.size());
		return map;
	}
}
