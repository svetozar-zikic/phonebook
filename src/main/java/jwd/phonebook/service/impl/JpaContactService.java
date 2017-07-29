package jwd.phonebook.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jwd.phonebook.model.Contact;
import jwd.phonebook.repository.ContactRepository;
import jwd.phonebook.service.ContactService;

@Service
@Transactional
public class JpaContactService implements ContactService {

	@Autowired
	private ContactRepository contactRepo;
	
	/*@Override
	public List<Contact> findAll() {
		return contactRepo.findAll();
	}*/
	
	@Override
	public Page<Contact> findAll(int page) {
		return contactRepo.findAll(new PageRequest(page, 10));
	}

	@Override
	public Contact findOne(Long id) {
		return contactRepo.findOne(id);
	}

	@Override
	public Contact save(Contact lek) {
		return contactRepo.save(lek);
	}

	@Override
	public Contact delete(Long id) {
		Contact deleted = contactRepo.findOne(id);
		contactRepo.delete(id);
		return deleted;
	}

	@Override
	public List<Contact> findByPositionOrPhone(Contact contact) {
		/*String position = contact.getPosition();
		if (position != null){
			position = "%" + position + "%";
		}*/
		return contactRepo.findByPositionOrPhone(contact.getPosition(), contact.getPhone());
	}


}
