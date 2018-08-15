package com.scushare.service.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scushare.config.ObjectRedisSerializer;
import com.scushare.entity.RewardTaskAnswerInfo;
import com.scushare.entity.RewardTaskInfo;
import com.scushare.mapper.RewardTaskMapper;
import com.scushare.service.RewardTaskService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service("RewardTaskService")
public class RewardTaskServiceImpl implements RewardTaskService {

	@Autowired
	RewardTaskMapper rewardTaskMapper;
	
	@Autowired
	JedisPool jedisPool;
	
	@Autowired
	ObjectRedisSerializer objectRedisSerializer;
	
	private final static int TASK_INFO_CHANNEL_SIZE	= 10;
	
	public final static byte[] TASK_INFO_CHANNEL = "TASK_INFO_CHANNEL".getBytes();
	
	@Override
	public Integer insertRewardTaskInfo(RewardTaskInfo task) {
		return rewardTaskMapper.insertRewardTaskInfo(task);
	}

	@Override
	public Integer insertRewardTaskAnswerInfo(RewardTaskAnswerInfo answer) {
		Integer answer_id = rewardTaskMapper.selectAnswerIdByUidAndTid(answer.getUser_id(), answer.getTask_id());
		if(answer_id != null) {
			return rewardTaskMapper.updateTaskAnswerInfo(answer_id, answer);
		}else {
			return rewardTaskMapper.insertTaskAnswerInfo(answer);
		}
	}

	@Override
	public Integer updateRewardTaskToClosed(Integer user_id, Integer task_id, Integer answer_id) {
		Integer correctTaskId = rewardTaskMapper.selectTidByAid(answer_id);
		if(correctTaskId != task_id) {
			return 0;
		}
		return  rewardTaskMapper.updateRewardTaskToClosed(user_id, task_id, answer_id);
	}

	@Override
	public RewardTaskInfo getTaskInfoById(Integer task_id) {
		return rewardTaskMapper.selectTaskInfoById(task_id);
	}

	@Override
	public List<Integer> getTaskIdByKeywords(String keywords) {
		if(keywords == null || keywords.equals("")) {
			return new ArrayList<Integer>();
		}else {
			return rewardTaskMapper.selectTaskIdByKeywords(keywords.split(" "));
		}
	}

	@Override
	public List<RewardTaskInfo> getLatestTask() {
		List<RewardTaskInfo> taskList = new LinkedList<RewardTaskInfo>();
		List<byte[]> byteTaskList;
		Jedis jedis = jedisPool.getResource();
		byteTaskList = jedis.lrange(TASK_INFO_CHANNEL, 0, -1);
		for(byte[] taskByte : byteTaskList) {
			taskList.add(objectRedisSerializer.Deserializer(taskByte, RewardTaskInfo.class));
		}
		
		return taskList;
	}

	@Override
	public RewardTaskInfo pushTask(RewardTaskInfo task) {
		Jedis jedis = jedisPool.getResource();
		long length = jedis.llen(TASK_INFO_CHANNEL);
		if(length >= TASK_INFO_CHANNEL_SIZE) {
			jedis.brpop(5000, TASK_INFO_CHANNEL);
		}
		jedis.lpush(TASK_INFO_CHANNEL, objectRedisSerializer.serialize(task));
		jedis.close();
		return task;
		
	}

	@Override
	public List<RewardTaskAnswerInfo> getAnswerInfoByTaskId(Integer task_id) {
		return rewardTaskMapper.selectAnswerInfoByTid(task_id);
	}

	@Override
	public Integer getUserIdByTaskId(Integer task_id) {
		return rewardTaskMapper.selectUidByTid(task_id);
	}

	@Override
	public Integer getTaskIdByAnswerId(Integer answer_id) {
		return rewardTaskMapper.selectTidByAid(answer_id);
	}

}
