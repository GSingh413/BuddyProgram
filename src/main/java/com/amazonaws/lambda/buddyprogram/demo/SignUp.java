package com.amazonaws.lambda.buddyprogram.demo;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import com.amazonaws.lambda.buddyprogram.pojo.ReturnObject;
import com.amazonaws.lambda.buddyprogram.pojo.User;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SignUp implements RequestHandler<User, ReturnObject> {

	public ReturnObject handleRequest(User user, Context context) {
		// Get the object from the event and show its content type
		ReturnObject returnObject = new ReturnObject();
		returnObject.setMessageFromServer("Unsuccessful");
		try {
			Connection dbConnection = ConnectUtil.createNewDBConnection();

			String sql = "INSERT INTO LEAPBuddy.Users (email, password, salt) VALUES (?, ?, ?)";

			byte[] array = new byte[7]; // length is bounded by 7
			new Random().nextBytes(array);
			String generatedString = new String(array, Charset.forName("UTF-8"));

			PreparedStatement preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, generatedString);

			ResultSet resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				String email = resultSet.getObject(1).toString();
				System.out.println("Email: " + email);

			}

			ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				user.setUserId(generatedKeys.getLong(1));
			} else {
				throw new SQLException("Creating user failed, no ID obtained.");
			}
			resultSet.close();
			preparedStatement.close();
			ConnectUtil.closeDBConnection(dbConnection);

			user.setPassword(null);
			returnObject.setUser(user);
			returnObject.setMessageFromServer("Successful");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnObject;
	}
}