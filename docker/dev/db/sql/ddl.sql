/* タスク管理テーブル */
CREATE TABLE IF NOT EXISTS task_manage_table (
  task_id int NOT NULL AUTO_INCREMENT COMMENT 'タスク番号',
  task_name varchar(30) UNIQUE NOT NULL COMMENT 'タスク名',
  task_detail varchar(50) NOT NULL COMMENT 'タスク詳細',
  status varchar(30) NOT NULL COMMENT 'タスクステータス',
  PRIMARY KEY (task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='タスク管理テーブル';

/* ユーザーテーブル */
CREATE TABLE IF NOT EXISTS user (
  user_id int NOT NULL AUTO_INCREMENT COMMENT 'ユーザー番号',
  user_name varchar(30) UNIQUE NOT NULL COMMENT 'ユーザー名',
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ユーザーテーブル';

/* ユーザーIDタスクIDマッピングテーブル */
CREATE TABLE IF NOT EXISTS user_id_task_id_mapping (
  user_id int NOT NULL COMMENT 'ユーザー番号',
  task_id int NOT NULL COMMENT 'タスク番号',
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ユーザーIDタスクIDマッピングテーブル';

