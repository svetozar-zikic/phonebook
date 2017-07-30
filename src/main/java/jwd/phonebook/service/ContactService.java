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
	
	List<Contact> findByPositionOrPhone(Contact contact);
	
	Page<Contact> findByPositionLike(int page, String position);

	Page<Contact> findByPhone(int page, Integer phoneMin, Integer phoneMax);
	
	Page<Contact> findByBoth(int page, String position, Integer phoneMin, Integer phoneMax);
	
	

}
