package service;

import java.util.List;

import entity.GameResult;
import entity.RoundResult;

public interface GameResultService {
    int createGameResult(GameResult gameResult, RoundResult roundResults[]);
    GameResult selectById(int id);
    List<GameResult> selectByPlayerName(String playerName);
    RoundResult selectRoundResultById(int id);
    String showByPlayerName(String playerName);
    String showAllPlayerNames();
    void deleteGameResult(int id);
}
