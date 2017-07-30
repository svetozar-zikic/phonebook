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

	@Override
	public Page<Contact> findByPositionLike(int page, String position) {
		position = "%" + position + "%";
		return contactRepo.findByPositionLike(new PageRequest(page, 10), position);
	}

	@Override
	public Page<Contact> findByPhone(int page, Integer phoneMin, Integer phoneMax) {
		return contactRepo.customSearch(new PageRequest(page, 5), phoneMin, phoneMax);
	}

	@Override
	public Page<Contact> findByBoth(int page, String position, Integer phoneMin, Integer phoneMax) {
		position = "%" + position + "%";
		return contactRepo.customSearch2(new PageRequest(page, 5), position, phoneMin, phoneMax);
	}


}
