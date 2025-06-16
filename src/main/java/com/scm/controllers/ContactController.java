package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.services.ContactService;
import com.scm.services.UserService;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private UserService userService;
	
	//add contact page:handler
	@GetMapping("/add")
	public String addContactView(Model model) {
		
		ContactForm contactForm = new ContactForm();
		
		model.addAttribute("contactForm",contactForm);
		
		return "user/add_contact";
	}
	
	@ModelAttribute
	@PostMapping("/add")
	public String saveContact(@ModelAttribute ContactForm contactForm,Authentication authentication) {
		
		//process the form data
		
		//validate form
		//TODO:add validation logic here
		
		String username = Helper.getEmailOfLoggedInUser(authentication);
		
		//form --> contact
		User user = userService.getUserByEmail(username);
		
		
		Contact contact = new Contact();
		
		contact.setName(contactForm.getName());
		contact.setFavorite(contactForm.isFavorite());
		contact.setEmail(contactForm.getEmail());
		contact.setPhoneNumber(contactForm.getPhoneNumber());
		contact.setAddress(contactForm.getAddress());
		contact.setDescription(contactForm.getDescription());
		contact.setUser(user);
		contact.setLinkedInLink(contactForm.getLinkedInLink());
		contact.setWebsiteLink(contactForm.getWebsiteLink());
		
		
		
		
		contactService.save(contact);
		
		System.out.println(contactForm);
		
		return "redirect:/user/contacts/add";
		
	}

}
