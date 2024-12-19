package com.springboot.projects.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EnquiryController {
	@GetMapping("/dashboard")
	public String dashboard() {
		return "dashboard";
	}

	@GetMapping("/addenquiry")
	public String addEnquiryPage() { 
		return "addEnquiry";
	}

	@GetMapping("/viewenquiry")
	public String viewEnquiryPage() {
		return "viewEnquiry";
	}
}
