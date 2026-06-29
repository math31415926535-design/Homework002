package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

	public static void main(String[] args) {
		System.out.println(DbConnection.getDb());
	}

	public static Connection getDb()
	{
		Connection conn=null;
		String url="jdbc:mysql://localhost:3306/TJPoker";
		String user="root";
		String password="1234";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e) {
			System.err.println("No Driver");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("No Connection");
			e.printStackTrace();
		}

		return conn;
	}
}
