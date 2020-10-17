/* タスク管理テーブル */
CREATE TABLE IF NOT EXISTS task_manage_table (
  task_number int NOT NULL AUTO_INCREMENT COMMENT 'タスク番号',
  task_name varchar(30) UNIQUE NOT NULL COMMENT 'タスク名',
  task_detail varchar(50) NOT NULL COMMENT 'タスク詳細',
  status varchar(30) NOT NULL COMMENT 'タスクステータス',
  PRIMARY KEY (task_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='タスク管理テーブル';
