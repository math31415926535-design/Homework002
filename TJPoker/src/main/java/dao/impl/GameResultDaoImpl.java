package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.GameResultDao;
import entity.GameResult;
import util.DbConnection;

public class GameResultDaoImpl implements GameResultDao {

	Connection conn=DbConnection.getDb();

	@Override
	public int insert(GameResult gameResult) {
		int id=0;
		String sql="insert into game_result(game_index,player_name_1,player_name_2,winner_name,"
				+ "round_result_1,round_result_2,round_result_3,round_result_4,round_result_5,round_result_6) "
				+ "value(?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, gameResult.getGameIndex());
			ps.setString(2, gameResult.getPlayerName1());
			ps.setString(3, gameResult.getPlayerName2());
			ps.setString(4, gameResult.getWinnerName());
			ps.setInt(5, gameResult.getRoundResult1());
			ps.setInt(6, gameResult.getRoundResult2());
			ps.setInt(7, gameResult.getRoundResult3());
			ps.setInt(8, gameResult.getRoundResult4());
			ps.setInt(9, gameResult.getRoundResult5());
			ps.setInt(10, gameResult.getRoundResult6());
			ps.executeUpdate();
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next())
			{
				id=rs.getInt(1);
				gameResult.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public List<GameResult> selectAll() {
		List<GameResult> l=new ArrayList<>();
		String sql="select * from game_result";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				l.add(readGameResult(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public GameResult selectById(int id) {
		GameResult gameResult=null;
		String sql="select * from game_result where id=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				gameResult=readGameResult(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameResult;
	}

	@Override
	public GameResult selectByGameIndex(int gameIndex) {
		GameResult gameResult=null;
		String sql="select * from game_result where game_index=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, gameIndex);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				gameResult=readGameResult(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return gameResult;
	}

	// 根據玩家名稱查詢 top
	@Override
	public List<GameResult> selectByPlayerName(String playerName) {
		List<GameResult> l=new ArrayList<>();
		String sql="select * from game_result where player_name_1=? or player_name_2=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, playerName);
			ps.setString(2, playerName);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				l.add(readGameResult(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}
	// 根據玩家名稱查詢 end

	@Override
	public void deleteById(int id) {
		String sql="delete from game_result where id=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private GameResult readGameResult(ResultSet rs) throws SQLException
	{
		GameResult gameResult=new GameResult();
		gameResult.setId(rs.getInt("id"));
		gameResult.setGameIndex(rs.getInt("game_index"));
		gameResult.setPlayerName1(rs.getString("player_name_1"));
		gameResult.setPlayerName2(rs.getString("player_name_2"));
		gameResult.setWinnerName(rs.getString("winner_name"));
		gameResult.setRoundResult1(rs.getInt("round_result_1"));
		gameResult.setRoundResult2(rs.getInt("round_result_2"));
		gameResult.setRoundResult3(rs.getInt("round_result_3"));
		gameResult.setRoundResult4(rs.getInt("round_result_4"));
		gameResult.setRoundResult5(rs.getInt("round_result_5"));
		gameResult.setRoundResult6(rs.getInt("round_result_6"));
		return gameResult;
	}

}
