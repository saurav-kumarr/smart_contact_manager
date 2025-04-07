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
	
	//about
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
	

}
