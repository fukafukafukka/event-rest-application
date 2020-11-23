package com.github.task_manage.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.github.task_manage.domain.model.TaskByUser;

@Mapper
public interface TaskByUserMapper {

	// 1件レコード投入
    public boolean insertTask(TaskByUser taskByUser) throws DataAccessException;

	// userIdに紐づく全レコードを取得
	public List<TaskByUser> selectAllTasks(int userId) throws DataAccessException;

	// 未完了かつ未削除の全レコードを取得
	public List<TaskByUser> selectNotYetTasks(int userId) throws DataAccessException;

	// 完了した全レコードを取得
	public List<TaskByUser> selectDoneTasks(int userId) throws DataAccessException;

	// 削除した全レコードを取得
	public List<TaskByUser> selectDeletedTasks(int userId) throws DataAccessException;

	// TaskByUserテーブルのレコード1件更新
    public boolean updateTask(TaskByUser taskByUser) throws DataAccessException;

    // 1件レコード削除
    @Deprecated
    public boolean deleteTask(int taskId) throws DataAccessException;
}
