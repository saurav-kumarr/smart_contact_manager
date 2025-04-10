package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String signup() {
		System.out.println("signup page loading");
		return new String("register");
	}

}
