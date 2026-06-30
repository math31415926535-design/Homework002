package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import dao.RoundResultDao;
import entity.RoundResult;
import util.DbConnection;

public class RoundResultDaoImpl implements RoundResultDao {

	Connection conn=DbConnection.getDb();

	@Override
	public int insert(RoundResult roundResult) {
		int id=0;
		String sql="insert into round_result(handtype,card_1,card_2,card_3,card_4,card_5) value(?,?,?,?,?,?)";
		try {
			PreparedStatement ps=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, roundResult.getHandtype());
			ps.setString(2, roundResult.getCard1());
			ps.setString(3, roundResult.getCard2());
			ps.setString(4, roundResult.getCard3());
			ps.setString(5, roundResult.getCard4());
			ps.setString(6, roundResult.getCard5());
			ps.executeUpdate();
			ResultSet rs=ps.getGeneratedKeys();
			if(rs.next())
			{
				id=rs.getInt(1);
				roundResult.setId(id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public List<RoundResult> selectAll() {
		List<RoundResult> l=new ArrayList<>();
		String sql="select * from round_result";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				l.add(readRoundResult(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public RoundResult selectById(int id) {
		RoundResult roundResult=null;
		String sql="select * from round_result where id=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				roundResult=readRoundResult(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roundResult;
	}

	@Override
	public void deleteById(int id) {
		String sql="delete from round_result where id=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private RoundResult readRoundResult(ResultSet rs) throws SQLException
	{
		RoundResult roundResult=new RoundResult();
		roundResult.setId(rs.getInt("id"));
		roundResult.setHandtype(rs.getString("handtype"));
		roundResult.setCard1(rs.getString("card_1"));
		roundResult.setCard2(rs.getString("card_2"));
		roundResult.setCard3(rs.getString("card_3"));
		roundResult.setCard4(rs.getString("card_4"));
		roundResult.setCard5(rs.getString("card_5"));
		return roundResult;
	}

}
