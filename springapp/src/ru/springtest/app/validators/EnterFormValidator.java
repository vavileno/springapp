package ru.springtest.app.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import ru.springtest.app.web.formdata.EnterMessageForm;

public class EnterFormValidator implements Validator  {
	
	private static final String userNamePattern ="[a-zA-Zа-яА-Я0-9\\s]{1,255}";

	@Override
	public boolean supports(Class<?> arg0) {
		return EnterMessageForm.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		EnterMessageForm enterMessageForm = (EnterMessageForm)arg0;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "field.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "messageContent", "field.required");
		
		if(errors.getFieldError("userName")== null) {
			// Имя пользователя должно состоять из букв латинского или русского алфавита и цифр
			if(!enterMessageForm.getUserName().matches(userNamePattern)) {
				errors.rejectValue("userName","field.required.alphanumeric");
			}
			
			// Длина имени пользователя не должна быть более 48 символов
			if(enterMessageForm.getUserName().length()>48) {
				errors.rejectValue("userName","field.length.required48");
			}
		}
		
		// Длина сообщения не должна быть более 48 символов		
		if(enterMessageForm.getMessageContent().length()>48) {
			errors.rejectValue("messageContent","field.length.required48");
		}		
	}

}
