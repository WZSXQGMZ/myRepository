package com.scushare.task;

import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.scushare.entity.UserSearchKeyword;
import com.scushare.mapper.SearchKeyWordsMapper;
import com.scushare.service.impl.KeyWordsServiceImpl;
import com.scushare.utils.SystemTaskControl;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * 处理搜索关键词的线程类
 * @author xh
 *
 */
@Component("SearchKeyWordTask")
@Scope("singleton")
public class SearchKeyWordTask implements Runnable, SystemTaskControl{
	@Autowired
	SearchKeyWordsMapper searchKeyWordsMapper;
	@Autowired
	JedisPool jedisPool;
	
	private Thread thread;
	
	private Jedis jedis = null;
	private Jedis listeningJedis = null;
	
	final public static String KEYWORD_SORTED_SET = "KEYWORD_CACHE";
	
	final private static long ONE_DAY_MILLIS = 86400000;
	private long baseDateMillis = 0;
	private long nextDateMillis = 0;
	
	private boolean started = false;
	private volatile boolean STOP_RUNNING = false;
	
	SearchKeyWordTask() {
		this.thread = new Thread(this);
	}
	
	public void start() {
		if(thread.isAlive() == false || started == false) {
			started = true;
			thread.start();
		}
	}
	
	public synchronized void setSaveFlag(boolean flag) {
		this.STOP_RUNNING = flag;
	}
	
	@Override
	public void run() {
		System.out.println("\nSearch Task Running.");
		try {
			baseDateMillis = System.currentTimeMillis();
			baseDateMillis = baseDateMillis - baseDateMillis % ONE_DAY_MILLIS;
			nextDateMillis = baseDateMillis + ONE_DAY_MILLIS;

			getKeywordsFromDB(baseDateMillis);
			processMessage();

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	void init() {
		listeningJedis = new Jedis("localhost", 7379, 5000);
	}
	
	@PreDestroy
	public void destroy() throws Exception {
		STOP_RUNNING = true;
		saveKeywords();
		if(listeningJedis != null) {
			listeningJedis.close();
		}
		System.out.println("\nSearch Task Closed");
	}
	
	private void processMessage() {
		while(true) {
			if(nextDateMillis <= System.currentTimeMillis()){
				saveKeywords();
				clearKeywords();
				baseDateMillis = nextDateMillis;
				nextDateMillis = baseDateMillis + ONE_DAY_MILLIS;
			}else if(STOP_RUNNING){
				break;
			}else {
				List<String> words = listeningJedis.brpop(0, KeyWordsServiceImpl.MESSAGE_KEY);
				if(words != null && words.size() > 1) {
					System.out.println("Keyword Task processing message");
					handleKeyWord(words.get(1));
				}else {
					try {
						System.out.println("Keyword Task sleeping");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}
	
	
	private void handleKeyWord(String keyword) {
		synchronized (KEYWORD_SORTED_SET) {
			listeningJedis.zincrby(KEYWORD_SORTED_SET, 1, keyword);
		}
		System.out.println(keyword + " processed");

	}
	
	//保存关键字到mysql
	private synchronized void saveKeywords() {
		System.out.println("saving words");
		//暂时锁住KEYWORD_SORTED_SET
		synchronized (KEYWORD_SORTED_SET) {
			//生成date
			Date date = new Date(baseDateMillis);
			//从redis获取集合
			jedis = jedisPool.getResource();
			Set<Tuple> keywordSet = jedis.zrangeWithScores(KEYWORD_SORTED_SET, 0, -1);
			jedis.close();
			//获取遍历器
			Iterator<Tuple> setIterator = keywordSet.iterator();
			while(setIterator.hasNext()) {
				try {
					Tuple tuple = setIterator.next();
					String keyword = tuple.getElement();
					Integer search_times = (int)tuple.getScore();
					//插入数据
					searchKeyWordsMapper.insertKeyWordSearch(keyword, search_times, date);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	//半小时保存一次关键词
	@Scheduled(initialDelay = 1800000, fixedDelay = 1800000)
	private void scheduleSaveKeywords() {
		saveKeywords();
	}
	
	private void clearKeywords() {
		synchronized (KEYWORD_SORTED_SET) {
			System.out.println("cleaning keywords");
			jedis.zremrangeByRank(KEYWORD_SORTED_SET, 0, -1);
		}
	}
	
	private void getKeywordsFromDB(long currMillis) {
		Date date = new Date(currMillis);
		List<UserSearchKeyword> keywordsList = searchKeyWordsMapper.selectKeywordsByDate(date);
		synchronized (KEYWORD_SORTED_SET) {
			try {
				for(UserSearchKeyword userSearchKeyword : keywordsList) {
					listeningJedis.zincrby(KEYWORD_SORTED_SET, (double)userSearchKeyword.getSearch_times(), userSearchKeyword.getKey_word());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void stop() {
		STOP_RUNNING = true;
	}
	
	@Override
	public Thread getThread() {
		return thread;
	}
}
