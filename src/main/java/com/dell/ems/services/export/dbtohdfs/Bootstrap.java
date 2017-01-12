package com.dell.ems.services.export.dbtohdfs;

import java.util.HashMap;
import java.util.Map;

import net.sf.webdav.WebdavServlet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
public class Bootstrap {

    @Value("${service.export.dbtohdfs.webdav.root:/tmp}")
    private String root;

    @Bean
    public ThreadPoolTaskScheduler createThreadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(1);
        return threadPoolTaskScheduler;
    }

    @Bean
    public ServletRegistrationBean dispatcherServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebdavServlet(), "/webdav/*");
        Map<String, String> params = new HashMap<>();
        params.put("debug", "0");
        params.put("listings", "true");
        params.put("readonly", "true");
        params.put("rootpath", root);
        params.put("ResourceHandlerImplementation", "net.sf.webdav.LocalFileSystemStore");
        registration.setInitParameters(params);
        registration.addUrlMappings();
        return registration;
    }

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

}
