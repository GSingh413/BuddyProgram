package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.lambda.buddyprogram.pojo.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetProfile implements RequestHandler<User, ReturnObject> {

	public ReturnObject handleRequest(User user, Context context) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setMessageFromServer("Invalid Request");

		// Get the object from the event and show its content type
		try {
			Connection dbConnection = ConnectUtil.createNewDBConnection();

			String sql = "SELECT first_name, last_name, about_me, mentor FROM LEAPBuddy.Users WHERE user_id = ?";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setLong(1, user.getUserId());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				user.setFirstName(resultSet.getString("first_name"));
				user.setLastName(resultSet.getString("last_name"));
				user.setAboutMe(resultSet.getString("about_me"));
				user.setMentor(resultSet.getBoolean("mentor"));

			}

			resultSet.close();
			preparedStatement.close();
			ConnectUtil.closeDBConnection(dbConnection);

			returnObject.setMessageFromServer("Success");
			returnObject.setUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error getting record");
		}

		return returnObject;
	}
}