package com.scushare.utils;

import java.util.ArrayList;
import java.util.List;

public class SystemTaskManager {
	private static List<SystemTaskControl> taskList;
	
	public static void addThread(SystemTaskControl task) {
		if(taskList == null) {
			taskList = new ArrayList<SystemTaskControl>();
		}
		
		taskList.add(task);
	}
	
	public static void stopTask() {
		for(SystemTaskControl task : taskList) {
			task.stop();
		}
		for(SystemTaskControl task : taskList) {
			try {
				task.getThread().join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
