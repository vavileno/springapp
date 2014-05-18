package ru.springtest.app;

import org.springframework.context.support.FileSystemXmlApplicationContext;

public class TestUtil {

    private static FileSystemXmlApplicationContext context;


    public static void springStart() {
        if (context == null) {
            context = new FileSystemXmlApplicationContext("applicationContext.xml");
        }
    }

    public static void springStop() {
        if (context != null) {
            context.close();
            context = null;
        }
    }

    public static FileSystemXmlApplicationContext getContext() {
        return context;
    }

}
