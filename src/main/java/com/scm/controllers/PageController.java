package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.scm.forms.UserForm;

import ch.qos.logback.core.joran.spi.HttpUtil.RequestMethod;


@Controller
public class PageController {

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
	public String processRegister(@ModelAttribute UserForm userForm) {
		System.out.println("processing registration");
		System.out.println(userForm);
		// fetch form data
		// validation form data
		// save to database
		// message ="Registration Successful"
		// redirect to login page
		return "redirect:/signup";
	}

}
