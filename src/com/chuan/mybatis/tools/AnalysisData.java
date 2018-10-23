package com.chuan.mybatis.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
import com.chuan.mybatis.beans.DailyDataSum;
import com.chuan.mybatis.beans.Holds;

public class AnalysisData {
	public static void main(String[] args) {
		List<String> goodsList = new ArrayList<String>();
		goodsList.add("ni");
		goodsList.add("cu");
		goodsList.add("zn");
		goodsList.add("al");
		goodsList.add("ma");
		goodsList.add("bu");
		goodsList.add("ta");
		goodsList.add("fg");
		goodsList.add("pp");
		goodsList.add("rb");
		goodsList.add("hc");
//		goodsList.add("ru");
		String endDate = "2018-10-22";
		for (String goods : goodsList) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					outPutDataToFile(goods, endDate, 295, true);
				}
			});
			thread.start();
		}
	}

	public static AnalysisResult analysis(String goods, String date) {
		AnalysisResult analysisResult = new AnalysisResult();
		analysisResult.setDate(date);
		Map<String, String> nameCodeMap = GetDailyData.getGoodsCode();
		String name = nameCodeMap.get(goods);
		Map<String, DailyDataSum> dailyDataSumMap = GetHoldsFromDB.getDailyDataSum(name, date);
		analysisResult.setAvgPrice(dailyDataSumMap.get(name).getAVGPRICE());
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

	public static void outPutDataToFile(String goods, String endDate, Integer days, Boolean append) {
		File file = new File("C:\\Users\\chuan\\desktop\\DailyData\\" + goods + ".xlsx");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < days; i++) {
			String inputDate = DateTool.dateAdd(endDate, -i);
			Map<String, String> nameCodeMap = GetDailyData.getGoodsCode();
			String name = nameCodeMap.get(goods);
			Map<String, DailyDataSum> dailyDataSumMap = GetHoldsFromDB.getDailyDataSum(name, inputDate);
			if (dailyDataSumMap.get(name) == null) {
				continue;
			}
			if (DateTool.isWeekend(inputDate)) {
				continue;
			}
			try {
				AnalysisResult result = analysis(goods, inputDate);
				if (i == 0 && !append) {
					bw.write("日期" + "\t" + "结算价" + "\t" + "成交前十" + "\t" + "成交二十" + "\t" + "多头前十" + "\t" + "多头二十" + "\t"
							+ "空头前十" + "\t" + "空头二十" + "\t" + "永安成交" + "\t" + "永安多仓" + "\t" + "永安空仓" + "\r\n");
				}
				bw.write(result.getDate() + "\t" + result.getAvgPrice() + "\t" + result.getVolumeTopTen() + "\t"
						+ result.getVolumeTopTwenty() + "\t" + result.getBuyTopTen() + "\t" + result.getBuyTopTwenty()
						+ "\t" + result.getSellTopTen() + "\t" + result.getSellTopTwenty() + "\t" + result.getYAVolume()
						+ "\t" + result.getYABuy() + "\t" + result.getYASell() + "\r\n");
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

//			System.out.println(analysis("ma", date));			
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}