package com.chuan.mybatis.tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.chuan.mybatis.beans.AnalysisResult;
import com.chuan.mybatis.beans.Holds;

public class AnalysisData {
	public static void main(String[] args) {
		System.out.println(analysis("cu", "2018-09-05"));;
	}

	public static AnalysisResult analysis(String goods, String date) {
		AnalysisResult analysisResult = new AnalysisResult();
		analysisResult.setDate(date);
		List<Holds> holdsList = GetHoldsFromDB.getHolds(goods, date);
		Map<String, Integer> volumeList = new HashMap<String, Integer>();
		Map<String, Integer> buyList = new HashMap<String, Integer>();
		Map<String, Integer> sellList = new HashMap<String, Integer>();
		for (Holds holds : holdsList) {
			String compName = holds.getCompName();
			Integer value = holds.getValue();
			switch (holds.getType()) {
			case "成交量":
				if (!(volumeList.get(compName) == null)) {
					value = volumeList.get(compName) + value;
				}
				volumeList.put(compName, value);
				break;
			case "多仓量":
				if (!(buyList.get(compName) == null)) {
					value = buyList.get(compName) + value;
				}
				buyList.put(compName, value);
				break;
			case "空仓量":
				if (!(sellList.get(compName) == null)) {
					value = sellList.get(compName) + value;
				}
				sellList.put(compName, value);
				break;
			}
		}
		String YAName = "永安期货";
		analysisResult.setYAVolume(volumeList.get(YAName));
		analysisResult.setYABuy(buyList.get(YAName));
		analysisResult.setYASell(sellList.get(YAName));
		
		LinkedHashMap<String, Integer> volumeLinked = sortMap(volumeList);
		LinkedHashMap<String, Integer> buyLinked = sortMap(buyList);
		LinkedHashMap<String, Integer> sellLinked = sortMap(sellList);
		
		analysisResult.setVolumeTopTen(sumTop(volumeLinked, 10));
		analysisResult.setVolumeTopTwenty(sumTop(volumeLinked, 20));
		analysisResult.setBuyTopTen(sumTop(buyLinked, 10));
		analysisResult.setBuyTopTwenty(sumTop(buyLinked, 20));
		analysisResult.setSellTopTen(sumTop(sellLinked, 10));
		analysisResult.setSellTopTwenty(sumTop(sellLinked, 20));

		return analysisResult;
	}

	public static LinkedHashMap<String, Integer> sortMap(Map<String, Integer> map) {
		LinkedHashMap<String, Integer> resultMap = new LinkedHashMap<String, Integer>();
		List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		for (Entry<String, Integer> entry : list) {
			resultMap.put(entry.getKey(), entry.getValue());
		}
		return resultMap;
	}

	public static Integer sumTop(LinkedHashMap<String, Integer> map, int count) {
		Integer sum = 0;
		int times = 0;
		Iterator<Integer> iterator = map.values().iterator();
		while (iterator.hasNext() && times < count) {			
			sum = sum + iterator.next();
			times++;
		}
		return sum;
	}
}