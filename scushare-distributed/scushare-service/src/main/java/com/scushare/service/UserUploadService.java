package com.scushare.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserUploadService {
	UserUploadResult doUpload(HttpServletRequest request, HttpServletResponse response);
	UserUploadResult doUploadChathead(HttpServletRequest request, HttpServletResponse response);
	Long getUploadProgress(HttpServletRequest request);
}
