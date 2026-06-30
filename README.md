# TJPoker 田忌撲克

TJPoker 是以 Java Swing 製作的撲克牌桌面遊戲，使用 MySQL 保存會員資料與對局紀錄。
玩家登入後可與電腦進行三輪比牌，也能查詢及列印過去的遊戲結果。

## 遊戲規則

- 每位玩家會取得 15 張牌。
- 第一輪選擇 3 張牌。
- 第二輪選擇 5 張牌。
- 第三輪選擇 5 張牌。
- 其餘 2 張牌不會使用。
- 每輪比較雙方牌型，三輪中獲勝次數較多的一方贏得遊戲。

五張牌的牌型大小：

`同花順 > 鐵支 > 葫蘆 > 同花 > 順子 > 三條 > 兩對 > 一對 > 高牌`

三張牌的牌型大小：

`三條 > 一對 > 高牌`

## 主要功能

- 會員註冊與登入
- 玩家與電腦對戰
- 點擊手牌選擇或取消出牌
- 依花色或點數整理手牌
- 自動尋找目前回合可組成的最強牌型
- 顯示每輪牌型及勝負結果
- 自動保存完整對局紀錄
- 依玩家名稱查詢歷史紀錄
- 顯示曾參加對局的所有玩家
- 列印當局結果或歷史紀錄

## 使用技術

- Java
- Java Swing
- JDBC
- MySQL
- Maven
- MySQL Connector/J 8.0.33

## 專案結構

```text
TJPoker
├─ picture/
│  ├─ cards_classic/          撲克牌圖片
│  └─ poker_table_felt.png    遊戲背景
├─ src/main/java/
│  ├─ controller/             登入、註冊、主頁、遊戲及歷史紀錄畫面
│  ├─ dao/                    資料存取介面
│  ├─ dao/impl/               JDBC 資料存取實作
│  ├─ entity/                 遊戲與資料庫實體類別
│  ├─ service/                業務邏輯介面
│  ├─ service/impl/           遊戲、會員及紀錄服務實作
│  └─ util/                   資料庫連線、排序、牌型及圖片工具
├─ pom.xml                    Maven 設定
└─ TJPoker.sql                資料庫建置指令
```

## 執行環境

開始前請先準備：

- JDK 11 或以上版本
- Eclipse（需支援 Maven）或其他 Java IDE
- MySQL Server
- Maven

## 資料庫設定

1. 啟動 MySQL。
2. 開啟 `TJPoker.sql` 並執行全部指令。
3. 確認已建立 `TJPoker` 資料庫，以及以下三張資料表：
   - `member`
   - `round_result`
   - `game_result`
4. 開啟 `src/main/java/util/DbConnection.java`，依照自己的 MySQL 設定修改帳號及密碼。

目前程式預設使用：

```java
String url = "jdbc:mysql://localhost:3306/TJPoker";
String user = "root";
String password = "1234";
```

> 注意：`TJPoker.sql` 會先刪除同名資料表再重新建立，原有的會員及遊戲紀錄會被清除。

## Eclipse 匯入方式

1. 選擇 `File > Import`。
2. 選擇 `Maven > Existing Maven Projects`。
3. 選取 `TJPoker` 專案資料夾。
4. 按下 `Finish`。
5. 等待 Maven 下載 MySQL Connector/J。

若依賴套件沒有正確載入，可在專案上按右鍵，選擇：

`Maven > Update Project`

## 啟動方式

執行以下類別的 `main` 方法：

```text
controller.MemberLoginUi
```

若要使用重新設計後的介面，請改為執行：

```text
test_ui.MemberLoginUi
```

新版介面位於 `src/main/java/test_ui/`，沿用原本的會員、遊戲、牌型判斷、
對局紀錄與列印服務，不會取代 `controller/` 內的舊版畫面。

操作流程：

1. 註冊新帳號或使用現有帳號登入。
2. 在主頁選擇「開始遊戲」。
3. 每輪點選指定張數的牌，再按下「確認出牌」。
4. 需要時可使用排序或自動選牌功能。
5. 三輪結束後，系統會顯示結果並保存紀錄。
6. 回到主頁選擇「歷史紀錄」，即可依玩家名稱查詢。

## 資料表用途

| 資料表 | 用途 |
| --- | --- |
| `member` | 保存會員帳號、密碼及姓名 |
| `round_result` | 保存單一玩家在某一輪的牌型及牌面 |
| `game_result` | 保存一局遊戲的雙方玩家、勝者及六筆回合紀錄編號 |

## 注意事項

- 遊戲執行時必須保持 MySQL 服務開啟。
- 撲克牌及背景圖片位於 `picture` 資料夾，請勿單獨移動或刪除。
- 若資料庫無法連線，請先檢查資料庫名稱、連接埠、帳號及密碼。
- 本專案目前以桌面視窗方式執行，不需要瀏覽器或網頁伺服器。
