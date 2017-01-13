package com.dell.ems.services.export.dbtohdfs.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DestinationServiceFS implements DestinationService {

    @Value("${service.export.dbtohdfs.root:/tmp}")
    private String root;

    @Override
    public PrintWriter accept(String bucket) {
        File folder = new File(root, bucket);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(folder, "data");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file, false /* append */);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException("Unable to initialize output stream: " + e.getMessage());
        }
        return new PrintWriter(fos, true);
    }

}
