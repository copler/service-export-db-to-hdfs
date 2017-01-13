package com.dell.ems.services.export.dbtohdfs.service;

import com.dell.ems.services.export.dbtohdfs.tloureiro.entity.Contact;
import com.dell.ems.services.export.dbtohdfs.tloureiro.entity.Organization;
import com.dell.ems.services.export.dbtohdfs.tloureiro.repository.ContactRepository;
import com.dell.ems.services.export.dbtohdfs.tloureiro.repository.OrganizationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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

    @Inject
    private ContactRepository contactRepository;

    @Inject
    private OrganizationRepository organizationRepository;

    @Inject
    private DestinationService destinationService;

    private Logger logger = Logger.getLogger(getClass());

    private final ObjectMapper om = new ObjectMapper();

    @Override
    public void exportSince(Date updateTimestamp) {
        exportContacts();
        exportOrganizations();
    }

    private void exportContacts() {
        List<Contact> contacts = new ArrayList<>();
        for (Contact contact : contactRepository.findAll()) {
            contacts.add(contact);
        }
        exportEntities(contacts, "contact");
    }

    private void exportOrganizations() {
        List<Organization> organizations = new ArrayList<>();
        for (Organization organization : organizationRepository.findAll()) {
            organizations.add(organization);
        }
        exportEntities(organizations, "organization");
    }

    private void exportEntities(List<?> entities, String bucket) {
        if (entities.isEmpty()) {
            return;
        }
        if (logger.isInfoEnabled()) {
            logger.info("    found " + entities.size() + " " + bucket + "s");
        }
        try (PrintWriter pw = destinationService.accept(bucket)) {
            for (Object entity : entities) {
                pw.append(om.writeValueAsString(entity));
                pw.println();
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to write value as json to specified writer: " + e.getMessage(), e);
        }
    }

}
