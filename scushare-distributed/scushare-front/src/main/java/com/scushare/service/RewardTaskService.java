package com.scushare.service;

import java.util.List;
import com.scushare.entity.RewardTaskAnswerInfo;
import com.scushare.entity.RewardTaskInfo;

public interface RewardTaskService {
	Integer insertRewardTaskInfo(RewardTaskInfo task);
	Integer insertRewardTaskAnswerInfo(RewardTaskAnswerInfo answer);
	Integer updateRewardTaskToClosed(Integer user_id, Integer task_id, Integer answer_id);
	RewardTaskInfo getTaskInfoById(Integer task_id);
	List<Integer> getTaskIdByKeywords(String keywords);
	List<RewardTaskInfo> getLatestTask();
	RewardTaskInfo pushTask(RewardTaskInfo task);
	List<RewardTaskAnswerInfo> getAnswerInfoByTaskId(Integer task_id);
	Integer getUserIdByTaskId(Integer task_id);
	Integer getTaskIdByAnswerId(Integer answer_id);
}
