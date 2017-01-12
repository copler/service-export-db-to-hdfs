package com.dell.ems.services.export.dbtohdfs.task;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.dell.ems.services.export.dbtohdfs.service.ExportService;

@Component
public class ExportTask {

    @Value("${service.export.dbtohdfs.scheduler}")
    private String schedulerExpression;

    @Value("${service.export.dbtohdfs.timestamp}")
    private long timestamp;

    @PostConstruct
    protected void setup() {
        scheduler.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    doRun();
                } catch (Throwable t) {
                    logger.warn("Exception during task execution: " + t.getMessage());
                }
            }
        }, new CronTrigger(schedulerExpression));
    }

    private void doRun() {
        long currentTimestamp = System.currentTimeMillis();
        Date since = new Date(timestamp);
        if (logger.isInfoEnabled()) {
            logger.info("Exporting data since: " + since + " ...");
        }
        exportService.exportSince(since);
        if (logger.isInfoEnabled()) {
            logger.info("Exporting data done.");
        }
        timestamp = currentTimestamp;
    }

    @Inject
    private ExportService exportService;

    @Inject
    private ThreadPoolTaskScheduler scheduler;

    private Logger logger = Logger.getLogger(getClass());
}
