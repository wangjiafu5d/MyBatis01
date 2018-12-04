package com.chuan.mybatis.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.ibatis.io.Resources;

import com.chuan.mybatis.beans.AnalysisResult;
import com.chuan.mybatis.beans.DailyDataSum;
import com.chuan.mybatis.tools.DateTool;
import com.chuan.mybatis.tools.GetDailyData;
import com.chuan.mybatis.tools.GetHoldsFromDB;

public class AnalysisDataClear {
	public static void main(String[] args) {

		Map<String, String> list = new HashMap<String, String>();
		BufferedReader br = null;
		String directoryPath = "C:\\Users\\chuan\\Desktop\\DailyData";
		File dir = new File(directoryPath);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				try {
					br = new BufferedReader(
							new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "utf-8"));
					String line = null;
					while ((line = br.readLine()) != null) {
						list.put(file.getName(),line+"\t"+file.getName());
					}
					br.close();
				} catch (FileNotFoundException e) {
					System.out.println("文件输入流出错");
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
		File file = new File("C:\\Users\\chuan\\desktop\\dailydata.xlsx");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			bw.write(list.get("al.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("cu.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("ni.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("zn.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("bu.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("hc.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("rb.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("fg.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("ma.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("pp.xlsx"));
			bw.write("\r\n");
			bw.write(list.get("ta.xlsx"));
			bw.write("\r\n");
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
