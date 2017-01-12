package com.dell.ems.services.export.dbtohdfs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.dell.ems.services.export.dbtohdfs.tloureiro.entity.Contact;
import com.dell.ems.services.export.dbtohdfs.tloureiro.repository.ContactRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Andrew on 1/12/2017.
 */
@Service
@Profile("pcf")
public class CrmExportService implements ExportService {

    @Autowired
    private ContactRepository repository;

    @Autowired
    private DestinationService destinationService;

    private Logger logger = Logger.getLogger(getClass());

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public void exportSince(Date updateTimestamp) {
        List<Contact> contacts = new ArrayList<>();
        Iterable<Contact> iterable = repository.findAll();
        iterable.forEach(contacts::add);
        if (contacts.isEmpty()) {
            return;
        }
        if (logger.isInfoEnabled()) {
            logger.info("    found " + contacts.size() + " contacts");
        }
        try (PrintWriter pw = destinationService.accept("contact")) {
            for (Contact contact : contacts) {
                om.writeValue(pw, contact);
                pw.println();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write value as json to specified writer: " + e.getMessage(), e);
        }
    }

}
