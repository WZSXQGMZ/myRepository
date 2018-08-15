package com.scushare.task;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.scushare.utils.SpringContextUtil;
import com.scushare.utils.SystemTaskControl;
import com.scushare.utils.SystemTaskManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component("SystemCommandTask")
@Scope("singleton")
public class SystemCommandTask implements Runnable, SystemTaskControl {

	@Autowired
	JedisPool jedisPool;
	
	private Thread thread;
	private Jedis jedis;
	final private static String SYSTEM_COMMAND_QUEUE = "SYSTEM_COMMAND_QUEUE";
	private boolean started = false;
	private boolean excuting = true;
	
	@PostConstruct
	private void Init() {
		thread = new Thread(this);
		jedis = jedisPool.getResource();
	}
	
	public void start() {
		if(started) {
			return;
		}else {
			started = true;
			thread.start();
		}
	}
	
	@Override
	public void run() {
		processMessage();
		
	}

	private void processMessage() {
		while(excuting) {
			List<String> command = jedis.brpop(0, SYSTEM_COMMAND_QUEUE);
			if(command.size() == 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				handleCommand(command.get(0));
			}
		}
	}
	
	private void handleCommand(String command) {
		if(command == null) {
			return;
		}
		if(command.equals("shutdown")) {
			System.out.println("Spring closing...");
			setExcuting(false);
			SystemTaskManager.stopTask();
			SpringContextUtil.getContext().close();
		}
	}
	
	public void setExcuting(boolean excuting) {
		this.excuting = excuting;
	}

	@Override
	public void stop() {
		setExcuting(false);
		
	}
	@Override
	public Thread getThread() {
		return thread;
	}
}
