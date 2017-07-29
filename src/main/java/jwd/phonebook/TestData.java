package jwd.phonebook;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jwd.phonebook.model.Contact;
import jwd.phonebook.service.ContactService;

@Component
public class TestData {
	
	@Autowired
	ContactService contactSvc;
	
//	@PostConstruct
	public void init(){
		
		Contact c1 = new Contact();
		c1.setPhone(8931);
		c1.setPosition("ASM3");
		c1.setEmail("asm3@brilliance.rccl.com");
		c1.setFirstName("Sveki");
		c1.setLastName("Žikić");
		c1.setAdminComment("Comment1");
		contactSvc.save(c1);

		Contact c2 = new Contact();
		c2.setPhone(911);
		c2.setPosition("ECC");
//		c2.setEmail("n/a");
//		c2.setFirstName("n/a");
//		c2.setLastName("n/a");
		c2.setAdminComment("Comment2");
		contactSvc.save(c2);
		
		Contact c3 = new Contact();
		c3.setPhone(8928);
		c3.setPosition("IT Mgr");
		c3.setEmail("itmgrc@brilliance.rccl.com");
		c3.setFirstName("John");
		c3.setLastName("Doe");
		c3.setAdminComment("Comment3");
		contactSvc.save(c3);
		
		for (int i = 0; i <= 100; i++) {
			Contact contact = new Contact();
			contact.setPhone(1000 + i);
			contact.setPosition("Position " + i);
			contact.setEmail("email" + i + "@mail.com");
			contact.setFirstName("FirstName" + i);
			contact.setLastName("LastName" + i);
			contact.setAdminComment("Admin Comment " + i);
			contactSvc.save(contact);
			
		}
		
		
		
		

		
		
		
	}
	

}
