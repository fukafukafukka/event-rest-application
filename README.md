# event-rest-application
# README #
タスク管理アプリのCRUDな"REST API"です。

# 動作チェック方法について
## 1.ソースをclone、もしくはダウンロードしてローカルPC上に解凍する。

## 2.Webサーバーの起動
### 1.解凍したソース（プロジェクト）をEclipseにインポートする。
1. [ファイル]-[インポート]でインポート画面を表示
2. [既存 Maven プロジェクト]を[選択]し[次へ]
3. [ルート・ディレクトリ]にクローンした[RestAPI]を選ぶ
4. プロジェクト欄に表示された全ての項目にチェックを入れて[完了]を押す
### 2.Eclipse上からSpringBootを起動させる。
1. プルジェクト[RestAPI]を右クリック→[実行]→[Spring Boot アプリケーション]と順に選択。

## 3.DBサーバーの起動
1. 解凍したソース（プロジェクト）内の[docker/dev/]ディレクトリに移動する。
2. 移動したディレクトリ配置されている[docker-compose]ファイル内の、[MYSQL_ROOT_PASSWORD],[MYSQL_DATABASE],[MYSQL_USER],[MYSQL_PASSWORD]の値を任意の値に設定する。
3. 移動したディレクトリにて、次のコマンドを実行し、DBコンテナを起動させる。[docker-compose up]
4. ファイル[/RestApi/src/main/resources/application.properties]内の[spring.datasource.username],[spring.datasource.password]の値をdocker-composeファイルの値と揃えて記入する。

## 4.動作チェック
### task一件追加
1. ターミナルにて次のリクエストを実行する。[curl -X POST -H "Content-Type:application/json" -d '{"userId":"1","taskName":"commit作成","taskDetail":"RestAPI作成分をcommit"}' http://localhost:8080/rest/insert]

### ログインユーザーの全task一覧取得
1. ブラウザにて次のリクエストを実行する。[http://localhost:8080/rest/getAllTasks/1]
### ログインユーザーの未完了・未削除の全task検索
1. ブラウザにて次のリクエストを実行する。[http://localhost:8080/rest/getNotYetTasks/1]

### task1件更新（task完了）
1. ターミナルにて次のリクエストを実行する。[curl -X PUT -H "Content-Type:application/json" -d '{"userId":"1","taskId":"2","taskName":"commit作成","taskDetail":"RestAPI作成分をcommit2","doneFlag":"1","deleteFlag":"0"}' http://localhost:8080/rest/update]
### ログインユーザーの完了済の全task検索
1. ブラウザにて次のリクエストを実行する。[http://localhost:8080/rest/getNotYetTasks/1]

### task1件更新（task削除）
1. ターミナルにて次のリクエストを実行する。[curl -X PUT -H "Content-Type:application/json" -d '{"userId":"1","taskId":"2","taskName":"commit作成","taskDetail":"RestAPI作成分をcommit2","doneFlag":"1","deleteFlag":"1"}' http://localhost:8080/rest/update]
### ログインユーザーの削除済の全task検索
1. ブラウザにて次のリクエストを実行する。[http://localhost:8080/rest/getDeletedTasks/1]

### 1件のタスクに紐づく全履歴を取得
1. ブラウザにて次のリクエストを実行する。[http://localhost:8080/rest/getAllRevisionsOnOneTask/2]
### 1人のユーザーに紐づく全履歴を取得
1. ブラウザにて次のリクエストを実行する。[http://localhost:8080/rest/getAllRevisions/1]
