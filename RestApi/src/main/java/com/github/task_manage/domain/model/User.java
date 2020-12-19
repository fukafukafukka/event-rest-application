package com.github.task_manage.domain.model;

import lombok.Data;

@Data
public class User {
	private int userId; //ユーザーID
	private String userName; //ユーザー名
	private String mailAddress; //メールアドレス
	private String password; //パスワード
	private String userRole; //権限
}
