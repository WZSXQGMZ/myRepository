package com.scushare.service.impl;

import java.util.concurrent.ConcurrentHashMap;

public class UploadProgress {
	private static ConcurrentHashMap<String, Object> progressMap = new ConcurrentHashMap<String, Object>();
	
	public static Object getProgress(String id) {
		return progressMap.get(id);
	}
	public static void putProgress(String id, Object progress) {
		progressMap.put(id, progress);
	}
	public static void removeProgress(String id) {
		progressMap.remove(id);
	}
}
