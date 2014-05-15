package ru.springtest.app;

import java.util.ArrayList;
import java.util.List;

import ru.springtest.app.domain.Message;
import ru.springtest.app.domain.User;

public class DbTestDataLoader {
	
	private Integer userCount;
	
	private Integer messageCount;
	
	private List<Object> forSave = new ArrayList<>();
	
	public DbTestDataLoader(Integer userCount, Integer messageCount) {
		super();
		this.userCount = userCount;
		this.messageCount = messageCount;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getMessageCount() {
		return messageCount;
	}

	public void setMessageCount(Integer messageCount) {
		this.messageCount = messageCount;
	}

	public void load() {		

		for(int i=1; i<=userCount; i++) {
			forSave.add(new User("user" + i, "password" + i));			
		}
		
		for(int i=1; i<=messageCount; i++) {
			forSave.add(new Message("message" + i));			
		}
		
		Lookup.getDataProvider().save(forSave.toArray());
	}

}
