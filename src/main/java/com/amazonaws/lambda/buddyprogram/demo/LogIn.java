package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.lambda.buddyprogram.pojo.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogIn implements RequestHandler<User, ReturnObject> {

	public ReturnObject handleRequest(User user, Context context) {
		String userPassword = null;
		ReturnObject returnObject = new ReturnObject();
		returnObject.setMessageFromServer("Invalid");
		ObjectMapper mapper = new ObjectMapper();
		String jsonResponse = null;

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
				returnObject.setMessageFromServer("Valid");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error getting record");
		}

		// try {
		// jsonResponse = mapper.writeValueAsString(returnObject);
		// } catch (JsonProcessingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		return returnObject;
	}
}