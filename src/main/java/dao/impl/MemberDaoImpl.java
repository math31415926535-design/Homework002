package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dao.MemberDao;
import entity.Member;
import util.DbConnection;

public class MemberDaoImpl implements MemberDao {

	Connection conn=DbConnection.getDb();

	@Override
	public void insert(Member member) {
		String sql="insert into member(username,password,name) value(?,?,?)";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, member.getUsername());
			ps.setString(2, member.getPassword());
			ps.setString(3, member.getName());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Member selectByUsernameAndPassword(String username, String password) {
		Member member=null;
		String sql="select * from member where username=? and password=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				member=readMember(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}

	@Override
	public Member selectByUsername(String username) {
		Member member=null;
		String sql="select * from member where username=?";
		try {
			PreparedStatement ps=conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				member=readMember(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}

	private Member readMember(ResultSet rs) throws SQLException
	{
		Member member=new Member();
		member.setId(rs.getInt("id"));
		member.setUsername(rs.getString("username"));
		member.setPassword(rs.getString("password"));
		member.setName(rs.getString("name"));
		return member;
	}
}
