package com.scushare.backgroundService;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scushare.entity.FileInfo;
import com.scushare.mapper.SearchKeyWordsMapper;
import com.scushare.mapper.UserSearchMapper;
import com.scushare.service.UserSearchService;
import com.scushare.task.SearchKeyWordTask;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Jedis;

@Component
public class SearchService{

	static final private long delay = 300000;
	static final private long hotKeywordsNum = 10;
	
	@Autowired
	JedisPool jedisPool;
	@Autowired
	SearchKeyWordsMapper searchKeyWordsMapper;
	@Autowired
	UserSearchService userSearchService;
	@Autowired
	UserSearchMapper userSearchMapper;
	
	private Jedis jedis;
	
	@Scheduled(initialDelay = delay, fixedDelay = delay)
	protected void setHotKeyWords() {
		Set<String> hotestKeywrods =  getHotKeywordsSet();
		if(hotestKeywrods == null) {
			return;
		}
		Iterator<String> setIterator = hotestKeywrods.iterator();
		while(setIterator.hasNext()) {
			String keyword = setIterator.next();
			List<String> fileIdList = searchKeyWordsMapper.searchFileIdByKeywords(keyword.split(" "));
			if(fileIdList != null) {
				for(String fileId : fileIdList) {
					userSearchService.getFileInfoById(fileId);
				}
			}
		}
	}
	
	@PostConstruct
	void init() {
		jedis = jedisPool.getResource();
	}
	@PreDestroy
	void destroy() {
		if(jedis != null) {
			jedis.close();
		}
	}
	
	public Set<String> getHotKeywordsSet(){
		if(jedis != null) {
			return jedis.zrevrange(SearchKeyWordTask.KEYWORD_SORTED_SET, 0, hotKeywordsNum - 1);
		}else {
			return null;
		}
	}
	
	@Cacheable(value = "FILE_ID_CACHE", key = "#file_id + '-fid'")
	public FileInfo getFileInfoById(String file_id) {
		return userSearchMapper.getFileInfoById(file_id);
	}
}
