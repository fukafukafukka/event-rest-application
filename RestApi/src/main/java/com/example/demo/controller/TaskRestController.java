package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.model.Task;
import com.example.demo.service.RestService;

@RestController
public class TaskRestController {

	@Autowired
    @Qualifier("TaskManageService")
    RestService service;

	/**
     * タスク全件取得
     */
    @GetMapping("/rest/get")
    public List<Task> getTaskMany() {

        // タスク全件取得
        return service.selectMany();
    }

    /**
     * タスク１件取得
     */
    @GetMapping("/rest/get/{id:.+}")
    public Task getTaskOne(@PathVariable("id") int taskNumber) {

        // タスク１件取得
        return service.selectOne(taskNumber);
    }

    /**
     * タスク１件登録
     */
    @PostMapping("/rest/insert")
    public String postUserOne(@RequestBody Task task) {

        // タスクを１件登録
        boolean result = service.insertOne(task);

        String str = "";

        if(result == true) {
            str = "{\"result\":\"ok\"}";
        } else {
            str = "{\"result\":\"error\"}";
        }

        // 結果用の文字列をリターン
        return str;
    }

    /**
     * タスク１件登録
     */
    @PutMapping("/rest/update")
    public String putTaskOne(@RequestBody Task task) {

        // タスクを１件登録
        boolean result = service.updateOne(task);

        String str = "";

        if(result == true) {
            str = "{\"result\":\"ok\"}";
        } else {
            str = "{\"result\":\"error\"}";
        }

        // 結果用の文字列をリターン
        return str;
    }

    @DeleteMapping("/rest/delete/{id:.+}")
    public String deleteTaskOne(@PathVariable("id") int taskNumber) {

        // タスクを１件削除
        boolean result = service.deleteOne(taskNumber);

        String str = "";

        if(result == true) {
            str = "{\"result\":\"ok\"}";
        } else {
            str = "{\"result\":\"error\"}";
        }

        // 結果用の文字列をリターン
        return str;
    }
}
