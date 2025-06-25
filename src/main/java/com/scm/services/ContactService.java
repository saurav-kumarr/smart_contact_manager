package com.scm.services;

import java.util.List;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {
	//save contact
	Contact save(Contact contact);
	
	//update contact
	Contact update(Contact contact);
	
	//get contact
	List<Contact> getAll();
	
	//get contact by id
	Contact getById(String id);
	
	//delete contact
	void delete(String id);
	
	//search contact
	List<Contact> search(String name, String email, String phoneNumber);
	
	//get contacts by userId
	List<Contact> getByUserId(String userId);
	
	//get contacts by username
	List<Contact> getByUser(User user);

}
