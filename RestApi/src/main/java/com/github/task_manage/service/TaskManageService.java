package com.github.task_manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.task_manage.domain.model.Task;
import com.github.task_manage.domain.repository.mybatis.TaskMapper;

@Service("TaskManageService")
public class TaskManageService implements RestService {

	@Autowired
	TaskMapper taskMapper;

	@Override
	public boolean insertOne(Task task) {
		// insert実行
		return taskMapper.insertOne(task);
	}

	@Override
	public Task selectOne(int taskId) {
		// select実行
		return taskMapper.selectOne(taskId);
	}

	@Override
	public List<Task> selectMany() {
		// select実行
		return taskMapper.selectMany();
	}

	@Override
	public boolean updateOne(Task task) {
		// update実行
		return taskMapper.updateOne(task);
	}

	@Override
	public boolean deleteOne(int taskId) {
		// delete実行
		return taskMapper.deleteOne(taskId);
	}

}
