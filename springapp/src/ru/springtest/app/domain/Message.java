package ru.springtest.app.domain;

import java.util.Date;

public class Message {
	
	private String content;
	
	private Date messageTime;
	
	public Message() {
		super();
	}

	public Message(String content) {
		this.content = content;
		this.messageTime = new Date();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getMessageTime() {
		return messageTime;
	}

	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}

}
