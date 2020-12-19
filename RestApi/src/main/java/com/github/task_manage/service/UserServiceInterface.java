package com.github.task_manage.service;

import com.github.task_manage.domain.model.SearchUser;
import com.github.task_manage.domain.model.User;

public interface UserServiceInterface {

	//１件ユーザーを登録するメソッド
    public boolean insert(User user);

    //全件ユーザーを取得するメソッド
    public User selectUserByAddressAndPassword(SearchUser searchUser);

    //１件ユーザーを更新するメソッド
    public boolean update(User user);

    //ユーザーを削除するメソッド
    public boolean delete(int userId);

}
