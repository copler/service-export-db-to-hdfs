package com.dell.ems.services.export.dbtohdfs;

import com.dell.ems.services.export.dbtohdfs.domain.Item;
import com.dell.ems.services.export.dbtohdfs.domain.ItemRepository;
import net.sf.webdav.WebdavServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Bootstrap {

    @Value("${service.export.dbtohdfs.root:/tmp}")
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

    @Bean
    CommandLineRunner init(final ItemRepository itemRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... arg0) throws Exception {
                Item item = new Item();
                item.setId("1");
                item.setPayload("Payload");
                item.setUpdateTimestamp(new Date());
                itemRepository.save(item);
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

}
