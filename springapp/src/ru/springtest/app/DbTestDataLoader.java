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
		List<User> userList = new ArrayList<>();

		User user = null;
		for(int i=1; i<=userCount; i++) {
			user = new User("user" + i, "password" + i);
			forSave.add(user);	
			userList.add(user);
		}
		
		for(int i=1; i<=messageCount; i++) {
			Message m = new Message("message" + i, null);
			
			if(userList.isEmpty()) {
				forSave.add(m);
				continue;
			}

			user = i-1 < userList.size() ? userList.get(i-1) : userList.get(0); 
			m.setUser(user);
			forSave.add(m);		
		}
		
		Lookup.getDataProvider().save(forSave.toArray());
	}
}
