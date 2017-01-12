package com.dell.ems.services.export.dbtohdfs.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, String> {

    List<Item> findByUpdateTimestampGreaterThanEqual(Date updateTimestamp);

}
