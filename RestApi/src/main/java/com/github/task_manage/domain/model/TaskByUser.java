package com.github.task_manage.domain.model;

import lombok.Data;

@Data
public class TaskByUser {
	private int taskId; //タスクID
	private int userId; //ユーザーID
	private String taskName; //タスク名
	private String taskDetail; //タスク詳細
	private int doneFlag; //タスク未完了なら0、タスク完了なら0以外の-127から127の値
	private int deleteFlag; //タスク削除フラグ
}
