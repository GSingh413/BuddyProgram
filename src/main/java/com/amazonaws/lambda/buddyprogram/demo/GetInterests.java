package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.buddyprogram.pojo.Interest;
import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetInterests implements RequestHandler<Interest, ReturnObject> {

	public ReturnObject handleRequest(Interest interest, Context context) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setMessageFromServer("Invalid Request");

		// Get the object from the event and show its content type
		try {
			Connection dbConnection = ConnectUtil.createNewDBConnection();

			String sql = "SELECT interest_id, current_interest, area_of_business, title FROM LEAPBuddy.Interests WHERE user_id = ?";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setLong(1, interest.getUserId());

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				interest.setInterestId(resultSet.getLong("interest_id"));
				interest.setCurrentInterest(resultSet.getString("current_interest"));
				interest.setAreaOfBusiness(resultSet.getString("area_of_business"));
				interest.setTitle(resultSet.getString("title"));

			}

			resultSet.close();
			preparedStatement.close();
			ConnectUtil.closeDBConnection(dbConnection);

			returnObject.setMessageFromServer("Success");
			returnObject.setInterest(interest);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error getting record");
		}

		return returnObject;
	}

}