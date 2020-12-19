package com.github.task_manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.task_manage.domain.model.SearchUser;
import com.github.task_manage.domain.model.User;
import com.github.task_manage.service.UserServiceInterface;

@RestController
@RequestMapping(value = {"user"})
public class UserController {

	@Autowired
    @Qualifier("UserService")
    UserServiceInterface service;

    /**
     * ユーザー１件登録
     */
    @PostMapping("/insert")
    @CrossOrigin(allowCredentials = "true")
    public String postUser(@RequestBody User user) {

        // ユーザーを１件登録
        boolean result = service.insert(user);

        if(result == true) {
            return "{\"result\":\"ok\"}";
        } else {
            return "{\"result\":\"error\"}";
        }
    }

	/**
     * ユーザー取得
     */
	@PostMapping("/getUserByAddressAndPassword")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public User getUserByAddressAndPassword(@RequestBody SearchUser searchUser) {
        // ユーザー取得
        return service.selectUserByAddressAndPassword(searchUser);
    }

    /**
     * ユーザー１件更新
     */
    @PutMapping("/update")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public String putUser(@RequestBody User user) {
        // ユーザーを１件登録
        boolean result = service.update(user);

        if(result == true) {
        	return "{\"result\":\"ok\"}";
        } else {
        	return "{\"result\":\"error\"}";
        }
    }

    /**
     * 1件のユーザー削除
     */
    @GetMapping("/delete/{id:.+}")
    @CrossOrigin(origins = {"http://localhost:8081"}, allowCredentials = "true")
    public String deleteUser(@PathVariable("id") int userId) {

    	 // ユーザーを１件削除
        boolean result = service.delete(userId);

        if(result == true) {
        	return "{\"result\":\"ok\"}";
        } else {
        	return "{\"result\":\"error\"}";
        }
    }

}
