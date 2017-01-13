package com.dell.ems.services.export.dbtohdfs.tloureiro.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dell.ems.services.export.dbtohdfs.tloureiro.entity.OrganizationType;

public interface OrganizationTypeRepository extends CrudRepository<OrganizationType, Integer>{

	public List<OrganizationType> findByNameContainingIgnoreCase(String name);
}
