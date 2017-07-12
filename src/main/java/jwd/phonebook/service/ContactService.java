package jwd.phonebook.service;

import java.util.List;

import org.springframework.data.domain.Page;

import jwd.phonebook.model.Contact;

public interface ContactService {
	
//	List<Contact> findAll();
	
	Page<Contact> findAll(int page);
	
	Contact findOne(Long id);
	
	Contact save(Contact contact);
	
	Contact delete(Long id);
	
	

}
