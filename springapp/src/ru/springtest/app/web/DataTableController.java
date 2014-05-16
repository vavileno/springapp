package ru.springtest.app.web;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import ru.springtest.app.Lookup;
import ru.springtest.app.domain.Message;

public class DataTableController implements Controller {
	
    private static final Logger logger = LoggerFactory.getLogger(DataTableController.class);
    
    private static final int RECORDS_ON_PAGE = 10;
    
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("MM/dd/yyyy");

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String userPattern = request.getParameter("userPattern");
    	String fromDateString = request.getParameter("dateFrom");
    	String toDateString = request.getParameter("dateTo");
    	
    	Date fromDate = null; 
    	Date toDate = null;
    	try {
	    	if(fromDateString != null && !fromDateString.isEmpty()) {
				fromDate = sDateFormat.parse(fromDateString);
	    	}
	    	if(toDateString != null && !toDateString.isEmpty()) {
	    		toDate = sDateFormat.parse(toDateString);
	    	}
    	} catch (ParseException e) {
    		logger.error(e.getMessage(), e);
    	}

        int pageNum = Integer.parseInt(request.getParameter("page"));
    	
    	boolean hasWhere = false;
    	
    	Map<String, Object> params = new HashMap<String, Object>();
    	StringBuilder hql = new StringBuilder("from  " + Message.class.getName() + " m ");
    	
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
    		hql.append(" m.messageDate >  :fromDate");
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
    	
    	hql.append(" order by m.messageDate");
    	
    	int resultCount = Lookup.getDataProvider().getResultCount(hql.toString(), params);
    	
    	int firstResult = pageNum * RECORDS_ON_PAGE - RECORDS_ON_PAGE;
    	
    	int maxResult = firstResult + RECORDS_ON_PAGE > resultCount ? resultCount - firstResult : RECORDS_ON_PAGE;
    	
    	List<Message> messages = (List<Message>) Lookup.getDataProvider().findByHQL(hql.toString(), params, firstResult, maxResult);
    	
    	int totalPages =  (resultCount == resultCount/RECORDS_ON_PAGE * RECORDS_ON_PAGE) ? resultCount/RECORDS_ON_PAGE : resultCount/RECORDS_ON_PAGE + 1;
    	
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", pageNum);
        map.put("totalPages", totalPages);
        map.put("searchUri", "datatable.htm?page=" + pageNum);  
        map.put("messages", messages);
    	
        return new ModelAndView("datatable", map);
    }
}
