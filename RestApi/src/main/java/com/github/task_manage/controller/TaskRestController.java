package com.github.task_manage.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.task_manage.domain.model.TaskByUser;
import com.github.task_manage.domain.model.TaskRevisionHistory;
import com.github.task_manage.service.RestService;

@RestController
public class TaskRestController {

	@Autowired
    @Qualifier("TaskService")
    RestService service;

	/**
     * タスク全件取得
     */
    @GetMapping("/rest/getAllTasks/{id:.+}")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public List<TaskByUser> getAllTasks(@PathVariable("id") int userId) {

        // タスク全件取得
        return service.selectAllTasks(userId);
    }

    /**
     * 未完了・未削除の全件タスクを取得するメソッド
     */
    @GetMapping("/rest/getNotYetTasks/{id:.+}")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public List<TaskByUser> getNotYetTasks(@PathVariable("id") int userId) {

        // 未完了・未削除のタスク全取得
        return service.selectNotYetTasks(userId);
    }

    /**
     * 完了した全タスクを取得するメソッド
     */
    @GetMapping("/rest/getDoneTasks/{id:.+}")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public List<TaskByUser> getDoneTasks(@PathVariable("id") int userId) {

        // 完了した全タスクを取得
        return service.selectDoneTasks(userId);
    }

    /**
     * 削除した全タスクを取得するメソッド
     */
    @GetMapping("/rest/getDeletedTasks/{id:.+}")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public List<TaskByUser> getDeletedTasks(@PathVariable("id") int userId) {

        // 削除した全タスクを取得
        return service.selectDeletedTasks(userId);
    }

    /**
     * タスク１件登録
     */
    @PostMapping("/rest/insert")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public String postUserOne(@RequestBody TaskByUser taskByUser) {

        // タスクを１件登録
        boolean result = service.insertTask(taskByUser);

        if(result == true) {
            return "{\"result\":\"ok\"}";
        } else {
            return "{\"result\":\"error\"}";
        }
    }

    /**
     * タスク１件更新
     */
    @PutMapping("/rest/update")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public String putTaskOne(@RequestBody TaskByUser taskByUser) {
        // タスクを１件登録
        boolean result = service.updateTask(taskByUser);

        if(result == true) {
        	return "{\"result\":\"ok\"}";
        } else {
        	return "{\"result\":\"error\"}";
        }
    }

    /**
     * 1件のタスクに紐づく全履歴を取得
     */
    @GetMapping("/rest/getAllRevisionsOnOneTask/{id:.+}")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public List<TaskRevisionHistory> getAllRevisionsOnOneTask(@PathVariable("id") int taskId) {

        // タスク全件取得
        return service.selectRevisionsOnOneTask(taskId);
    }

    /**
     * 1人のユーザーに紐づく全履歴を取得
     */
    @GetMapping("/rest/getAllRevisions/{id:.+}")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public List<TaskRevisionHistory> getAllRevisions(@PathVariable("id") int userId) {

        // タスク全件取得
        return service.selectAllRevisions(userId);
    }
}
