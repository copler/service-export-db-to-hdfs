package com.dell.ems.services.export.dbtohdfs.service;

import java.util.Date;

public interface ExportService {

    void exportSince(Date since);

}
