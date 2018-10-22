package com.chuan.mybatis.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;

import com.chuan.mybatis.beans.AgreementInf;
import com.google.gson.Gson;

public class ToJsonArrayTest {
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>(); 
		List<AgreementInf> agreements = new ArrayList<AgreementInf>();
//	    File file = new File("C:\\Users\\chuan\\Desktop\\hy.xlsx");
	    BufferedReader br = null;
	   
	    try {
			br = new BufferedReader(new InputStreamReader(Resources.getResourceAsStream("com\\chuan\\mybatis\\assets\\agreementInf.txt"),"utf-8"));
			String line = null;
		    while ((line = br.readLine())!=null) {		    	
		    	list.add(line.split("	")[0]);
		    	AgreementInf agreementInf = new AgreementInf();
		    	agreementInf.setId(line.split("	")[0]);
		    	agreementInf.setName(line.split("	")[1]);
		    	agreementInf.setCode(line.split("	")[2]);
		    	agreementInf.setOneHandWeight(Integer.valueOf(line.split("	")[3]));
		    	agreementInf.setMarginRate(Double.valueOf(line.split("	")[4]));
		    	agreementInf.setGJRate(Double.valueOf(line.split("	")[5]));
		    	agreements.add(agreementInf);
//		    	System.out.println(line.split("	")[0]);
		    }
		    br.close();
		} catch (FileNotFoundException e) {
			System.out.println("文件输入流出错");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println(agreements.size());
	    Gson gson = new Gson();
	    String jsonArray = gson.toJson(agreements);
	    System.out.println(jsonArray);
	}
}
