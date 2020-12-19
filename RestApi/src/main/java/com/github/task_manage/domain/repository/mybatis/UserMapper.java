package com.github.task_manage.domain.repository.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.github.task_manage.domain.model.SearchUser;
import com.github.task_manage.domain.model.User;

@Mapper
public interface UserMapper {

	// 1件レコード投入
    public boolean insert(User user) throws DataAccessException;

	// userIdに紐づく全レコードを取得
	public User selectUserByAddressAndPassword(SearchUser searchUser) throws DataAccessException;

	// Userテーブルのレコード1件更新
    public boolean update(User user) throws DataAccessException;

    // 1件レコード削除
    @Deprecated
    public boolean delete(int userId) throws DataAccessException;
}
