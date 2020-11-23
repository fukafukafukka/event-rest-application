-- ユーザーテーブル用
-- passwordの値はhogehoge
insert into user
(user_name, mail_address, password)
values
('hogesawa', 'hoge@gmail.com', '$2a$08$ZonPqzzPiyTR5hx7wAZVYOpy.al1Fj80irlMlDWYhyph.WedrQOA6');

-- タスク管理テーブル用
insert into task_by_user
(user_id, task_name, task_detail)
values
(1, 'バックエンド実装', 'RestAPIの作成');

-- タスク修正履歴テーブル
insert into task_revision_history
(task_id, task_name, task_detail)
values
(1, 'バックエンド実装', 'RestAPIの作成')
