package ru.springtest.app;

import java.text.SimpleDateFormat;

import ru.springtest.app.dao.HibernateDataProvider;

/**
 * Хранит вспомогательные объекты доступные для приложения
 * 
 * */
public class Lookup {
    
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    
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

	public static SimpleDateFormat getSimpleDateFormat() {
		return sDateFormat;
	}
}
