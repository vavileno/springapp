package ru.springtest.app.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import ru.springtest.app.Lookup;
import ru.springtest.app.domain.Message;
import ru.springtest.app.domain.User;

public class EnterMessageController implements Controller {

	@Override
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("saveResult", "");
		
		String userName = request.getParameter("username");
		String messageContent = request.getParameter("message_content");
		
		if(userName != null && messageContent != null) {
			
			List<User> existingUser= Lookup.getDataProvider().findByProperty(User.class, "name", userName, 0, -1);
			
			User user = null;
			if(existingUser.isEmpty()) {
				user = new User(userName, userName);
			}
			else {
				user = existingUser.get(0);
			}
			
			Lookup.getDataProvider().save(user, new Message(messageContent, user));
			map.put("saveResult", "Message saved.");
		}
		
		return new ModelAndView("enter_message", map);
	}

}
