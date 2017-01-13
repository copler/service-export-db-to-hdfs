package com.dell.ems.services.export.dbtohdfs.tloureiro.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dell.ems.services.export.dbtohdfs.tloureiro.entity.Organization;

public interface OrganizationRepository extends CrudRepository<Organization, Integer>{

	public List<Organization> findByNameContainingIgnoreCase(String name);
}
