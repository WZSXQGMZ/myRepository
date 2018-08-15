package com.scushare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.scushare.entity.RewardTaskAnswerInfo;
import com.scushare.entity.RewardTaskInfo;

public interface RewardTaskMapper {

	
	
	@Insert("INSERT INTO rewardtask(user_id, date, title, description, keywords, is_closed, answer_id, reward)"
			+ " VALUE(#{task.user_id}, #{task.date}, #{task.title}, #{task.description}, #{task.keywords}, #{task.is_closed}, #{task.answer_id}, #{task.reward})")
	@Options(useGeneratedKeys = true, keyProperty = "task.task_id")
	Integer insertRewardTaskInfo(@Param("task") RewardTaskInfo task);
	
	@Insert("INSERT INTO rewardtaskanswer(task_id, user_id, file_id, date) "
			+ " VALUE(#{answer.task_id}, #{answer.user_id}, #{answer.file_id}, #{answer.date})")
	@Options(useGeneratedKeys = true, keyProperty = "answer.answer_id")
	Integer insertTaskAnswerInfo(@Param("answer") RewardTaskAnswerInfo answer);
	
	@Select("SELECT answer_id FROM rewardtaskanswer WHERE user_id = #{user_id} AND task_id = #{task_id}")
	Integer selectAnswerIdByUidAndTid(@Param("user_id") Integer user_id, @Param("task_id") Integer task_id);
	
	@Select("SELECT * FROM rewardtaskanswer WHERE task_id = #{task_id}")
	List<RewardTaskAnswerInfo> selectAnswerInfoByTid(@Param("task_id") Integer task_id);
	
	@Select("SELECT * FROM rewardtask WHERE task_id = #{task_id}")
	RewardTaskInfo selectTaskInfoById(@Param("task_id") Integer task_id);
	
	@Select("SELECT task_id FROM rewardtaskanswer WHERE answer_id = #{answer_id}")
	Integer selectTidByAid(@Param("answer_id") Integer answer_id);
	
	@Select("SELECT user_id FROM rewardtask WHERE task_id = #{task_id}")
	Integer selectUidByTid(Integer task_id);
	
	@Select({"<script>",
			  "SELECT task_id "
			+ "FROM "
			+ " (SELECT task_id, CONCAT(title,description,keywords) AS result"
			+ " FROM "
			+ "  rewardtask) AS concat_table "
			+ "WHERE"
			+ " <foreach collection=\"keywords\" item=\"keyword\"  open=\"\" separator=\"AND\" close=\"\">"
			+ " result LIKE CONCAT('%', #{keyword}, '%')"
			+ " </foreach>"
			,"</script>"})
	List<Integer> selectTaskIdByKeywords(@Param("keywords") String[] keywords);
	
	@Update("UPDATE rewardtask SET is_closed = '1', answer_id = #{answer_id,jdbcType=INTEGER} WHERE user_id = #{user_id} AND task_id = #{task_id}")
	Integer updateRewardTaskToClosed(@Param("user_id") Integer user_id, @Param("task_id") Integer task_id, @Param("answer_id") Integer answer_id);
	
	@Update("UPDATE rewardtaskanswer SET file_id = #{answer.file_id}, date = #{answer.date} WHERE answer_id = #{answer_id}")
	Integer updateTaskAnswerInfo(@Param("answer_id") Integer answer_id, @Param("answer") RewardTaskAnswerInfo answer);
	
}
