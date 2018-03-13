package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.lambda.buddyprogram.pojo.Interest;
import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class MentorRecommendations implements RequestHandler<Interest, ReturnObject> {

	public ReturnObject handleRequest(Interest interest, Context context) {
		ReturnObject returnObject = new ReturnObject();

		try {
			Connection dbConnection = ConnectUtil.createNewDBConnection();

			// determine user's interests
			String sql = "SELECT interest_title FROM LEAPBuddy.Interests WHERE user_id = ?";

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
			preparedStatement.setLong(1, interest.getUserId());
			ResultSet resultSet = preparedStatement.executeQuery();

			List<Interest> menteeInterestList = new ArrayList<>();

			while (interest != null) {
				// sql = sql + "interest title = " + interest[BIG_DATA];?\
			}

			/*
			 * String sql2 = "SELECT interest_"
			 * 
			 * // Store interest for mentee in array
			 * 
			 * // match user's interests to others interests sql =
			 * "SELECT DISTINCT user_id FROM LEAPBuddy.Interests GROUP BY interest_title HAVING COUNT(DISTINCT interest_title) > 1"
			 * ; // convert to prepared statements // bind ? resultSet =
			 * preparedStatement.executeQuery(); // store the results
			 * 
			 * // filter out mentors sql =
			 * "SELECT user_id FROM LEAPBuddy.Users WHERE mentor = 1"; // convert to
			 * prepared statements // bind ? resultSet = preparedStatement.executeQuery();
			 * // store the results
			 * 
			 * System.out.println(resultSet);
			 */
			resultSet.close();
			preparedStatement.close();
			ConnectUtil.closeDBConnection(dbConnection);

			returnObject.setMessageFromServer("Success");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
			returnObject.setMessageFromServer("Error matching users");
		}
		return returnObject;
	}

}
