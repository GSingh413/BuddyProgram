package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.buddyprogram.pojo.Interest;
import com.amazonaws.lambda.buddyprogram.pojo.InterestEnum;
import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.lambda.buddyprogram.pojo.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetInterests implements RequestHandler<User, ReturnObject> {

	public ReturnObject handleRequest(User user, Context context) {
		ReturnObject returnObject = new ReturnObject();
		returnObject.setMessageFromServer("Invalid Request");
		try {
			Connection dbConnection = ConnectUtil.createNewDBConnection();

			String sql = "SELECT DISTINCT interest_title FROM LEAPBuddy.Interests WHERE user_id = ?";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setLong(1, user.getUserId());

			ResultSet resultSet = preparedStatement.executeQuery();

			List<Interest> interests = new ArrayList<>();

			while (resultSet.next()) {
				Interest interest = new Interest();
				interest.setInterestEnum(InterestEnum.fromString(resultSet.getString("interest_title")));
				interests.add(interest);
			}

			resultSet.close();
			preparedStatement.close();
			ConnectUtil.closeDBConnection(dbConnection);

			returnObject.setMessageFromServer("Success");
			returnObject.setInterests(interests);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error getting record");
		}

		return returnObject;
	}

}