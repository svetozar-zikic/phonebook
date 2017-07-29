package jwd.phonebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jwd.phonebook.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

	/*@Query("SELECT contact FROM contact AS contact WHERE "
			+ "(:position IS NULL OR contact.position LIKE :position) AND "
			+ "(:phone IS NULL OR contact.phone = :phone)"
			)*/
	List<Contact> findByPositionOrPhone(
		@Param("position") String position,
		@Param("phone") Integer phone
			);
	
}
