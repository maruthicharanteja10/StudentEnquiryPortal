package com.springboot.projects.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.projects.binding.SignUpForm;
import com.springboot.projects.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/login")
	public String LoginPage() {
		return "login";
	}

	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("user") SignUpForm form, Model model) throws Exception {
		boolean status = userService.signup(form);
		if (status) {
			model.addAttribute("succMsg", "Check Your Email");
		} else {
			model.addAttribute("errMsg", "Problem Occured");
		}
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
