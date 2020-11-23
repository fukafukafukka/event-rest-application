package com.github.task_manage.domain.repository.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.github.task_manage.domain.model.TaskRevisionHistory;

@Mapper
public interface TaskRevisionHistoryMapper {

	// task_by_userテーブルの最新レコード1件をselectして、task_revision_historyテーブルに投入
	public boolean insertSelectRevision(int userId) throws DataAccessException;

	// task_revision_historyテーブルに1件レコード投入
	public boolean insertRevision(TaskRevisionHistory taskRevisionHistory) throws DataAccessException;

	// task_revision_historyテーブルから1件のタスクに紐づく全履歴レコードを取得
	public List<TaskRevisionHistory> selectRevisionsOnOneTask(int taskId) throws DataAccessException;

	// task_revision_historyテーブルから全タスク履歴レコードを取得
	public List<TaskRevisionHistory> selectAllRevisions(int userId) throws DataAccessException;

	// task_revision_historyテーブルの1件レコード更新
	@Deprecated
	public boolean updateRevision(TaskRevisionHistory taskRevisionHistory) throws DataAccessException;

	// task_revision_historyテーブルから1件レコード削除
	@Deprecated
	public boolean deleteRevision(int taskRevisionId) throws DataAccessException;
}
