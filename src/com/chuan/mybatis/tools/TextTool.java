package com.chuan.mybatis.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextTool {
	public static boolean isEmpty(String string) {
		String reg = "[\\da-z\\u2E80-\\u9FFF]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(string);
		if (m.find()) {
			return false;
		} else {
			// System.out.print("一个空白字符串");
			return true;
		}
	}
	public static String agreementMatch(String agreement) {
		String result = "";		
		String reg = "[0-9]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(agreement);
		if (m.find()) {
			result = m.group(0);
		} else {
			System.out.println("未匹配到合约名");
		}
		return result;
	}
	public static String priceMatch(String price) {
		String result = "";		
		String reg = "^[0-9.]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(price);
		if (m.find()) {
			result = m.group(0);
		} else {
			System.out.println("未匹配到价格");
		}
		return result;
	}
	public static String goodsCodeMatch(String agreement) {
		String goodsCode = "";
		String reg = "^[a-zA-Z]+";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(agreement);
		if (m.find()) {
			goodsCode = m.group(0);
		} else {
			System.out.println("未匹配到商品名称");
		}
		return goodsCode;
	}
	//去字符串空白字符
	public static String replaceSpecialStr(String str) {
        String repl = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            repl = m.replaceAll("");
        }
        return repl;
    }
}
