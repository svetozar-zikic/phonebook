package jwd.phonebook.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import jwd.phonebook.model.Contact;
import jwd.phonebook.service.ContactService;
import jwd.phonebook.web.dto.ContactDTO;

@Component
public class ContactDTOToContact implements Converter<ContactDTO, Contact> {

	@Autowired
	private ContactService contactSvc;
	
	@Override
	public Contact convert(ContactDTO dto) {

		Contact contact;
		
		if (dto.getId() == null){
			contact = new Contact();
		} else {
			contact = contactSvc.findOne(dto.getId());
		}
	
		contact.setPhone(dto.getPhone());
		contact.setPosition(dto.getPosition());
		contact.setEmail(dto.getEmail());
		contact.setFirstName(dto.getFirstName());
		contact.setLastName(dto.getLastName());
		
		return contact;
	
	}

}
