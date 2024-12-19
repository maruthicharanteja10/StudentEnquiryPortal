package com.springboot.projects.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	@GetMapping("/login")
	public String LoginPage() {
		return "login";
	}

	@GetMapping("/signup")
	public String signupPage() {
		return "signup";
	}

	@GetMapping("/forgot")
	public String forgotpassword() {
		return "forgotpswd";
	}

	@GetMapping("/unlock")
	public String unlockPage() {
		return "unlock";
	}
}
