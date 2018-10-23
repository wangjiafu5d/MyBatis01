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
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;

import com.chuan.mybatis.beans.AnalysisResult;
import com.chuan.mybatis.beans.DailyDataSum;
import com.chuan.mybatis.tools.DateTool;
import com.chuan.mybatis.tools.GetDailyData;
import com.chuan.mybatis.tools.GetHoldsFromDB;

public class AnalysisDataClear {
	public static void main(String[] args) {

		List<String> list = new ArrayList<String>();
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
						list.add(line);
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
			for (String string : list) {
				bw.write(string + "\r\n");
			}
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
