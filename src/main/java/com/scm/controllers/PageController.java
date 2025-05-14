package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/home")
	public String home() {

		System.out.println("Home page handler");
		return "home";
	}

	// about
	@GetMapping("/about")
	public String aboutPage() {
		System.out.println("About page loading");
		return "about";
	}

	// services
	@GetMapping("/services")
	public String servicesPage() {
		System.out.println("services page loading");
		return "services";
	}

	// Contact Page
	@GetMapping("/contact")
	public String contactPage() {
		System.out.println("contact page loading");
		return new String("contact");
	}

	// LogIn Page
	@GetMapping("/login")
	public String login() {
		System.out.println("login page loading");
		return new String("login");
	}

	// SignUp Page
	@GetMapping("/signup")
	public String signup(Model model) {
		
		System.out.println("signup page loading");
		UserForm userForm = new UserForm();
		model.addAttribute("userForm", userForm);
		return "register";
	}
	
	//Processing register
	@PostMapping("/do-register")
	public String processRegister(@Valid @ModelAttribute UserForm userForm,BindingResult rBindingResult, HttpSession session) {
		System.out.println("processing registration");
		System.out.println(userForm);
		// fetch form data
		// validation form data
		
		if(rBindingResult.hasErrors()) {
			return "register";
		}
		
		// save to database
		// message ="Registration Successful"
		// redirect to login page
		
		//UserForm --> User
//		User user = User.builder()
//				.name(userForm.getName())
//				.email(userForm.getEmail())
//				.password(userForm.getPassword())
//				.about(userForm.getAbout())
//				.phoneNumber(userForm.getPhoneNumber())
//				.profilePic("https://stock.adobe.com/search?k=%22default+picture%22")
//				.build();
		
		User user = new User();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setPassword(userForm.getPassword());
		user.setAbout(userForm.getAbout());
		user.setPhoneNumber(userForm.getPhoneNumber());
		
		user.setProfilePic("https://stock.adobe.com/search?k=%22default+picture%22");
		
		
		User savedUser = userService.saveUser(user);
		System.out.println("User Saved :");
		
		//add the message
		
		Message message = Message.builder().setContent("Registration Successful").setType(MessageType.green).build();
		
		session.setAttribute("message", message);
		
		return "redirect:/signup";
	}

}
