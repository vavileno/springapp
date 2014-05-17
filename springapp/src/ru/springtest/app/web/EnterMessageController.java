package ru.springtest.app.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.springtest.app.Lookup;
import ru.springtest.app.domain.Message;
import ru.springtest.app.domain.User;
import ru.springtest.app.validators.EnterFormValidator;
import ru.springtest.app.web.formdata.EnterMessageForm;

@Controller
public class EnterMessageController {
	
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new EnterFormValidator());
    }    

	@RequestMapping(value="enter_message", method=RequestMethod.GET)
    public String showForm(EnterMessageForm enterMessageForm) {
        return "enter_message";
    }	
	
	@RequestMapping(value="enter_message", method = RequestMethod.POST)
	public String createUserAndMessage(@ModelAttribute("enterMessageForm") @Validated EnterMessageForm enterMessageForm, 
			BindingResult result, ModelMap model) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(result.hasErrors()) {
			return "enter_message";
		}
		
		map.put("saveResult", "");
		
		String userName = enterMessageForm.getUserName();
		String messageContent = enterMessageForm.getMessageContent();
		
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
//			map.put("saveResult", "Message saved.");
		}
		
		return "enter_message";
	}

}
