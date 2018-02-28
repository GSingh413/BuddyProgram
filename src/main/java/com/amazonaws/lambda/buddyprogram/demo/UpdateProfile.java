package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.lambda.buddyprogram.pojo.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class UpdateProfile implements RequestHandler<User, ReturnObject> {

	public ReturnObject handleRequest(User user, Context context) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setMessageFromServer("Invalid Request");

		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Connection dbConnection = null;
		// Get the object from the event and show its content type
		try {
			dbConnection = ConnectUtil.createNewDBConnection();

			String sql = "UPDATE LEAPBuddy.Users SET first_name = ?, last_name = ?, about_me = ?, mentor = ? WHERE user_id = ?";

			preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setString(1, user.getFirstName());
			preparedStatement.setString(2, user.getLastName());
			preparedStatement.setString(3, user.getAboutMe());
			preparedStatement.setBoolean(4, user.isMentor());
			preparedStatement.setLong(5, user.getUserId());

			resultSet = preparedStatement.executeQuery();

			returnObject.setMessageFromServer("Success");
			returnObject.setUser(user);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error updating record");
		} finally {
			try {
				resultSet.close();
				preparedStatement.close();
				ConnectUtil.closeDBConnection(dbConnection);
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Error: " + e.getStackTrace());
				returnObject.setMessageFromServer("Error updating record");
			}

		}

		return returnObject;
	}
}