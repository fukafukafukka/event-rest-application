/* ユーザーテーブル */
CREATE TABLE IF NOT EXISTS user (
  user_id int NOT NULL AUTO_INCREMENT COMMENT 'ユーザーID',
  user_name varchar(30) NOT NULL COMMENT 'ユーザー名',
  mail_address varchar(20) NOT NULL COMMENT 'メールアドレス',
  password varchar(60) NOT NULL COMMENT 'パスワード',
  user_role varchar(10) NOT NULL DEFAULT 'USER' COMMENT '閲覧権限',
  PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ユーザーテーブル';

/* タスク管理テーブル */
CREATE TABLE IF NOT EXISTS task_by_user (
  task_id int NOT NULL AUTO_INCREMENT COMMENT 'タスクID',
  user_id int COMMENT 'ユーザーID',
  task_name varchar(30) NOT NULL COMMENT 'タスク名',
  task_detail varchar(50) NOT NULL COMMENT 'タスク詳細',
  task_done boolean NOT NULL DEFAULT false COMMENT 'タスク完了',
  delete_flag int NOT NULL DEFAULT 0 COMMENT '削除フラグ',
  PRIMARY KEY (task_id),
  CONSTRAINT fk_user_id
  FOREIGN KEY (user_id)
  REFERENCES user (user_id)
  ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='タスク管理テーブル';

/* タスク修正履歴テーブル */
CREATE TABLE IF NOT EXISTS task_revision_history (
  task_revision_id int NOT NULL AUTO_INCREMENT COMMENT 'タスク履歴ID',
  task_id int COMMENT 'タスクID',
  task_name varchar(30) NOT NULL COMMENT 'タスク名',
  task_detail varchar(50) NOT NULL COMMENT 'タスク詳細',
  task_done boolean NOT NULL DEFAULT false COMMENT 'タスク完了',
  delete_flag int NOT NULL DEFAULT 0 COMMENT '削除フラグ',
  insert_date_times TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '登録日付',
  PRIMARY KEY (task_revision_id),
  CONSTRAINT fk_task_id
  FOREIGN KEY (task_id)
  REFERENCES task_by_user (task_id)
  ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='タスク修正履歴テーブル';
