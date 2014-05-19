package ru.springtest.app.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MessageSavedController {

	@RequestMapping(value="message_saved", method=RequestMethod.GET)
	public String showMessageSaved() { 
		
		return "message_saved";
	}

}
