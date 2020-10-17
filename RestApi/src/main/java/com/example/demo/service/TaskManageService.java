package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.model.Task;
import com.example.demo.domain.repository.mybatis.TaskMapper;

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
	public Task selectOne(int taskNumber) {
		// select実行
		return taskMapper.selectOne(taskNumber);
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
	public boolean deleteOne(int taskNumber) {
		// delete実行
		return taskMapper.deleteOne(taskNumber);
	}

}
