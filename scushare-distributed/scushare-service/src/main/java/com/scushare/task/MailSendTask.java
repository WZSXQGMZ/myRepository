package com.scushare.task;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.scushare.service.UserRegisterService;
import com.scushare.utils.SystemTaskControl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component("MailSendTask")
@Scope("singleton")
public class MailSendTask implements Runnable, SystemTaskControl{
	
	@Autowired
	JedisPool jedisPool;
	private Jedis jedis;
	
	@Autowired
	UserRegisterService userRegisterService;
	
	final public static String MAIL_SEND_QUEUE = "MAIL_SEND_QUEUE";
	
	private boolean excuting = true;
	private Thread thread;
	
	private boolean started = false;
	
	@PostConstruct
	void init() {
		thread = new Thread(this);
		jedis = new Jedis("localhost", 7379, 5000);
	}
	
	@PreDestroy
	void destroy() {
		if(jedis != null) {
			jedis.close();
		}
	}
	
	public void start() {
		if(started) {
			return;
		}else {
			started = false;
			thread.start();
		}
	}
	
	@Override
	public void stop() {
		excuting = false;
		
	}

	@Override
	public Thread getThread() {
		return thread;
	}

	@Override
	public void run() {
		while(excuting) {
			processMessage();
		}
	}
	
	private void processMessage() {
		List<String> messageList = jedis.brpop(0, MAIL_SEND_QUEUE);
		if(messageList == null || messageList.size() < 2) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			handleMessage(messageList.get(1));
		}
	}
	
	private void handleMessage(String message) {
		String[] messages = message.split(",");
		userRegisterService.sendConfirmMail(messages[0], messages[1]);
	}
}
