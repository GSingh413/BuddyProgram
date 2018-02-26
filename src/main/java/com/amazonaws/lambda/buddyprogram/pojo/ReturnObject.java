package com.amazonaws.lambda.buddyprogram.pojo;

public class ReturnObject {
	private String messageFromServer;
	private User user;

	public String getMessageFromServer() {
		return messageFromServer;
	}

	public void setMessageFromServer(String messageFromServer) {
		this.messageFromServer = messageFromServer;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
