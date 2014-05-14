package ru.springtest.app.web;

import junit.framework.TestCase;

import org.springframework.web.servlet.ModelAndView;

public class MainControllerTest extends TestCase {

    public void testHandleRequestView() throws Exception{		
        MainController controller = new MainController();
        ModelAndView modelAndView = controller.handleRequest(null, null);		
        assertEquals("main", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
        String nowValue = (String) modelAndView.getModel().get("now");
        assertNotNull(nowValue);        
    }	
	
}
