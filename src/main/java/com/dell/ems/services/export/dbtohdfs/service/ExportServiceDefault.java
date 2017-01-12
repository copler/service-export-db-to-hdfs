package com.dell.ems.services.export.dbtohdfs.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.dell.ems.services.export.dbtohdfs.domain.Item;
import com.dell.ems.services.export.dbtohdfs.domain.ItemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ExportServiceDefault implements ExportService {

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public void exportSince(Date updateTimestamp) {
        List<Item> items = repository.findByUpdateTimestampGreaterThanEqual(updateTimestamp);
        if (items.isEmpty()) {
            return;
        }
        if (logger.isInfoEnabled()) {
            logger.info("    found " + items.size() + " items");
        }
        try (PrintWriter pw = destinationService.accept("item")) {
            for (Item item : items) {
                om.writeValue(pw, item);
                pw.println();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write value as json to specified writer: " + e.getMessage(), e);
        }
    }

    @Inject
    private ItemRepository repository;

    @Inject
    private DestinationService destinationService;

    private Logger logger = Logger.getLogger(getClass());
}
