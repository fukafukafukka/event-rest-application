package com.github.task_manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.task_manage.domain.model.SearchUser;
import com.github.task_manage.domain.model.User;
import com.github.task_manage.domain.repository.mybatis.UserMapper;

@Service("UserService")
@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
public class UserService implements UserServiceInterface {

	@Autowired
	UserMapper userMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public boolean insert(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// insert実行
		return userMapper.insert(user);
	}

	@Override
	public User selectUserByAddressAndPassword(SearchUser searchUser) {
		// select実行
		User selectedUserByMailAddress = userMapper.selectUserByAddressAndPassword(searchUser);
		if (selectedUserByMailAddress == null) {
			return null;
		}
		if (passwordEncoder.matches(searchUser.getPassword(), selectedUserByMailAddress.getPassword())) {
			// DBのパスワードと等しければuserデータを返す。
			return selectedUserByMailAddress;
		} else {
			return null;
		}
	}

	@Override
	public boolean update(User user) {
		System.out.println(user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		// update実行
		return userMapper.update(user);
	}

	@Override
	public boolean delete(int userId) {
		// delete実行
		return userMapper.delete(userId);
	}

}
