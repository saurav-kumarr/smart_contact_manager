package com.scm.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
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
		
		
		//2process the contact picture code 
		
		
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
		
		
		if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
			String filename = UUID.randomUUID().toString();
			String fileURL = imageService.uploadImage(contactForm.getContactImage(),filename);
			contact.setPicture(fileURL);
			contact.setCloudinaryImagePublicId(filename);
		}
		
		
		contactService.save(contact);
		
		System.out.println(contactForm);
		//3set the contact picture url
		
		
		//4set message to be displayed on the view
		
		session.setAttribute("message", Message.builder().setContent("You have successfully added a new contact").setType(MessageType.green).build());
		return "redirect:/user/contacts/add";
		
	}
	
	
	//View Contact
	@GetMapping
	public String viewContacts(
			
			@RequestParam(value = "page",defaultValue = "0") int page,
			@RequestParam(value = "size",defaultValue = AppConstants.PAGE_SIZE+"") int size,
			@RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
			@RequestParam(value = "direction",defaultValue = "asc") String direction,
			
			Model model,Authentication authentication) {
		
		String username = Helper.getEmailOfLoggedInUser(authentication);
		
		User userByEmail = userService.getUserByEmail(username);
		
		//load all the user contacts
		Page<Contact> pageContacts = contactService.getByUser(userByEmail,page,size,sortBy,direction);
		
		
		
		model.addAttribute("pageContacts", pageContacts);
		model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
		
		model.addAttribute("contactSearchForm", new ContactSearchForm());
		
		return "user/contacts";
	}
	
	
	// Search handler
	@GetMapping("/search")
	public String searchHandler(
			@ModelAttribute ContactSearchForm contactSearchForm,
			@RequestParam(value="size", defaultValue = AppConstants.PAGE_SIZE+"") int size,
			@RequestParam(value="page",defaultValue  = "0") int page,
			@RequestParam(value="sortBy",defaultValue = "name") String sortBy,
			@RequestParam(value = "direction",defaultValue = "asc") String direction,
			Model model,
			Authentication authentication
			) {
		
		var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));
		
		
	    String field = contactSearchForm.getField();
	    String value = contactSearchForm.getValue();

	    // 🛑 Case 3: Field and value both empty → stay on the same page
	    if ((field == null || field.isBlank()) && (value == null || value.isBlank())) {
	        model.addAttribute("contactSearchForm", contactSearchForm);
	        model.addAttribute("pageContacts", Page.empty());
	        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
	        return "user/search";
	    }

	    // ✅ Case 2: Value entered, field not selected → default to "name"
	    if ((field == null || field.isBlank()) && (value != null && !value.isBlank())) {
	        contactSearchForm.setField("name");
	        field = "name";
	    }

	    // 🛑 Case 1: Field selected, value empty → stay on the same page
	    if ((field != null && !field.isBlank()) && (value == null || value.isBlank())) {
	        model.addAttribute("contactSearchForm", contactSearchForm);
	        model.addAttribute("pageContacts", Page.empty());
	        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
	        return "user/search";
	    }


		
		Page<Contact> pageContacts = null;
		
		if(contactSearchForm.getField().equalsIgnoreCase("name")) {
			pageContacts = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,user);
			
		} else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
			pageContacts = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,user);
			
		} else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
			pageContacts = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction,user);
					
		}
		
		model.addAttribute("contactSearchForm",contactSearchForm);
		
		model.addAttribute("pageContacts", pageContacts);
		
		model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
		
		return "user/search";
		
	}
	
	
	//Delete Contact
	@RequestMapping("/delete/{contactId}")
	public String deleteContact(
			@PathVariable() String contactId,
			HttpSession session
			) {
		
		contactService.delete(contactId);
		
		session.setAttribute("message", 
				Message.builder()
				.setContent("Contact is Deleted successfully !!")
				.setType(MessageType.green)
				.build()
				);
		
		return "redirect:/user/contacts";
	}
	
	
	//Update contact form view
	@GetMapping("/view/{contactId}")
	public String updateContactFormView(
			@PathVariable() String contactId,
			Model model) {
		
		var contact = contactService.getById(contactId);
		
		ContactForm contactForm = new ContactForm();
		contactForm.setName(contact.getName());
		contactForm.setEmail(contact.getEmail());
		contactForm.setPhoneNumber(contact.getPhoneNumber());
		contactForm.setAddress(contact.getAddress());
		contactForm.setDescription(contact.getDescription());
		contactForm.setFavorite(contact.isFavorite());
		contactForm.setWebsiteLink(contact.getWebsiteLink());
		contactForm.setLinkedInLink(contact.getLinkedInLink());
		contactForm.setPicture(contact.getPicture());
		
		model.addAttribute("contactForm", contactForm);
		model.addAttribute("contactId",contactId);
		
		
		return "user/update_contact_view";
	}
	
	
	@PostMapping("/update/{contactId}")
	public String updateContact(
			@PathVariable String contactId,
			@Valid @ModelAttribute ContactForm contactForm,
			BindingResult bindingResult,
			Model model
			) {
		
		if(bindingResult.hasErrors()) {
			return "user/update_contact_view";
		}
		
		//update the contact
		var con =  contactService.getById(contactId);
		con.setId(contactId);
		con.setName(contactForm.getName());
		con.setEmail(contactForm.getEmail());
		con.setPhoneNumber(contactForm.getPhoneNumber());
		con.setAddress(contactForm.getAddress());
		con.setDescription(contactForm.getDescription());
		con.setFavorite(contactForm.isFavorite());
		con.setWebsiteLink(contactForm.getWebsiteLink());
		con.setLinkedInLink(contactForm.getLinkedInLink());
		//con.setPicture(contactForm.getPicture());
		
		//process image
		if(contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
		String fileName = UUID.randomUUID().toString();
		String imageUrl =  imageService.uploadImage(contactForm.getContactImage(), fileName);
		con.setCloudinaryImagePublicId(fileName);
		con.setPicture(imageUrl);
		contactForm.setPicture(imageUrl);
		}
		
		var updateCon = contactService.update(con);
		
		model.addAttribute("message", Message.builder().setContent("Contact Updated !!").setType(MessageType.green).build());
		
		return "redirect:/user/contacts/view/" + contactId;
	}

}
