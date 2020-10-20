package com.github.task_manage.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.github.task_manage.domain.model.Task;

@Mapper
public interface TaskMapper {

	// task_manageテーブルにデータを1件insert.
    public boolean insertOne(Task task) throws DataAccessException;

	// task_manageテーブルデータを1件取得
	public Task selectOne(int taskNumber) throws DataAccessException;

	// task_manageテーブルの全データを取得
	public List<Task> selectMany() throws DataAccessException;

	// task_manageテーブルを１件更新.
    public boolean updateOne(Task task) throws DataAccessException;

    // task_manageテーブルを１件削除.
    public boolean deleteOne(int taskNumber) throws DataAccessException;
}
