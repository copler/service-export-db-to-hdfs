package com.dell.ems.services.export.dbtohdfs.service;

import java.io.PrintWriter;

public interface DestinationService {

    PrintWriter accept(String bucket);

}
