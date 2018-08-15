package com.scushare.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.scushare.service.KeyWordsService;
import redis.clients.jedis.Jedis;

@Service
public class KeyWordsServiceImpl implements KeyWordsService{
	
	public final static String MESSAGE_KEY = "SEARCH_KEY_WORD";
	
	private Jedis jedis;
	
	@PostConstruct
	void Init() {
		jedis = new Jedis("localhost", 7379, 5000);
	}
	@PreDestroy
	void destroy() {
		if(jedis != null) {
			jedis.close();
		}
	}
	
	@Override
	public void handleKeyWord(String[] keywords) {
		if(keywords == null) {
			return;
		}
		for(String keyword : keywords) {
			jedis.lpush(MESSAGE_KEY, keyword);
		}
	}
}
