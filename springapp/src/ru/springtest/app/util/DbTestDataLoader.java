package ru.springtest.app.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ru.springtest.app.Lookup;
import ru.springtest.app.domain.Message;
import ru.springtest.app.domain.User;

/**
 * Загружает данные в БД для выполнения тестов. 
 * 
 * */
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

	// Загружает 10 пользователей вида "user" + i
	// Загружает 10 сообщений вида "message" + i с интервалом в 1 день
	// Каждому сообщению соответствует пользователь с соответствующим индексом i
	public void load() {
		List<User> userList = new ArrayList<>();
		Calendar c = Calendar.getInstance();

		User user = null;
		for(int i=1; i<=userCount; i++) {
			user = new User("user" + i, "password" + i);
			forSave.add(user);	
			userList.add(user);
		}
		
		for(int i=1; i<=messageCount; i++) {
			c.add(Calendar.DAY_OF_MONTH, -1);
			Message m = new Message("message" + i, null, c.getTime());
			
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
