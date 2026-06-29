package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.impl.GameResultDaoImpl;
import dao.impl.RoundResultDaoImpl;
import entity.GameResult;
import entity.RoundResult;
import service.GameResultService;

public class GameResultServiceImpl implements GameResultService {

    GameResultDaoImpl grdi=new GameResultDaoImpl();
    RoundResultDaoImpl rrdi=new RoundResultDaoImpl();

    // 新增遊戲紀錄 top
    @Override
    public int createGameResult(GameResult gameResult, RoundResult roundResults[]) {
        if(gameResult==null) return 0;
        if(roundResults==null) return 0;
        if(roundResults.length!=6) return 0;
        for(int i=0;i<roundResults.length;i++)
        {
            if(roundResults[i]==null) return 0;
        }

        if(gameResult.getGameIndex()<=0) gameResult.setGameIndex(nextGameIndex());
        if(grdi.selectByGameIndex(gameResult.getGameIndex())!=null) return 0;

        // 先新增回合，取得id
        int ids[]=new int[6];
        for(int i=0;i<roundResults.length;i++)
        {
            ids[i]=rrdi.insert(roundResults[i]);
            if(ids[i]<=0) return 0;
        }

        gameResult.setRoundResult1(ids[0]);
        gameResult.setRoundResult2(ids[1]);
        gameResult.setRoundResult3(ids[2]);
        gameResult.setRoundResult4(ids[3]);
        gameResult.setRoundResult5(ids[4]);
        gameResult.setRoundResult6(ids[5]);

        return grdi.insert(gameResult);
    }
    // 新增遊戲紀錄 end

    // 取得下一個遊戲編號 top
    private int nextGameIndex() {
        int max=0;
        List<GameResult> results=grdi.selectAll();
        for(int i=0;i<results.size();i++)
        {
            if(results.get(i).getGameIndex()>max)
            {
                max=results.get(i).getGameIndex();
            }
        }
        return max+1;
    }
    // 取得下一個遊戲編號 end

    @Override
    public GameResult selectById(int id) {
        return grdi.selectById(id);
    }

    @Override
    public List<GameResult> selectByPlayerName(String playerName) {
        return grdi.selectByPlayerName(playerName);
    }

    @Override
    public RoundResult selectRoundResultById(int id) {
        return rrdi.selectById(id);
    }

    // 查詢歷史紀錄 top
    @Override
    public String showByPlayerName(String playerName) {
        List<GameResult> results=selectByPlayerName(playerName);
        if(results.size()==0) return "查無 " + playerName + " 的對局紀錄";

        String text="";
        for(int i=0;i<results.size();i++)
        {
            text+=gameText(results.get(i));
            if(i<results.size()-1)
                text+="--------------------------------------------------\n";
        }
        return text;
    }
    // 查詢歷史紀錄 end

    // 查詢所有玩家 top
    @Override
    public String showAllPlayerNames() {
        List<GameResult> results=grdi.selectAll();
        List<String> names=new ArrayList<>();
        for(int i=0;i<results.size();i++)
        {
            String playerName1=results.get(i).getPlayerName1();
            String playerName2=results.get(i).getPlayerName2();
            if(playerName1!=null && !playerName1.equals("") && !names.contains(playerName1))
                names.add(playerName1);
            if(playerName2!=null && !playerName2.equals("") && !names.contains(playerName2))
                names.add(playerName2);
        }

        if(names.size()==0) return "目前沒有玩家紀錄";

        String text="所有有對局紀錄的玩家：\n";
        for(int i=0;i<names.size();i++)
        {
            text+=(i+1) + ". " + names.get(i) + "\n";
        }
        return text;
    }
    // 查詢所有玩家 end

    // 單局紀錄
    private String gameText(GameResult gameResult)
    {
        String text="第 " + gameResult.getGameIndex() + " 局\n"
                + "玩家：" + gameResult.getPlayerName1() + " vs "
                + gameResult.getPlayerName2() + "\n"
                + "勝者：" + gameResult.getWinnerName() + "\n";

        int ids[]={gameResult.getRoundResult1(), gameResult.getRoundResult2(),
                gameResult.getRoundResult3(), gameResult.getRoundResult4(),
                gameResult.getRoundResult5(), gameResult.getRoundResult6()};

        for(int i=0;i<3;i++)
        {
            RoundResult player1Result=selectRoundResultById(ids[i*2]);
            RoundResult player2Result=selectRoundResultById(ids[i*2+1]);
            text+="第" + (i+1) + "輪\n";
            text+="  " + gameResult.getPlayerName1() + "："
                    + roundText(player1Result) + "\n";
            text+="  " + gameResult.getPlayerName2() + "："
                    + roundText(player2Result) + "\n";
        }
        return text;
    }

    // 單輪紀錄
    private String roundText(RoundResult roundResult)
    {
        if(roundResult==null) return "查無資料";

        String text=roundResult.getHandtype() + "  ";
        String cards[]={roundResult.getCard1(), roundResult.getCard2(),
                roundResult.getCard3(), roundResult.getCard4(), roundResult.getCard5()};
        for(int i=0;i<cards.length;i++)
        {
            if(cards[i]!=null && !cards[i].equals(""))
            {
                text+=cards[i] + " ";
            }
        }
        return text;
    }

    // 刪除遊戲紀錄 top
    @Override
    public void deleteGameResult(int id) {
        GameResult gameResult=grdi.selectById(id);
        if(gameResult==null) return;

        grdi.deleteById(id);
        rrdi.deleteById(gameResult.getRoundResult1());
        rrdi.deleteById(gameResult.getRoundResult2());
        rrdi.deleteById(gameResult.getRoundResult3());
        rrdi.deleteById(gameResult.getRoundResult4());
        rrdi.deleteById(gameResult.getRoundResult5());
        rrdi.deleteById(gameResult.getRoundResult6());
    }
    // 刪除遊戲紀錄 end
}
