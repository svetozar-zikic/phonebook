package jwd.phonebook.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jwd.phonebook.model.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	List<Contact> findByPositionOrPhone(
		@Param("position") String position,
		@Param("phone") Integer phone
			);
	
	Page<Contact> findByPositionLike(
		Pageable pageable,
		@Param("position") String position
			);
	
	@Query("SELECT c FROM Contact AS c WHERE "
	+ "(:phoneMin IS NULL OR c.phone >= :phoneMin) AND "
	+ "(:phoneMax IS NULL OR c.phone <= :phoneMax)"
	)
	Page<Contact> customSearch(
		Pageable pageable,
		@Param("phoneMin") Integer phoneMin,
		@Param("phoneMax") Integer phoneMax
			);
	
	@Query("SELECT c FROM Contact AS c WHERE "
			+ "(:position IS NULL OR c.position LIKE :position) AND"
			+ "(:phoneMin IS NULL OR c.phone >= :phoneMin) AND "
			+ "(:phoneMax IS NULL OR c.phone <= :phoneMax)"
			)
	Page<Contact> customSearch2(
		Pageable pageable,
		@Param("position") String position,
		@Param("phoneMin") Integer phoneMin,
		@Param("phoneMax") Integer phoneMax
			);
	
	
}
