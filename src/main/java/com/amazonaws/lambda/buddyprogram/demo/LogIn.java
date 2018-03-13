package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.lambda.buddyprogram.pojo.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LogIn implements RequestHandler<User, ReturnObject> {

	public ReturnObject handleRequest(User user, Context context) {
		String userPassword = null;
		ReturnObject returnObject = new ReturnObject();
		returnObject.setMessageFromServer("Invalid");

		// Get the object from the event and show its content type
		try {
			// utility function*******
			Connection dbConnection = ConnectUtil.createNewDBConnection();

			String sql = "SELECT password, user_id FROM LEAPBuddy.Users WHERE email = ? LIMIT 1";
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setString(1, user.getEmail());
			ResultSet resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				userPassword = resultSet.getString("password");
				user.setUserId(resultSet.getLong("user_id"));
			}

			resultSet.close();
			preparedStatement.close();

			// utility function*******
			ConnectUtil.closeDBConnection(dbConnection);

			if (userPassword.compareTo(user.getPassword()) == 0) {
				returnObject.setMessageFromServer("Success");
				returnObject.setUser(user);
			} else {
				returnObject.setMessageFromServer("Incorrect Password");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error getting record");
		}

		return returnObject;
	}
}