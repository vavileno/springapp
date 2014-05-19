package ru.springtest.app.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import ru.springtest.app.Lookup;
import ru.springtest.app.domain.Message;
import ru.springtest.app.domain.User;
import ru.springtest.app.validators.EnterFormValidator;
import ru.springtest.app.web.formdata.EnterMessageForm;

@Controller
public class EnterMessageController {
	
	private static final Logger log = LoggerFactory.getLogger(EnterMessageController.class);
    
    private boolean error;
    
    private String errorString;
    
    public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getErrorString() {
		return errorString;
	}

	public void setErrorString(String errorString) {
		this.errorString = errorString;
	}

	@InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new EnterFormValidator());
    }    

	@RequestMapping(value="enter_message", method=RequestMethod.GET)
    public ModelAndView showForm(EnterMessageForm enterMessageForm) {
		error = false;
		errorString = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", error);
		map.put("errorString", errorString);		
        return new ModelAndView("enter_message", map);
    }	
	
	@RequestMapping(value="enter_message", method = RequestMethod.POST)
	public ModelAndView createUserAndMessage(@ModelAttribute("enterMessageForm") @Validated EnterMessageForm enterMessageForm, 
			BindingResult result, ModelMap model) throws Exception {
		error = false;
		errorString = null;
		
		if(result.hasErrors()) {
			return new ModelAndView("enter_message");
		}
		
		createAndSave(enterMessageForm.getUserName(), enterMessageForm.getMessageContent());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", error);
		map.put("errorString", errorString);
		return new ModelAndView("message_saved", map);
	}

	private void createAndSave(String userName, String messageContent) {
		List<User> existingUser= Lookup.getDataProvider().findByProperty(User.class, "name", userName, 0, -1);
		
		User user = null;
		if(existingUser.isEmpty()) {
			user = new User(userName, userName);
		}
		else {
			user = existingUser.get(0);
		}
			
		try {
			Lookup.getDataProvider().save(user, new Message(messageContent, user));
		}
		catch(DataAccessException daoe) {
			log.error(daoe.getMessage(), daoe);
			error = true;
			errorString = ResourceBundle.getBundle("messages").getString("error.database");
		}
	}

}
