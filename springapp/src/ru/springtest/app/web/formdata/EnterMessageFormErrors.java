package ru.springtest.app.web.formdata;

public class EnterMessageFormErrors {

	private String userNameError;
	
	private String messageDataError;

	public String getUserNameError() {
		return userNameError;
	}

	public void setUserNameError(String userNameError) {
		this.userNameError = userNameError;
	}

	public String getMessageDataError() {
		return messageDataError;
	}

	public void setMessageDataError(String messageDataError) {
		this.messageDataError = messageDataError;
	}
	
}
