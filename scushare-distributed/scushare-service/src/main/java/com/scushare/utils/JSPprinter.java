package com.scushare.utils;

/**
 * jsp打印工具类
 *
 */
public class JSPprinter {
	public static String printPageButton(int currPage, int maxPage,String servletName) {
		return printPageButton(currPage, maxPage, servletName, "");
	}
	public static String printPageButton(int currPage, int maxPage,String servletName, String param) {
		if(maxPage < 1) {
			return "";
		}
		if(currPage < 1 || currPage > maxPage) {
			return "";
		}
		if(servletName == null) {
			return "";
		}
		
		String outputString = "<br />\r\n<ul class=\"pagination\">\r\n";
		int buttonCount = 5;
		
		if(maxPage < 5) {
			buttonCount = maxPage;
		}
		int halfButtonCount = buttonCount / 2;
		
		//设置前一页和首页
		if(currPage == 1) {
			outputString += getSingleButton(currPage, "class=\"disabled\"", servletName, "&laquo;", param);
			outputString += getSingleButton(currPage, "class=\"disabled\"", servletName, "&lsaquo;", param);
		}else {
			outputString += getSingleButton(1, "", servletName, "&laquo;", param);
			outputString += getSingleButton(currPage - 1, "", servletName, "&lsaquo;", param);
		}
		
		//设置页按钮
		int firstPage = currPage - halfButtonCount;
		if(firstPage < 1) {
			firstPage = 1;
		}
		if(maxPage - currPage < (halfButtonCount) && firstPage != 1) {
			firstPage = maxPage - buttonCount + 1;
		}
		if(firstPage < 1) {
			firstPage = 1;
		}
		
		for(int i = 0; i < buttonCount; i++) {
			int currPrintPage = firstPage + i;
			if((currPrintPage) == currPage) {
				outputString += getSingleButton(currPrintPage, "class=\"active\"", servletName, String.valueOf(currPrintPage) + " <span class=\"sr-only\">(current)</span>", param);
			}else {
				outputString += getSingleButton(currPrintPage, "", servletName, String.valueOf(currPrintPage), param);
			}
		}
		
		//设置后一页和末页
		if(currPage == maxPage) {
			outputString += getSingleButton(currPage, "class=\"disabled\"", servletName, "&rsaquo;", param);
			outputString += getSingleButton(currPage, "class=\"disabled\"", servletName, "&raquo;", param);
		}else {
			outputString += getSingleButton(currPage + 1, "", servletName, "&rsaquo;", param);
			outputString += getSingleButton(maxPage, "", servletName, "&raquo;", param);
		}
		
		return outputString;
	}
	
	private static String getSingleButton(int currPage, String classString, String servletName, String buttonValue, String param) {
		return "<li " + classString + "><a href=\"" + servletName + "?pageNum=" + String.valueOf(currPage) + param + "\">" + buttonValue + "</a></li>" + "\r\n";
	}
}
