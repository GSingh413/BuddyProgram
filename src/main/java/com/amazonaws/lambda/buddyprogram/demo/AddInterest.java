package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.amazonaws.lambda.buddyprogram.pojo.Interest;
import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class AddInterest implements RequestHandler<Interest, ReturnObject> {

	public ReturnObject handleRequest(Interest interest, Context context) {
		ReturnObject returnObject = new ReturnObject();

		try {
			Connection dbConnection = ConnectUtil.createNewDBConnection();

			String sql = "INSERT INTO LEAPBuddy.Interests (interest_title, user_id) VALUES (?, ?)";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setString(1, interest.getInterestEnum().interestTitle());
			preparedStatement.setLong(2, interest.getUserId());

			ResultSet resultSet = preparedStatement.executeQuery();

			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				interest.setInterestId(generatedKeys.getLong(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}

			generatedKeys.close();
			resultSet.close();
			preparedStatement.close();
			ConnectUtil.closeDBConnection(dbConnection);

			returnObject.setMessageFromServer("Success");
			returnObject.setInterest(interest);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error updating record");
		}
		return returnObject;
	}

}
