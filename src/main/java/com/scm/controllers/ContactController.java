package com.scm.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {
	
	//private Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private UserService userService;
	
	//add contact page:handler
	@GetMapping("/add")
	public String addContactView(Model model) {
		
		ContactForm contactForm = new ContactForm();
		
		model.addAttribute("contactForm",contactForm);
		
		return "user/add_contact";
	}
	
	@PostMapping("/add")
	public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication, HttpSession session) {
		
		//process the form data
		
		//1validate form
		//TODO:add validation logic here
		if(result.hasErrors()) {
			session.setAttribute("message", Message.builder()
					.setContent("Please correct the following errors")
					.setType(MessageType.red)
					.build());
			return "user/add_contact";
		}
		
		
		
		String username = Helper.getEmailOfLoggedInUser(authentication);
		
		//form --> contact
		User user = userService.getUserByEmail(username);
		
		String filename = UUID.randomUUID().toString();
		//2process the contact picture code 
		String fileURL = imageService.uploadImage(contactForm.getContactImage(),filename);
		
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
		contact.setPicture(fileURL);
		contact.setCloudinaryImagePublicId(filename);
		
		
		contactService.save(contact);
		
		System.out.println(contactForm);
		//3set the contact picture url
		
		
		//4set message to be displayed on the view
		
		session.setAttribute("message", Message.builder().setContent("You have successfully added a new contact").setType(MessageType.green).build());
		return "redirect:/user/contacts/add";
		
	}

}
