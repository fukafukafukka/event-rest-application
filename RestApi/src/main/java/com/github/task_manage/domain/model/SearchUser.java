package com.github.task_manage.domain.model;

import lombok.Data;

@Data
public class SearchUser {
	private String mailAddress; //メールアドレス
	private String password; //パスワード
}
