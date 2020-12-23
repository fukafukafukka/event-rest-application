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
### curlオプションの意味
- -b：cookieファイルの指定
- -c：cookieを保存するファイルの指定
- -d：key,value方式で、Request Bodyに入れる。
- -H；ヘッダ情報の付与
- -X；HTTP メソッドの指定
- -i；httpヘッダを出力に含める

### CSRF-TOKENを取得する
curl -i -c cookie.txt "http://localhost:8080/prelogin"
#### 実行後
- CSRF-TOKEN:368c7d5b-1b5a-4cd9-9b57-5a96a368ec65

### ログイン認証する。（上記のCSRF-TOKENを使う）
curl -i -b cookie.txt -c cookie.txt -X POST -d "mailAddress=hoge@gmail.com" -d "password=hogehoge" -d "_csrf=368c7d5b-1b5a-4cd9-9b57-5a96a368ec65" "http://localhost:8080/login"
#### 実行後
- CSRF-TOKEN:eb92fdce-ae3a-4a26-a16b-49bc728404ce（これでUSER権限をもったユーザーでログイン完了したことになる。）

### ログアウトする。（上記のログイン後のCSRF-TOKENを使う）
curl -i -b cookie.txt -H "x-xsrf-token:eb92fdce-ae3a-4a26-a16b-49bc728404ce" -X POST "http://localhost:8080/logout"
- CSRF-TOKEN:eb92fdce-ae3a-4a26-a16b-49bc728404ce（ログアウトした後もローカルのTOKENは変わっていないが、これはもう使えない。）

### URERのROLEがないといけない、かつ、GETの場合。
curl -i -b cookie.txt "http://localhost:8080/task/getAllTasks/1"
curl -i -b cookie.txt "http://localhost:8080/task/getNotYetTasks/1"
curl -i -b cookie.txt "http://localhost:8080/task/getDoneTasks/1"
curl -i -b cookie.txt "http://localhost:8080/task/getDeletedTasks/1"
curl -i -b cookie.txt "http://localhost:8080/task/getAllRevisionsOnOneTask/1"
curl -i -b cookie.txt "http://localhost:8080/task/getAllRevisions/1"

### USERのROLEがないといけない、かつ、POSTの場合。
→ 実行前にCSRF-TOKENの値をcookie.txtの中身をから取って、それをHEADERに使う。
#### taskのinsert
curl -i -b cookie.txt -X POST -H "Content-Type:application/json" -H "x-xsrf-token:eb92fdce-ae3a-4a26-a16b-49bc728404ce" -d '{"userId":"1","taskName":"認証のテスト","taskDetail":"認証くぐれるかどうか"}' "http://localhost:8080/task/insert"
#### taskのupdate
curl -i -b cookie.txt -X PUT -H "Content-Type:application/json" -H "x-xsrf-token:eb92fdce-ae3a-4a26-a16b-49bc728404ce" -d '{"userId":"1","taskId":"1","taskName":"認証のテスト2","taskDetail":"認証くぐれるかどうか2","doneFlag":"0","deleteFlag":"0"}' http://localhost:8080/task/update
#### taskのdelete(論理削除のため実際はupdate)
curl -i -b cookie.txt -X PUT -H "Content-Type:application/json" -H "x-xsrf-token:eb92fdce-ae3a-4a26-a16b-49bc728404ce" -d '{"userId":"1","taskId":"1","taskName":"認証のテスト2","taskDetail":"認証くぐれるかどうか2","doneFlag":"1","deletedFlag":"1"}' http://localhost:8080/task/update
#### userのinsert
curl -i -b cookie.txt -X POST -H "Content-Type:application/json" -H "x-xsrf-token:eb92fdce-ae3a-4a26-a16b-49bc728404ce" -d '{"userName":"fugasawa","mailAddress":"fuga@gmail.com","password":"fugafuga","userRole":"ADMIN"}' "http://localhost:8080/user/insert"
#### userのselect
curl -i -b cookie.txt -X POST -H "Content-Type:application/json" -H "x-xsrf-token:eb92fdce-ae3a-4a26-a16b-49bc728404ce" -d '{"mailAddress":"fuga@gmail.com","password":"fugafuga"}' "http://localhost:8080/user/getUserByAddressAndPassword"

### ADMINのROLEがないといけないケース
curl -i -b cookie.txt "http://localhost:8080/admin"
→ ちゃんと403エラーが返ってくるかどうかチェックする。

### 認証が不要なAPIでも、CSRFの対象になるケース(POSTだからCSRF対象になってる？)
→ ※「/hello」を「/prelogin」と同じようにpermitallとして、/helloコンとロラークラスを一時的に作ってテストした。
→ 結果、POSTはpermitallしていても、通らないっぽい。
curl -i -b cookie.txt -X POST "http://localhost:8080/hello" -d "message=WORLD" -d "_csrf=eb92fdce-ae3a-4a26-a16b-49bc728404ce"
→通った。
curl -i -X POST "http://localhost:8080/hello" -d "message=WORLD"
→通らない。
curl -i -X GET "http://localhost:8080/hello"
→通った。