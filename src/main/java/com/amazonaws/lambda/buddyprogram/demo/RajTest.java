package com.amazonaws.lambda.buddyprogram.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class RajTest implements RequestHandler<Object, String> {

	@Override
	public String handleRequest(Object input, Context context) {
		context.getLogger().log("Input: " + input);
		System.out.println("Creating connection to DB");
		// TODO: implement your handler
		try {
			String url = "jdbc:mysql://securtyinnovationandresearch.cpu5zvvqzaxe.us-east-1.rds.amazonaws.com:3306";
			String username = "MyBuddyPOCAdmin";
			String password = "FBSM123abc";

			Connection conn = DriverManager.getConnection(url, username, password);
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery("SELECT email from LEAPBuddy.Users");
			// Extra
			if (resultSet.next()) {
				String email = resultSet.getObject(1).toString();
				System.out.println("Email: " + email);
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: " + e.getStackTrace());
		}

		return "Hello from Lambda!";
	}

}
