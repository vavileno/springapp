package ru.springtest.app.util;

import java.io.File;

import org.apache.catalina.startup.Tomcat;

public class TomcatEmbeddedSrv {

    public static void main(String[] args) throws Exception {
        String webappDirLocation = "war";
        Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8888";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        tomcat.start();
        tomcat.getServer().await();
    }
}
