package ru.springtest.app;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import ru.springtest.app.domain.Message;
import ru.springtest.app.domain.User;

public class HibernateDataProviderTest {
	
	@BeforeClass
	public static void setUp() {
		TestUtil.springStart();
	}

    @Test
	public void saveTest() {
    	User user = new User("testuser1", "testUser1");
    	Message message = new Message("testmessage1", user);
    	
    	Lookup.getDataProvider().save(user, message);    
    	
    	List<User> usersFromDb = Lookup.getDataProvider().findByProperty(User.class, "name", "testuser1", 0, -1);
    	assertTrue(!usersFromDb.isEmpty());
    	
    	List<Message> messagesFromDb = Lookup.getDataProvider().findByProperty(Message.class, "content", "testmessage1", 0, -1);
    	assertTrue(!messagesFromDb.isEmpty());    	
    }

    @Test
    public void findByPropertyTest() {
    	User user = new User("testuser3", "testUser3");
    	Lookup.getDataProvider().save(user);   

    	List<User> usersFromDb = Lookup.getDataProvider().findByProperty(User.class, "name", "testuser3", 0, -1);
    	
    	assertTrue(!usersFromDb.isEmpty());        	
    }
    
    @Test
    public void deleteTest() {
    	User user = new User("testuser2", "testUser2");
    	Lookup.getDataProvider().save(user);   
    	List<User> usersFromDb = Lookup.getDataProvider().findByProperty(User.class, "name", "testuser2", 0, -1);
    	assertTrue(!usersFromDb.isEmpty());      	
    	
    	Lookup.getDataProvider().delete(user);
    	
    	usersFromDb = Lookup.getDataProvider().findByProperty(User.class, "name", "testuser2", 0, -1);
    	assertTrue(usersFromDb.isEmpty());    	
    }

    
    
    @Test
	public void getResultCountTest() {
    	User user = new User("testuser4", "testUser4");
    	Lookup.getDataProvider().save(user);   
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("name", "testuser4");
    	
    	String hql = "from " + User.class.getName() + " u where u.name = :name";
    	
    	int resultCount  = Lookup.getDataProvider().getResultCount(hql, map);
    	
    	assertTrue(resultCount==1);
    }

    @Test
	public void findByHQLTest() {
    	User user = new User("testuser5", "testUser5");
    	Lookup.getDataProvider().save(user);   
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("name", "testuser5");
    	
    	String hql = "from " + User.class.getName() + " u where u.name = :name";
    	
    	List<User> result = (List<User>) Lookup.getDataProvider().findByHQL(hql, map, 0, -1);
    	
    	assertTrue(result.size()==1);
    }
    
    @AfterClass
    public static void tearDown() {
    	TestUtil.springStop();
    }
	
}
