package com.scushare.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scushare.entity.FileInfo;
import com.scushare.entity.RewardTaskAnswerInfo;
import com.scushare.entity.RewardTaskInfo;
import com.scushare.entity.UserUploadRec;
import com.scushare.mapper.RewardTaskMapper;
import com.scushare.mapper.UserRecMapper;
import com.scushare.service.RewardTaskService;
import com.scushare.service.UserInfoService;
import com.scushare.service.UserSearchService;
import com.scushare.utils.DateUtil;

@Controller
public class RewardTaskController {
	
	@Autowired
	RewardTaskService rewardTaskService;
	@Autowired
	UserRecMapper userRecMapper;
	@Autowired
	RewardTaskMapper rewardTaskMapper;
	@Autowired
	UserSearchService userSearchService;
	@Autowired
	UserInfoService userInfoService;
	
	final static int PageSize = 5;
	
	@RequestMapping("/rewardTaskPublishPage")
	public String rewardTaskPublishPage(HttpServletRequest request) {
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
		return "reward_task/rewardTaskPublishPage";
	}
	
	@RequestMapping("/rewardTaskPublish")
	public String rewardTaskPublish(HttpServletRequest request) {
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
		RewardTaskInfo task = new RewardTaskInfo();
		addTaskInfo(task, request);
		Integer result = rewardTaskService.insertRewardTaskInfo(task);
		if(result != 1) {
			return "error";
		}else {
			rewardTaskService.pushTask(task);
			return "redirect:/taskInfoPage?task_id=" + task.getTask_id().toString();
		}
	}
	
	@RequestMapping("/rewardTaskClose")
	public String rewardTaskClose(HttpServletRequest request) {
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
		
		String task_idStr = request.getParameter("task_id");
		String answer_idStr = request.getParameter("answer_id");
		if(task_idStr == null || task_idStr.equals("")) {
			return "error";
		}
		Integer answer_id = null;
		if(answer_idStr == null || answer_idStr.equals("")) {
			answer_id = null;
		}else {
			answer_id = Integer.parseInt(answer_idStr);
		}
		Integer task_id = Integer.parseInt(task_idStr);
		RewardTaskInfo task = rewardTaskService.getTaskInfoById(task_id);
		if(task.getIs_closed() == 1) {
			return "error";
		}
		Integer result = rewardTaskService.updateRewardTaskToClosed(user_id, task_id, answer_id);
		if(result != 1) {
			return "error";
		}
		
		return "forward:/taskInfoPage?task_id=" + task_id.toString();
	}
	
	@RequestMapping("/answerTaskPage")
	public String answerTaskPage(HttpServletRequest request) {
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
		
		String task_idStr = request.getParameter("task_id");
		String currPageStr = request.getParameter("pageNum");
		Integer task_id;
		Integer currPage;
		if(task_idStr == null) {
			return "error";
		}else {
			task_id = Integer.parseInt(task_idStr);
		}
		if(currPageStr == null) {
			currPage = 1;
		}else {
			currPage = Integer.valueOf(currPageStr);
		}
		Integer recCount = userRecMapper.getUploadRecCountByUid(user_id);
		int endIndex = recCount - 1 - (currPage - 1) * PageSize;
		int startIndex = endIndex - PageSize + 1;
		int limitCount = PageSize;
		if(startIndex < 0) {
			startIndex = 0;
			limitCount = recCount;
		}
		RewardTaskInfo task = rewardTaskService.getTaskInfoById(task_id);
		if(task.getUser_id() == user_id || task.getIs_closed() == 1) {
			return "error";
		}
		List<UserUploadRec> recList = userRecMapper.getUploadRecByUid(user_id, startIndex, limitCount);
		Collections.reverse(recList);
		request.setAttribute("startPage", currPage);
		request.setAttribute("maxPageNum", (recCount + PageSize - 1) / PageSize);
		request.setAttribute("recordsCountPerPage", PageSize);
		request.setAttribute("recList", recList);
		request.setAttribute("task", task);
		return "reward_task/answerTaskPage";
	}
	
	@RequestMapping("/answerTask")
	public String answerTask(HttpServletRequest request) {
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return "redirect:/loginPage";
		}
		RewardTaskAnswerInfo answer = new RewardTaskAnswerInfo();
		if(addAnswerInfo(request, answer) == false) {
			return "error";
		}
		RewardTaskInfo task = rewardTaskService.getTaskInfoById(answer.getTask_id());
		if(task.getUser_id() == user_id || task.getIs_closed() == 1) {
			return "error";
		}
		Integer result = rewardTaskService.insertRewardTaskAnswerInfo(answer);
		if(result != 1) {
			return "error";
		}
		
		return "redirect:/taskInfoPage?task_id=" + answer.getTask_id();
	}
	
	@RequestMapping("/searchTaskPage")
	public String searchTaskPage(HttpServletRequest request) {
		List<RewardTaskInfo> recentTaskList = rewardTaskService.getLatestTask();
		request.setAttribute("recentTaskList", recentTaskList);
		
		return "reward_task/searchTaskPage";
	}
	
	@RequestMapping("/searchTaskResultPage")
	public String searchTaskResultPage(HttpServletRequest request) {
		String keywords = request.getParameter("keywords");
		String currPageStr = request.getParameter("currPage");
		int currPage = 1;
		if(currPageStr != null) {
			currPage = Integer.parseInt(currPageStr);
		}
		if(keywords == null || keywords.equals("")) {
			return "forward:/searchTaskPage";
		}
		int startIndex = (currPage - 1) * PageSize;
		int endIndex = startIndex + PageSize - 1;
		List<Integer> taskIdList = rewardTaskService.getTaskIdByKeywords(keywords);
		if(endIndex > taskIdList.size() - 1) {
			endIndex = taskIdList.size() - 1;
		}
		List<RewardTaskInfo> taskList = new ArrayList<RewardTaskInfo>();
		for(int i = startIndex; i <= endIndex; i++) {
			taskList.add(rewardTaskService.getTaskInfoById(taskIdList.get(i)));
		}
		request.setAttribute("startPage", currPage);
		request.setAttribute("maxPageNum", (taskIdList.size() + PageSize - 1) / PageSize);
		request.setAttribute("recordsCountPerPage", PageSize);
		request.setAttribute("keywords", keywords);
		request.setAttribute("taskList", taskList);
		
		return "reward_task/searchTaskResultPage";
	}
	
	@RequestMapping("/taskInfoPage")
	public String taskInfoPage(HttpServletRequest request) {
		String task_idStr = request.getParameter("task_id");
		if(task_idStr == null) {
			return "error";
		}
		Integer task_id = Integer.parseInt(task_idStr);
		RewardTaskInfo task = rewardTaskService.getTaskInfoById(task_id);
		if(task == null) {
			return "error";
		}
		List<RewardTaskAnswerInfo> answerList = rewardTaskService.getAnswerInfoByTaskId(task_id);
		List<FileInfo> fileList = new LinkedList<FileInfo>();
		List<String> userList = new LinkedList<String>();
		Integer closedAnswerId = task.getAnswer_id();
		RewardTaskAnswerInfo closedAnswer = null;
		
		for(RewardTaskAnswerInfo answer: answerList) {
			if(answer.getAnswer_id() == closedAnswerId) {
				closedAnswer = answer;
			}
			fileList.add(userSearchService.getFileInfoById(answer.getFile_id().toString()));
			userList.add(userInfoService.getUserNameById(answer.getUser_id()));
		}

		request.setAttribute("task", task);
		request.setAttribute("closedAnswer", closedAnswer);
		request.setAttribute("answerList", answerList);
		request.setAttribute("fileList", fileList);
		request.setAttribute("userList", userList);
		return "reward_task/taskInfoPage";
	}
	
	private boolean addTaskInfo(RewardTaskInfo task, HttpServletRequest request) {
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		String keywords = request.getParameter("keywords");
		String rewardStr = request.getParameter("reward");
		
		task.setUser_id(user_id);
		if(title == null || title.equals("")) {
			return false;
		}
		task.setTitle(title);
		task.setDescription(description);
		task.setKeywords(keywords);
		task.setDate(DateUtil.getCurrDate());
		if(rewardStr != null) {
			task.setReward(Integer.parseInt(rewardStr));
		}
		
		return true;
	}
	
	private boolean addAnswerInfo(HttpServletRequest request, RewardTaskAnswerInfo answer) {
		Integer user_id = (Integer)request.getSession().getAttribute("user_id");
		if(user_id == null) {
			return false;
		}
		answer.setUser_id(user_id);
		String task_idStr = request.getParameter("task_id");
		if(task_idStr == null) {
			return false;
		}
		answer.setTask_id(Integer.parseInt(task_idStr));
		String file_idStr = request.getParameter("file_id");
		if(file_idStr == null) {
			return false;
		}
		answer.setFile_id(Integer.parseInt(file_idStr));
		answer.setDate(DateUtil.getCurrDate());
		
		return true;
	}
}
