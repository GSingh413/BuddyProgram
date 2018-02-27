package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectUtil {

	public static Connection createNewDBConnection() {
		Connection dbConnection = null;
		try {
			String url = "jdbc:mysql://securtyinnovationandresearch.cpu5zvvqzaxe.us-east-1.rds.amazonaws.com:3306";
			String username = "MyBuddyPOCAdmin";
			String password = "FBSM123abc";

			dbConnection = DriverManager.getConnection(url, username, password);

		} catch (Exception e) {
			System.out.println("db connection failed");
		}

		return dbConnection;
	}

	public static void closeDBConnection(Connection dbConnection) {

		try {
			dbConnection.close();
		} catch (SQLException e) {
			System.out.println("db connection failed");
		}

	}

}
