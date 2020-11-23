package com.github.task_manage.service;

import java.util.List;

import com.github.task_manage.domain.model.TaskByUser;
import com.github.task_manage.domain.model.TaskRevisionHistory;

public interface RestService {

	//１件タスクを登録するメソッド
    public boolean insertTask(TaskByUser taskByUser);

    //全件タスクを取得するメソッド
    public List<TaskByUser> selectAllTasks(int userId);

    //未完了・未削除の全タスクを取得するメソッド
    public List<TaskByUser> selectNotYetTasks(int userId);

    //完了した全タスクを取得するメソッド
    public List<TaskByUser> selectDoneTasks(int userId);

    //削除した全タスクを取得するメソッド
    public List<TaskByUser> selectDeletedTasks(int userId);

    //１件タスクを更新するメソッド
    public boolean updateTask(TaskByUser taskByUser);

    //全件履歴を取得するメソッド
    public List<TaskRevisionHistory> selectRevisionsOnOneTask(int taskId);

    //全件履歴を取得するメソッド
    public List<TaskRevisionHistory> selectAllRevisions(int userId);

}
