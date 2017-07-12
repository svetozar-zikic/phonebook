package jwd.phonebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jwd.phonebook.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

}
