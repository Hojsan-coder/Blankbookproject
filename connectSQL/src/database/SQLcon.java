package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import beans.UserBean;

public class SQLcon {

	static Connection userConn = null;
	static Connection postConn = null;
	static PreparedStatement prepareStatement = null;
	static ResultSet rs = null;

	public static boolean connectSQL() {
		try {
			// driver setup
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception ex) {
			// handle the error
			System.out.println("Exception Driver: " + ex);
			return false;
		}

		try {
			userConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?serverTimezone=UTC",
					DatabaseLogin.getuName(), DatabaseLogin.getuPass());
			postConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/feed?serverTimezone=UTC",
					DatabaseLogin.getuName(), DatabaseLogin.getuPass());

			return true;
		} catch (SQLException ex) {
			// handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
	}

	public static boolean stateSQL(UserBean bean) {
		// test a query
		try {
			String requestQuery = "SELECT * FROM user WHERE username = ? and password = ?";
			prepareStatement = userConn.prepareStatement(requestQuery);
			prepareStatement.setString(1, bean.getusername());
			prepareStatement.setString(2, bean.getPassword());

			rs = prepareStatement.executeQuery();
			// ResultSet return
			while (rs.next()) {
				bean.setName(rs.getString("fullname"));

				return true;
			}

			userConn.endRequest();
		} catch (SQLException ex) {
			// TODO Auto-generated catch block
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}

		return false;
	}

	// method to add message to SQL
	public static void addPostMessagesSQL(String creator, String content, String tag) {
		try {
			String requestQuery = "INSERT INTO `post`( `creator`, `content`, `tag` ) VALUES (?,?,?)";
			prepareStatement = postConn.prepareStatement(requestQuery);
			prepareStatement.setString(1, creator);
			prepareStatement.setString(2, content);
			prepareStatement.setString(3, tag);
			prepareStatement.executeUpdate();

			postConn.endRequest();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			System.out.println("addpostmessage");
		}
	}

	// Method to get info from database post from SQl
	public static ResultSet getPostSql() {
		try {
			String requestQuery = "SELECT * FROM post";
			prepareStatement = postConn.prepareStatement(requestQuery);
			rs = prepareStatement.executeQuery();

			postConn.endRequest();
			// postConn.close();

			return rs;
		} catch (SQLException e) {
			System.out.println("getpost");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}

		return null;
	}

	// method closing post connection
	public static void closePostCon() {
		try {
			postConn.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
}
