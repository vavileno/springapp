package ru.springtest.app.web.controllers;

import java.text.ParseException;
import java.util.Date;
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
import ru.springtest.app.validators.FilterDataFormValidator;
import ru.springtest.app.web.formdata.FilterDataForm;

@Controller
public class DataTableController {
	
    private static final Logger log = LoggerFactory.getLogger(DataTableController.class);
    
    private static final int RECORDS_ON_PAGE = 10;
    
    // Кэш отображаемых записей
    private static List<Message> messages;
    
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
        binder.setValidator(new FilterDataFormValidator());
    } 
    
	/**
	 * По умолчанию отображаются все записи из БД
	 * */
	@RequestMapping(value="datatable", method=RequestMethod.GET)
    public ModelAndView showForm(FilterDataForm filterDataForm) {
		error = false;
		errorString = null;
		
        Map<String, Object> map = new HashMap<String, Object>();
        
        requestData(filterDataForm, map);
        
        ModelAndView mv = new ModelAndView("datatable"); 
        map.put("error", error);
        map.put("errorString", errorString);
        return mv.addAllObjects(map);
    }	    

	/**
	 * Если параметры фильтра корректны, то из БД извлекаются соответствующие записи
	 * Иначе отображаются кэшированные данные
	 * 
	 * */
	@RequestMapping(value="datatable", method = RequestMethod.POST)
	public ModelAndView searchresult(@ModelAttribute("filterDataForm") @Validated FilterDataForm filterDataForm, 
			BindingResult result, ModelMap model) throws Exception {
		error = false;
		errorString = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// Ошибки в фильтре
		if(result.hasErrors()) {
			requestCacheData(filterDataForm, map);
			ModelAndView mv = new ModelAndView("datatable"); 
	        return mv.addAllObjects(map);
		}

    	requestData(filterDataForm, map);
        ModelAndView mv = new ModelAndView("datatable"); 
        return mv.addAllObjects(map);
    }
	
	/**
	 * Если кэш пустой, то возвращаются все записи
	 * */
	private void requestCacheData(FilterDataForm filterDataForm, Map<String, Object> map) {
		if(messages == null) {
			filterDataForm.setFromDate(null);
			filterDataForm.setToDate(null);
			filterDataForm.setUserPattern(null);
			requestData(filterDataForm, map);
			return;
		}
	
		int resultCount = messages.size();
		int pageNum = Integer.parseInt(filterDataForm.getPage());
		int totalPages =  (resultCount == resultCount/RECORDS_ON_PAGE * RECORDS_ON_PAGE) ? resultCount/RECORDS_ON_PAGE : resultCount/RECORDS_ON_PAGE + 1;
		
		map.put("page", pageNum);
		map.put("totalPages", totalPages);
		map.put("searchUri", "datatable.htm?page=" + pageNum);  
		map.put("messages", messages);		
		map.put("error", error);
		map.put("errorString", errorString);		
	}
	
	private void requestData(FilterDataForm filterDataForm, Map<String, Object> map) {
		String userPattern = filterDataForm.getUserPattern();
		String fromDateString = filterDataForm.getFromDate();
		String toDateString = filterDataForm.getToDate();
		
		Date fromDate = null; 
		Date toDate = null;
		try {
 			if(fromDateString != null && !fromDateString.isEmpty()) {
				fromDate = Lookup.getSimpleDateFormat().parse(fromDateString);
			}
			if(toDateString != null && !toDateString.isEmpty()) {
				toDate = Lookup.getSimpleDateFormat().parse(toDateString);
			}
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("fromDate", fromDate);
		params.put("toDate", toDate);
		
		// Построение запроса с указанными параметрами фильтра
		StringBuilder hql = buildHql(params, userPattern);	
		
		// Узнаем количество записей с указанными параметрами фильтра
		int resultCount = Lookup.getDataProvider().getResultCount(hql.toString(), params);
		
		// Переменные для пагинатора
		int pageNum = Integer.parseInt(filterDataForm.getPage());
		int firstResult = pageNum * RECORDS_ON_PAGE - RECORDS_ON_PAGE;
		int maxResult = firstResult + RECORDS_ON_PAGE > resultCount ? resultCount - firstResult : RECORDS_ON_PAGE;
		int totalPages =  (resultCount == resultCount/RECORDS_ON_PAGE * RECORDS_ON_PAGE) ? resultCount/RECORDS_ON_PAGE : resultCount/RECORDS_ON_PAGE + 1;
		
		try {
			messages = (List<Message>) Lookup.getDataProvider().findByHQL(hql.toString(), params, firstResult, maxResult);
		}
		catch(DataAccessException daoe) {
			log.error(daoe.getMessage(), daoe);
			error = true;
			errorString = ResourceBundle.getBundle("messages").getString("error.database");
		}
		
		map.put("page", pageNum);
		map.put("totalPages", totalPages);
		map.put("searchUri", "datatable.htm?page=" + pageNum);  
		map.put("messages", messages);
		map.put("error", error);
		map.put("errorString", errorString);
	}

	private StringBuilder buildHql(Map<String, Object> params, String userPattern) {
    	
    	boolean hasWhere = false;
		StringBuilder hql = new StringBuilder("from  " + Message.class.getName() + " m ");
    	Date fromDate = (Date)params.get("fromDate"); 
    	Date toDate = (Date)params.get("toDate");
    	
    	if(userPattern != null && !userPattern.isEmpty()) {
    		if(!hasWhere) {
    			hql.append(" where ");
    			hasWhere = true;
    		}
    		hql.append(" m.user.name like '%" + userPattern + "%'");
    	}
    	
    	if(fromDate != null) {
    		if(!hasWhere) {
    			hql.append(" where ");
    			hasWhere = true;
    		}
    		else {
    			hql.append(" and ");
    		}
    		hql.append(" m.messageDate >=  :fromDate");
        	params.put("fromDate", fromDate);
    	}
    	
    	if(toDate != null) {
    		if(!hasWhere) {
    			hql.append(" where ");
    			hasWhere = true;
    		}
    		else {
    			hql.append(" and ");
    		}
    		hql.append(" m.messageDate <=  :toDate");
    		params.put("toDate", toDate);
    	}
    	
    	hql.append(" order by m.messageDate, m.user.name");
    	
		return hql;
	}
}
