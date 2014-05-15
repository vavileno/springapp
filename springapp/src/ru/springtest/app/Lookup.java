package ru.springtest.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lookup {

    private static final Logger log = LoggerFactory.getLogger(Lookup.class);
//
//    private static File webappDir;
//
//    private static File tempDir;
//
//    private static Properties applicationProperties;
    
    private static HibernateDataProvider dataProvider;
    
    public Lookup(HibernateDataProvider dataProvider) {
    	Lookup.dataProvider = dataProvider;
    }
    
    public static HibernateDataProvider getDataProvider() {
        return dataProvider;
    }    
    
	public void setDataProvider(HibernateDataProvider dataProvider) {
		Lookup.dataProvider = dataProvider;
	}    

//    public static ServletContext getServletContext() {
////        return (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//    }

//    public static File getWebappDir() {
//        return getWebappDir(getServletContext());
//    }

//    public static File getWebappDir(ServletContext servletContext) {
//        if (webappDir == null) {
//            webappDir = new File(servletContext.getRealPath("/"));
//        }
//        return webappDir;
//    }

//
//    public static <T extends Object> T getBean(Class<T> type) {
//        String beanId = type.getSimpleName().substring(0, 1).toLowerCase() + type.getSimpleName().substring(1);
//        return getBean(type, beanId, getServletContext());
//    }
//
//    public static <T extends Object> T getBean(Class<T> type, ServletContext servletContext) {
//        String beanId = type.getSimpleName().substring(0, 1).toLowerCase() + type.getSimpleName().substring(1);
//        return getBean(type, beanId, servletContext);
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T extends Object> T getBean(Class<T> type, String beanId, ServletContext servletContext) {
//        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//        return (T) wac.getBean(beanId);
//    }

}
