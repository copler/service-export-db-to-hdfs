package com.dell.ems.services.export.dbtohdfs.tloureiro.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.dell.ems.services.export.dbtohdfs.tloureiro.entity.Contact;

public interface ContactRepository extends CrudRepository<Contact, Integer>{

	public List<Contact> findByFirstNameContainingIgnoreCase(String firstName);
	
	public List<Contact> findByOrganizationId(Integer id);
}
