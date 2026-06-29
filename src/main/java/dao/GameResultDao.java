package dao;

import java.util.List;

import entity.GameResult;

public interface GameResultDao {
	// create
	int insert(GameResult gameResult);
	// read
	List<GameResult> selectAll();
	GameResult selectById(int id);
	GameResult selectByGameIndex(int gameIndex);
	List<GameResult> selectByPlayerName(String playerName);
	// update

	// delete
	void deleteById(int id);
}
