package com.github.task_manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.task_manage.domain.model.TaskByUser;
import com.github.task_manage.domain.model.TaskRevisionHistory;
import com.github.task_manage.domain.repository.mybatis.TaskByUserMapper;
import com.github.task_manage.domain.repository.mybatis.TaskRevisionHistoryMapper;

@Service("TaskService")
@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
public class TaskService implements RestService {

	@Autowired
	TaskByUserMapper taskByUserMapper;

	@Autowired
	TaskRevisionHistoryMapper taskRevisionHistoryMapper;

	@Override
	public boolean insertTask(TaskByUser taskByUser) {
		// insert実行
		return taskByUserMapper.insertTask(taskByUser) && taskRevisionHistoryMapper.insertSelectRevision(taskByUser.getUserId());
	}

	@Override
	public List<TaskByUser> selectAllTasks(int userId) {
		// select実行
		return taskByUserMapper.selectAllTasks(userId);
	}

	@Override
	public List<TaskByUser> selectNotYetTasks(int userId) {
		// select実行
		return taskByUserMapper.selectNotYetTasks(userId);
	}

	@Override
	public List<TaskByUser> selectDoneTasks(int userId) {
		// select実行
		return taskByUserMapper.selectDoneTasks(userId);
	}

	@Override
	public List<TaskByUser> selectDeletedTasks(int userId) {
		// select実行
		return taskByUserMapper.selectDeletedTasks(userId);
	}

	@Override
	public boolean updateTask(TaskByUser taskByUser) {
		// update実行
		return taskByUserMapper.updateTask(taskByUser) && taskRevisionHistoryMapper.insertSelectRevision(taskByUser.getUserId());
	}

	@Override
	public List<TaskRevisionHistory> selectRevisionsOnOneTask(int taskId) {
		// select実行
		return taskRevisionHistoryMapper.selectRevisionsOnOneTask(taskId);
	}

	@Override
	public List<TaskRevisionHistory> selectAllRevisions(int userId) {
		// select実行
		return taskRevisionHistoryMapper.selectAllRevisions(userId);
	}

}
