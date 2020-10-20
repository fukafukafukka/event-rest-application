package com.github.task_manage.domain.model;

import lombok.Data;

@Data
public class Task {
	private int taskId; //タスクID
	private String taskName; //タスク名
	private String taskDetail; //タスク詳細
	private String status; //タスク進捗状況
}
