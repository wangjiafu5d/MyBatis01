package com.chuan.mybatis.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GetIds {
	public static void main(String[] args) {
		getIds();
	}
	public static List<String> getIds(){
		List<String> list = new ArrayList<String>(); 
	    File file = new File("C:\\Users\\chuan\\Desktop\\hy.xlsx");
	    BufferedReader br = null;
	   
	    try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			String line = null;
		    while ((line = br.readLine())!=null) {		    	
		    	list.add(line.split("	")[0]);
//		    	System.out.println(line.split("	")[0]);
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			System.out.println("文件输入流出错");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//	    System.out.println(list.size());
	    return list;
	}
}
