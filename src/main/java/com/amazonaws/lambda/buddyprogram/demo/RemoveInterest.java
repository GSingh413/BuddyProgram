package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.amazonaws.lambda.buddyprogram.pojo.Interest;
import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RemoveInterest implements RequestHandler<Interest, ReturnObject> {

	public ReturnObject handleRequest(Interest interest, Context context) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setMessageFromServer("Invalid Request");
		try {
			Connection dbConnection = ConnectUtil.createNewDBConnection();

			String sql = "DELETE FROM LEAPBuddy.Interests WHERE user_id = ? AND interest_id = ?";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setLong(1, interest.getUserId());
			preparedStatement.setLong(2, interest.getInterestId());

			ResultSet resultSet = preparedStatement.executeQuery();

			resultSet.close();
			preparedStatement.close();
			ConnectUtil.closeDBConnection(dbConnection);

			returnObject.setMessageFromServer("Success");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error getting record");
		}

		return returnObject;
	}

}