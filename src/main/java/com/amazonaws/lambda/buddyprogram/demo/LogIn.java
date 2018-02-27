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

			String sql = "SELECT password FROM LEAPBuddy.Users WHERE email = ?";
			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setString(1, user.getEmail());
			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				userPassword = resultSet.getObject(1).toString();
			}

			resultSet.close();
			preparedStatement.close();

			// utility function*******
			ConnectUtil.closeDBConnection(dbConnection);

			if (userPassword.compareTo(user.getPassword()) == 0) {
				returnObject.setMessageFromServer("Valid");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error getting record");
		}

		return returnObject;
	}
}