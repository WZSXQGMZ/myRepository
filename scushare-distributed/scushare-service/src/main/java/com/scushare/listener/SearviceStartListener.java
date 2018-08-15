package com.scushare.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.scushare.task.MailSendTask;
import com.scushare.task.SearchKeyWordTask;
import com.scushare.utils.SystemTaskManager;

@WebListener
public class SearviceStartListener implements ServletContextListener {

	@Autowired
	SearchKeyWordTask searchKeyWordTask;
	
	@Autowired
	MailSendTask mailSendTask;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("\ndetect context inited");
		//systemCommandTask.start();
		mailSendTask.start();
		searchKeyWordTask.start();
		
		SystemTaskManager.addThread(mailSendTask);
		SystemTaskManager.addThread(searchKeyWordTask);
		//SystemTaskManager.addThread();
	}

}
