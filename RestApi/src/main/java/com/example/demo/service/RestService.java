package com.example.demo.service;

import java.util.List;

import com.example.demo.domain.model.Task;

public interface RestService {

	//１件登録用メソッド
    public boolean insertOne(Task task);

    //１件検索用メソッド
    public Task selectOne(int taskNumber);

    //全件検索用メソッド
    public List<Task> selectMany();

    //１件更新用メソッド
    public boolean updateOne(Task task);

    //１件削除用メソッド
    public boolean deleteOne(int taskNumber);
}
