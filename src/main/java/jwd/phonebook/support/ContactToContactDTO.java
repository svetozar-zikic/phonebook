package jwd.phonebook.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import jwd.phonebook.model.Contact;
import jwd.phonebook.web.dto.ContactDTO;

@Component
public class ContactToContactDTO implements Converter<Contact, ContactDTO> {

	@Override
	public ContactDTO convert(Contact contact) {
		
		ContactDTO dto = new ContactDTO();

		dto.setId(contact.getId());
		dto.setPhone(contact.getPhone());
		dto.setPosition(contact.getPosition());
		dto.setEmail(contact.getEmail());
		dto.setFirstName(contact.getFirstName());
		dto.setLastName(contact.getLastName());
		
		return dto; 
	}
	
	public List<ContactDTO> convert(List<Contact> contacts) {
		
		List<ContactDTO> dtos = new ArrayList<>();
		
		for (Contact contact : contacts) {
			dtos.add(convert(contact));
		}
		
		return dtos;
	}

}
