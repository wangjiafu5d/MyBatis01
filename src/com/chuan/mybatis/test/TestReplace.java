package com.chuan.mybatis.test;

import com.chuan.mybatis.tools.TextTool;

public class TestReplace {
	public static void main(String[] args) {
		String str = "AFGF FDG	FDSAF";
		System.out.println(TextTool.replaceSpecialStr(str));
	}
}
