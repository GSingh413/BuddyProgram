package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.buddyprogram.pojo.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LogIn implements RequestHandler<User, String> {

	public String handleRequest(User user, Context context) {
		String userPassword = null;
		Boolean success = false;

		// Get the object from the event and show its content type
		try {
			String url = "jdbc:mysql://securtyinnovationandresearch.cpu5zvvqzaxe.us-east-1.rds.amazonaws.com:3306";
			String username = "MyBuddyPOCAdmin";
			String password = "FBSM123abc";

			Connection dbConnection = DriverManager.getConnection(url, username, password);
			String sql = "SELECT password FROM LEAPBuddy.Users WHERE email = ?";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setString(1, user.getEmail());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userPassword = resultSet.getObject(1).toString();
			}

			resultSet.close();
			preparedStatement.close();
			dbConnection.close();

			if (userPassword.compareTo(user.getPassword()) == 0) {
				success = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			return "Error getting record";
		}

		return success.toString();
	}
}