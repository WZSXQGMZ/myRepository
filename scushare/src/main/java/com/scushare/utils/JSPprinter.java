package com.scushare.utils;

/**
 * jsp��ӡ������
 *
 */
public class JSPprinter {
	public static String printPageButton(int currPage, int maxPage,String servletName) {
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
		
		//����ǰһҳ����ҳ
		if(currPage == 1) {
			outputString += getSingleButton(currPage, "class=\"disabled\"", servletName, "&laquo;");
			outputString += getSingleButton(currPage, "class=\"disabled\"", servletName, "&lsaquo;");
		}else {
			outputString += getSingleButton(1, "", servletName, "&laquo;");
			outputString += getSingleButton(currPage - 1, "", servletName, "&lsaquo;");
		}
		
		//����ҳ��ť
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
				outputString += getSingleButton(currPrintPage, "class=\"active\"", servletName, String.valueOf(currPrintPage) + " <span class=\"sr-only\">(current)</span>");
			}else {
				outputString += getSingleButton(currPrintPage, "", servletName, String.valueOf(currPrintPage));
			}
		}
		
		//���ú�һҳ��ĩҳ
		if(currPage == maxPage) {
			outputString += getSingleButton(currPage, "class=\"disabled\"", servletName, "&rsaquo;");
			outputString += getSingleButton(currPage, "class=\"disabled\"", servletName, "&raquo;");
		}else {
			outputString += getSingleButton(currPage + 1, "", servletName, "&rsaquo;");
			outputString += getSingleButton(maxPage, "", servletName, "&raquo;");
		}
		
		return outputString;
	}
	
	private static String getSingleButton(int currPage, String classString, String servletName, String buttonValue) {
		return "<li " + classString + "><a href=\"" + servletName + "?pageNum=" + String.valueOf(currPage) + "\">" + buttonValue + "</a></li>" + "\r\n";
	}
}