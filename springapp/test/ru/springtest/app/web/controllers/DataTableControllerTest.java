package ru.springtest.app.web.controllers;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ru.springtest.app.TestUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:springapp-servlet.xml", "classpath*:applicationContext.xml"})
@WebAppConfiguration
public class DataTableControllerTest {
	
	private static final Logger log = LoggerFactory.getLogger(DataTableControllerTest.class);

	private MockMvc mockMvc;
	
    @InjectMocks
    private DataTableController dataTableController = new DataTableController();
	
	@Autowired
	WebApplicationContext wac;

	@Before
	public void setUp() {
		TestUtil.springStart();
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(dataTableController).build();
	}
	
	@Test
    public void showFormTest() {
	    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/datatable.htm?page=1");
	    ResultActions result;
		try {
			result = mockMvc.perform(request);
			result.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("datatable"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("page","totalPages","messages","searchUri","error"))
			.andExpect(MockMvcResultMatchers.model().attribute("messages", Matchers.isA(ArrayList.class)));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Test
    public void searchresult() {
	    MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/datatable.htm?page=1");
	    request.param("userPattern", "user1");
	    request.param("fromDate", "05/09/2014");
	    request.param("toDate", "05/09/2014");
	    ResultActions result;
		try {
			result = mockMvc.perform(request);
			result.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.view().name("datatable"))
			.andExpect(MockMvcResultMatchers.model().attributeExists("page","totalPages","messages","searchUri","error"))
			.andExpect(MockMvcResultMatchers.model().attribute("messages", Matchers.isA(ArrayList.class)))
			.andExpect(MockMvcResultMatchers.model().attribute("messages", Matchers.hasSize(1)));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}		
	}
	
	public void tearDown() {
		TestUtil.springStop();
	}
}
