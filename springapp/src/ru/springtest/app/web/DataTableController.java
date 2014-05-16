package ru.springtest.app.web;

import java.io.IOException;
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

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	int pageNum = Integer.parseInt((String)request.getParameter("page"));
    	
    	int resultCount = Lookup.getDataProvider().getResultCount("from  " + Message.class.getName(), null);
    	
    	int firstResult = pageNum * RECORDS_ON_PAGE - RECORDS_ON_PAGE;
    	
    	int maxResult = firstResult + RECORDS_ON_PAGE > resultCount ? resultCount - firstResult : RECORDS_ON_PAGE;
    	
    	String hql = "from  " + Message.class.getName() + " m order by m.messageTime";
    	List<Message> messages = (List<Message>) Lookup.getDataProvider().findByHQL(hql, null, firstResult, maxResult);
    	
    	int totalPages =  (resultCount == resultCount/RECORDS_ON_PAGE * RECORDS_ON_PAGE) ? resultCount/RECORDS_ON_PAGE : resultCount/RECORDS_ON_PAGE + 1;
    	
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("page", pageNum);
        map.put("totalPages", totalPages);
        map.put("searchUri", "datatable.htm?page=" + pageNum);  
        map.put("messages", messages);
    	
        return new ModelAndView("datatable", map);
    }
}
