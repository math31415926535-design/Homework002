# TJPoker

這份專案是把 `Homework01` 的完整田忌撲克，整理成 `Company2` 的架構：

專案名稱：

- Eclipse 專案名稱：`TJPoker`
- Maven artifactId：`TJPoker`
- 專案資料夾：`TJPoker`
- MySQL schema：`TJPoker`
- 遊戲名稱：`田忌撲克`

背景圖片：

- 目前使用：`picture/poker_table_felt.png`

- `controller`：登入、註冊、主選單、遊戲 UI、歷史紀錄查詢
- `entity`：原遊戲的 `Card`、`Player`、`Round`、`Game`，以及資料庫使用的 `Member`、`GameResult`、`RoundResult`
- `service` / `service.impl`：遊戲邏輯與遊戲紀錄服務
- `dao` / `dao.impl`：參考 `Company2` 的手寫 MySQL DAO
- `util`：`DbConnection`、`Sort`、`GameTool`、`CardIconFactory`、`Tool`

啟動流程：

1. 匯入 `sql command.sql`
2. 執行 `controller.MemberLoginUi`
3. 註冊或登入
4. 登入成功後進入 `PokerHomeUi`
5. 選擇「開始遊戲」進入電腦對戰，或選擇「歷史紀錄」依玩家名稱查詢
6. 玩完三輪後，自動寫入 `game_result` 與 `round_result`

DAO 對應：

- `MemberDao`：帳號登入註冊
- `GameResultDao`：一局遊戲的玩家、勝者、六筆單人回合結果編號，以及依玩家名稱查詢
- `RoundResultDao`：單一玩家在單一回合的牌型與牌
