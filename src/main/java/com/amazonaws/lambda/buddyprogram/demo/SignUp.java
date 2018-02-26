package com.amazonaws.lambda.buddyprogram.demo;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import com.amazonaws.lambda.buddyprogram.pojo.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SignUp implements RequestHandler<User, String> {

	public String handleRequest(User user, Context context) {
		// context.getLogger().log("Input: " + input);

		// // JSON from String to Object
		// ObjectMapper mapper = new ObjectMapper();
		// mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		// User user = null;
		//
		// try {
		// System.out.println("About to map input string");
		// user = mapper.readValue(input.toString(), User.class);
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// System.out.println("Unable to parse input" + input);
		// return "Failed to parse input";
		// }

		// Get the object from the event and show its content type
		try {
			String url = "jdbc:mysql://securtyinnovationandresearch.cpu5zvvqzaxe.us-east-1.rds.amazonaws.com:3306";
			String username = "MyBuddyPOCAdmin";
			String password = "FBSM123abc";

			Connection dbConnection = DriverManager.getConnection(url, username, password);
			String sql = "INSERT INTO LEAPBuddy.Users (email, password, salt) VALUES (?, ?, ?)";

			byte[] array = new byte[7]; // length is bounded by 7
			new Random().nextBytes(array);
			String generatedString = new String(array, Charset.forName("UTF-8"));

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);

			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, generatedString);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String email = resultSet.getObject(1).toString();
				System.out.println("Email: " + email);
			}

			resultSet.close();
			preparedStatement.close();
			dbConnection.close();

		} catch (Exception e) {
			e.printStackTrace();
			return "Error inserting record";
		}

		return "Success";
	}
}