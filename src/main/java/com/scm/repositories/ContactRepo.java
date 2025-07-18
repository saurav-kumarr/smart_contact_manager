package com.scm.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

import java.util.List;



@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
	
	//find the contact by user
	//custom finder method
	Page<Contact> findByUser(User user,Pageable pageable);
	
	//custom query method to get all contacts of a user
	@Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
	List<Contact> findByUserId(String userid);
	
	Page<Contact> findByNameContainingAndUser(String namekeyword, User user, Pageable pageable);
	Page<Contact> findByEmailContainingAndUser(String emailkeyword, User user, Pageable pageable);
	Page<Contact> findByPhoneNumberContainingAndUser(String phonekeyword, User user, Pageable pageable);

}
