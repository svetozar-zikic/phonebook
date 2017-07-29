package jwd.phonebook.web.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.net.SyslogOutputStream;
import jwd.phonebook.model.Contact;
import jwd.phonebook.service.ContactService;
import jwd.phonebook.support.ContactDTOToContact;
import jwd.phonebook.support.ContactToContactDTO;
import jwd.phonebook.web.dto.ContactDTO;

@RestController
@RequestMapping(value = "/api/contacts")
public class ApiContactController {

	@Autowired
	private ContactService contactSvc;
	
	@Autowired
	private ContactToContactDTO toDTO;
	
	@Autowired
	private ContactDTOToContact toContact;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ContactDTO>> getAll(
			@RequestParam(value = "page", defaultValue = "0") int page
			){
		
		Page<Contact> retVal = contactSvc.findAll(page);
		
		if (retVal == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		if (retVal.getContent().isEmpty())
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("pages", retVal.getTotalPages()+"");
		
		return new ResponseEntity<>(toDTO.convert(retVal.getContent()), headers, HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<ContactDTO> getOne(@PathVariable Long id){
		
		Contact retVal = contactSvc.findOne(id);
		
		if (retVal == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(toDTO.convert(retVal), HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ContactDTO> add(
			@RequestBody ContactDTO dto,
			@RequestParam(value = "page", defaultValue = "0") int page
			){
		
		Contact contact = toContact.convert(dto);
		List<Contact> existing = contactSvc.findByPositionOrPhone(contact);
		
		if (!existing.isEmpty() || existing.size() > 0) {
			System.out.println("phone or position already exists!!");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		contactSvc.save(contact);
		Page<Contact> pages = contactSvc.findAll(0);
		HttpHeaders headers = new HttpHeaders();
		headers.add("pages", pages.getTotalPages()+"");
		
		return new ResponseEntity<>(toDTO.convert(contact), headers, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<ContactDTO> edit(
			@PathVariable Long id,
			@RequestBody ContactDTO dto
			){
		
		if (!id.equals(dto.getId()))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		
		Contact contact = toContact.convert(dto);
		Contact persisted = contactSvc.save(contact);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<ContactDTO> delete(@PathVariable Long id){
		
		Contact contact = contactSvc.findOne(id);
		
		if (contact == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		Contact deleted = contactSvc.delete(id);
		
		return new ResponseEntity<>(toDTO.convert(deleted), HttpStatus.OK);
		
	}
	
	
	
}
