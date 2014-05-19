package ru.springtest.app.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ru.springtest.app.web.formdata.FilterDataForm;

public class FilterDataFormValidator implements Validator{
		
	private static final String userNamePattern ="[a-zA-Zа-яА-Я0-9\\s]{1,48}";
	
	private static final String datePattern ="^(0[1-9]|1[012])[- \\/.](0[1-9]|[12][0-9]|3[01])[- \\/.]((?:19|20)\\d\\d)$";


	@Override
	public boolean supports(Class<?> arg0) {
		return FilterDataForm.class.isAssignableFrom(arg0);
	}

	@Override
	public void validate(Object arg0, Errors errors) {
		FilterDataForm filterForm = (FilterDataForm)arg0;

		if(filterForm.getUserPattern() != null && !filterForm.getUserPattern().isEmpty()) {
			
			// Имя пользователя должно состоять из букв латинского или русского алфавита и цифр
			if(!filterForm.getUserPattern().matches(userNamePattern)) {
				errors.rejectValue("userPattern","field.required.alphanumeric");
			}
			// Длина имени пользователя не должна быть более 48 символов
			if(filterForm.getUserPattern().length()>48) {
				errors.rejectValue("userPattern","field.length.required48");
			}
		}
		
		if(filterForm.getFromDate() != null && !filterForm.getFromDate().isEmpty()) {
			// Дата должна быть в формате MMDDYYYY
			if(!filterForm.getFromDate().matches(datePattern)) {
				errors.rejectValue("userPattern","field.date.mmddyyyy.expected");
			}
		}
		
		if(filterForm.getToDate() != null && !filterForm.getToDate().isEmpty()) {
			// Дата должна быть в формате MMDDYYYY			
			if(!filterForm.getToDate().matches(datePattern)) {
				errors.rejectValue("userPattern","field.date.mmddyyyy.expected");
			}
		}		

	}

}
