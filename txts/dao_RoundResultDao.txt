package dao;

import java.util.List;

import entity.RoundResult;

public interface RoundResultDao {
	// create
	int insert(RoundResult roundResult);
	// read
	List<RoundResult> selectAll();
	RoundResult selectById(int id);
	// update

	// delete
	void deleteById(int id);
}
